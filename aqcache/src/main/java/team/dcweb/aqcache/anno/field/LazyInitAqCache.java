package team.dcweb.aqcache.anno.field;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import team.dcweb.aqcache.*;
import team.dcweb.aqcache.anno.CacheConsts;
import team.dcweb.aqcache.anno.CachePenetrationProtect;
import team.dcweb.aqcache.anno.CacheRefresh;
import team.dcweb.aqcache.anno.CreateAqCache;
import team.dcweb.aqcache.anno.listener.AbstractCacheEventListener;
import team.dcweb.aqcache.anno.listener.CacheEventListener;
import team.dcweb.aqcache.anno.method.CacheConfigUtil;
import team.dcweb.aqcache.anno.support.*;
import team.dcweb.aqcache.lock.AutoReleaseLock;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * @author hongkun
 * @version 1.0.0
 * @since 1.8
 **/
class LazyInitAqCache implements ProxyAqCache {

    private boolean inited;
    private AqCache aqCache;

    private ConfigurableListableBeanFactory beanFactory;
    private CreateAqCache ann;
    private Field field;
    private RefreshPolicy refreshPolicy;
    private PenetrationProtectConfig protectConfig;

    public LazyInitAqCache(ConfigurableListableBeanFactory beanFactory, CreateAqCache ann, Field field) {
        this.beanFactory = beanFactory;
        this.ann = ann;
        this.field = field;
        CacheRefresh cr = field.getAnnotation(CacheRefresh.class);
        if (cr != null) {
            refreshPolicy = CacheConfigUtil.parseRefreshPolicy(cr);
        }
        CachePenetrationProtect penetrateProtect = field.getAnnotation(CachePenetrationProtect.class);
        if (penetrateProtect != null) {
            protectConfig = CacheConfigUtil.parsePenetrationProtectConfig(penetrateProtect);
        }
    }

    private void checkInit() {
        if (!inited) {
            synchronized (this) {
                if (!inited) {
                    init();
                    inited = true;
                }
            }
        }
    }

    @Override
    public AqCache getTargetCache() {
        checkInit();
        return aqCache;
    }

    private void init() {
        if (inited) {
            throw new IllegalStateException();
        }
        GlobalAqCacheConfig globalAqCacheConfig = beanFactory.getBean(GlobalAqCacheConfig.class);
        ConfigProvider configProvider = beanFactory.getBean(ConfigProvider.class);

        CachedAnnoConfig cac = new CachedAnnoConfig();
        cac.setArea(ann.area());
        cac.setName(ann.name());
        cac.setTimeUnit(ann.timeUnit());
        cac.setExpire(ann.expire());
        cac.setLocalExpire(ann.localExpire());
        cac.setCacheType(ann.cacheType());
        cac.setLocalLimit(ann.localLimit());
        cac.setSerialPolicy(ann.serialPolicy());
        cac.setKeyConvertor(ann.keyConvertor());
        cac.setLimitMemory(ann.limitMemory());
        try {
            if (ann.method().getSuperclass().equals(AbstractCacheEventListener.class)) {
                cac.setCacheEventListener((CacheEventListener) ann.method().newInstance());
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        cac.setRefreshPolicy(refreshPolicy);
        cac.setPenetrationProtectConfig(protectConfig);

        String cacheName = cac.getName();
        if (CacheConsts.isUndefined(cacheName)) {
            String[] hiddenPackages = globalAqCacheConfig.getHiddenPackages();
            CacheNameGenerator g = configProvider.createCacheNameGenerator(hiddenPackages);
            cacheName = g.generateCacheName(field);
        }
        aqCache = configProvider.getCacheContext().__createOrGetCache(cac, ann.area(), cacheName);
    }

    @Override
    public CacheConfig config() {
        checkInit();
        return aqCache.config();
    }

    @Override
    public Object get(Object key) {
        checkInit();
        return aqCache.get(key);
    }

    @Override
    public Map getAll(Set keys) {
        checkInit();
        return aqCache.getAll(keys);
    }

    @Override
    public CacheGetResult GET(Object key) {
        checkInit();
        return aqCache.GET(key);
    }

    @Override
    public MultiGetResult GET_ALL(Set keys) {
        checkInit();
        return aqCache.GET_ALL(keys);
    }

    @Override
    public Object computeIfAbsent(Object key, Function loader) {
        checkInit();
        return aqCache.computeIfAbsent(key, loader);
    }

    @Override
    public Object computeIfAbsent(Object key, Function loader, boolean cacheNullWhenLoaderReturnNull) {
        checkInit();
        return aqCache.computeIfAbsent(key, loader, cacheNullWhenLoaderReturnNull);
    }

    @Override
    public Object computeIfAbsent(Object key, Function loader, boolean cacheNullWhenLoaderReturnNull, long expireAfterWrite, TimeUnit timeUnit) {
        checkInit();
        return aqCache.computeIfAbsent(key, loader, cacheNullWhenLoaderReturnNull, expireAfterWrite, timeUnit);
    }

    @Override
    public void put(Object key, Object value) {
        checkInit();
        aqCache.put(key, value);
    }

    @Override
    public void putAll(Map map) {
        checkInit();
        aqCache.putAll(map);
    }

    @Override
    public void put(Object key, Object value, long expireAfterWrite, TimeUnit timeUnit) {
        checkInit();
        aqCache.put(key, value, expireAfterWrite, timeUnit);
    }

    @Override
    public CacheResult PUT(Object key, Object value) {
        checkInit();
        return aqCache.PUT(key, value);
    }

    @Override
    public CacheResult PUT(Object key, Object value, long expireAfterWrite, TimeUnit timeUnit) {
        checkInit();
        return aqCache.PUT(key, value, expireAfterWrite, timeUnit);
    }

    @Override
    public void putAll(Map map, long expireAfterWrite, TimeUnit timeUnit) {
        checkInit();
        aqCache.putAll(map, expireAfterWrite, timeUnit);
    }

    @Override
    public CacheResult PUT_ALL(Map map) {
        checkInit();
        return aqCache.PUT_ALL(map);
    }

    @Override
    public CacheResult PUT_ALL(Map map, long expireAfterWrite, TimeUnit timeUnit) {
        checkInit();
        return aqCache.PUT_ALL(map, expireAfterWrite, timeUnit);
    }

    @Override
    public boolean remove(Object key) {
        checkInit();
        return aqCache.remove(key);
    }

    @Override
    public void removeAll(Set keys) {
        checkInit();
        aqCache.removeAll(keys);
    }

    @Override
    public Object unwrap(Class clazz) {
        checkInit();
        return aqCache.unwrap(clazz);
    }

    @Override
    public CacheResult REMOVE(Object key) {
        checkInit();
        return aqCache.REMOVE(key);
    }

    @Override
    public CacheResult REMOVE_ALL(Set keys) {
        checkInit();
        return aqCache.REMOVE_ALL(keys);
    }

    @Override
    public AutoReleaseLock tryLock(Object key, long expire, TimeUnit timeUnit) {
        checkInit();
        return aqCache.tryLock(key, expire, timeUnit);
    }

    @Override
    public boolean tryLockAndRun(Object key, long expire, TimeUnit timeUnit, Runnable action) {
        checkInit();
        return aqCache.tryLockAndRun(key, expire, timeUnit, action);
    }

    @Override
    public boolean putIfAbsent(Object key, Object value) {
        checkInit();
        return aqCache.putIfAbsent(key, value);
    }

    @Override
    public CacheResult PUT_IF_ABSENT(Object key, Object value, long expireAfterWrite, TimeUnit timeUnit) {
        checkInit();
        return aqCache.PUT_IF_ABSENT(key, value, expireAfterWrite, timeUnit);
    }

    @Override
    public Set getKeySet() {
        checkInit();
        return aqCache.getKeySet();
    }

    @Override
    public boolean isValueExist(Object value) {
        checkInit();
        return aqCache.isValueExist(value);
    }

    @Override
    public boolean isKeyExist(Object key) {
        checkInit();
        return aqCache.isKeyExist(key);
    }

    @Override
    public Collection values() {
        return aqCache.values();
    }
}
