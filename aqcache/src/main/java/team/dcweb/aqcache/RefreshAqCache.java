package team.dcweb.aqcache;

import team.dcweb.aqcache.embedded.AbstractEmbeddedAqCache;
import team.dcweb.aqcache.exception.CacheInvokeException;
import team.dcweb.aqcache.support.AqCacheExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * Create On 2021/7/17
 *
 * @author hongkun
 * @version 1.0.0
 */
public class RefreshAqCache<K, V> extends LoadingAqCache<K, V> {

    private static final Logger logger = LoggerFactory.getLogger(RefreshAqCache.class);

    private ConcurrentHashMap<Object, RefreshTask> taskMap = new ConcurrentHashMap<>();

//    private boolean multiLevelCache;

    public RefreshAqCache(AqCache aqCache) {
        super(aqCache);
//        multiLevelCache = isMultiLevelCache();
    }

    protected void stopRefresh() {
        List<RefreshTask> tasks = new ArrayList<>();
        tasks.addAll(taskMap.values());
        tasks.forEach(task -> task.cancel());
    }

    @Override
    public void close() {
        stopRefresh();
        super.close();
    }


    private boolean hasLoader() {
        return config.getLoader() != null;
    }

    @Override
    public V computeIfAbsent(K key, Function<K, V> loader) {
        return computeIfAbsent(key, loader, config().isCacheNullValue());
    }

    @Override
    public V computeIfAbsent(K key, Function<K, V> loader, boolean cacheNullWhenLoaderReturnNull) {
        return AbstractAqCache.computeIfAbsentImpl(key, loader, cacheNullWhenLoaderReturnNull,
                0, null, this);
    }

    @Override
    public V computeIfAbsent(K key, Function<K, V> loader, boolean cacheNullWhenLoaderReturnNull,
                             long expireAfterWrite, TimeUnit timeUnit) {
        return AbstractAqCache.computeIfAbsentImpl(key, loader, cacheNullWhenLoaderReturnNull,
                expireAfterWrite, timeUnit, this);
    }

    protected AqCache concreteCache() {
        AqCache c = getTargetCache();
        while (true) {
            if (c instanceof ProxyAqCache) {
                c = ((ProxyAqCache) c).getTargetCache();
            } else {
                return c;
            }
        }
    }

//    private boolean isMultiLevelCache() {
//        Cache c = getTargetCache();
//        while (c instanceof ProxyCache) {
//            c = ((ProxyCache) c).getTargetCache();
//        }
//        return c instanceof MultiLevelCache;
//    }

    private Object getTaskId(K key) {
        AqCache c = concreteCache();
        if (c instanceof AbstractEmbeddedAqCache) {
            return ((AbstractEmbeddedAqCache) c).buildKey(key);
        } else {
            logger.error("can't getTaskId from " + c.getClass());
            return null;
        }
    }

    protected void addOrUpdateRefreshTask(K key, CacheLoader<K, V> loader) {
        RefreshPolicy refreshPolicy = config.getRefreshPolicy();
        if (refreshPolicy == null) {
            return;
        }
        long refreshMillis = refreshPolicy.getRefreshMillis();
        if (refreshMillis > 0) {
            Object taskId = getTaskId(key);
            RefreshTask refreshTask = taskMap.computeIfAbsent(taskId, tid -> {
                logger.debug("add refresh task. interval={},  key={}", refreshMillis, key);
                RefreshTask task = new RefreshTask(taskId, key, loader);
                task.lastAccessTime = System.currentTimeMillis();
                ScheduledFuture<?> future = AqCacheExecutor.heavyIOExecutor().scheduleWithFixedDelay(
                        task, refreshMillis, refreshMillis, TimeUnit.MILLISECONDS);
                task.future = future;
                return task;
            });
            refreshTask.lastAccessTime = System.currentTimeMillis();
        }
    }

    @Override
    public V get(K key) throws CacheInvokeException {
        if (config.getRefreshPolicy() != null && hasLoader()) {
            addOrUpdateRefreshTask(key, null);
        }
        return super.get(key);
    }

    @Override
    public Map<K, V> getAll(Set<? extends K> keys) throws CacheInvokeException {
        if (config.getRefreshPolicy() != null && hasLoader()) {
            for (K key : keys) {
                addOrUpdateRefreshTask(key, null);
            }
        }
        return super.getAll(keys);
    }

    class RefreshTask implements Runnable {
        private Object taskId;
        private K key;
        private CacheLoader<K, V> loader;

        private long lastAccessTime;
        private ScheduledFuture future;

        RefreshTask(Object taskId, K key, CacheLoader<K, V> loader) {
            this.taskId = taskId;
            this.key = key;
            this.loader = loader;
        }

        private void cancel() {
            logger.debug("cancel refresh: {}", key);
            future.cancel(false);
            taskMap.remove(taskId);
        }

        private void load() throws Throwable {
            CacheLoader<K, V> l = loader == null ? config.getLoader() : loader;
            if (l != null) {
                l = CacheUtil.createProxyLoader(aqCache, l, eventConsumer);
                V v = l.load(key);
                if (needUpdate(v, l)) {
                    aqCache.PUT(key, v);
                }
            }
        }


//        private void refreshUpperCaches(K key) {
//            MultiLevelCache<K, V> targetCache = (MultiLevelCache<K, V>) getTargetCache();
//            Cache[] caches = targetCache.caches();
//            int len = caches.length;
//
//            CacheGetResult cacheGetResult = caches[len - 1].GET(key);
//            if (!cacheGetResult.isSuccess()) {
//                return;
//            }
//            for (int i = 0; i < len - 1; i++) {
//                caches[i].PUT(key, cacheGetResult.getValue());
//            }
//        }

        @Override
        public void run() {
            try {
                if (config.getRefreshPolicy() == null || (loader == null && !hasLoader())) {
                    cancel();
                    return;
                }
                long now = System.currentTimeMillis();
                long stopRefreshAfterLastAccessMillis = config.getRefreshPolicy().getStopRefreshAfterLastAccessMillis();
                if (stopRefreshAfterLastAccessMillis > 0) {
                    if (lastAccessTime + stopRefreshAfterLastAccessMillis < now) {
                        logger.debug("cancel refresh: {}", key);
                        cancel();
                        return;
                    }
                }
                logger.debug("refresh key: {}", key);
                AqCache concreteAqCache = concreteCache();
                load();
            } catch (Throwable e) {
                logger.error("refresh error: key=" + key, e);
            }
        }
    }

    private byte[] combine(byte[] bs1, byte[] bs2) {
        byte[] newArray = Arrays.copyOf(bs1, bs1.length + bs2.length);
        System.arraycopy(bs2, 0, newArray, bs1.length, bs2.length);
        return newArray;
    }
}