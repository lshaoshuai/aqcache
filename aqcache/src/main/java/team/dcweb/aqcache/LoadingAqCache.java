package team.dcweb.aqcache;

import team.dcweb.aqcache.event.CacheEvent;
import team.dcweb.aqcache.exception.CacheInvokeException;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Create On 2021/7/17
 *
 * @author hongkun
 * @version 1.0.0
 */

public class LoadingAqCache<K, V> extends SimpleProxyAqCache<K, V> {

    protected Consumer<CacheEvent> eventConsumer;

    protected CacheConfig<K, V> config;

    public LoadingAqCache(AqCache<K, V> aqCache) {
        super(aqCache);
        this.config = config();
        eventConsumer = CacheUtil.getAbstractCache(aqCache)::notify;
    }

    @Override
    public V get(K key) throws CacheInvokeException {
        CacheLoader<K, V> loader = config.getLoader();
        if (loader != null) {
            return AbstractAqCache.computeIfAbsentImpl(key, loader,
                    config.isCacheNullValue(), 0, null, this);
        } else {
            return aqCache.get(key);
        }
    }

    protected boolean needUpdate(V loadedValue, CacheLoader<K, V> loader) {
        if (loadedValue == null && !config.isCacheNullValue()) {
            return false;
        }
        if (loader.vetoCacheUpdate()) {
            return false;
        }
        return true;
    }

    @Override
    public Map<K, V> getAll(Set<? extends K> keys) throws CacheInvokeException {
        CacheLoader<K, V> loader = config.getLoader();
        if (loader != null) {
            MultiGetResult<K, V> r = GET_ALL(keys);
            Map<K, V> kvMap;
            if (r.isSuccess() || r.getResultCode() == CacheResultCode.PART_SUCCESS) {
                kvMap = r.unwrapValues();
            } else {
                kvMap = new HashMap<>();
            }
            Set<K> keysNeedLoad = new LinkedHashSet<>();
            keys.forEach((k) -> {
                if (!kvMap.containsKey(k)) {
                    keysNeedLoad.add(k);
                }
            });
            if (!config.isCachePenetrationProtect()) {
                if (eventConsumer != null) {
                    loader = CacheUtil.createProxyLoader(aqCache, loader, eventConsumer);
                }
                Map<K, V> loadResult;
                try {
                    loadResult = loader.loadAll(keysNeedLoad);

                    CacheLoader<K, V> theLoader = loader;
                    Map<K, V> updateValues = loadResult.entrySet().stream()
                            .filter(kvEntry -> needUpdate(kvEntry.getValue(), theLoader))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

                    // batch put
                    if (!updateValues.isEmpty()) {
                        PUT_ALL(updateValues);
                    }
                } catch (Throwable e) {
                    throw new CacheInvokeException(e);
                }
                kvMap.putAll(loadResult);
            } else {
                AbstractAqCache<K, V> abstractCache = CacheUtil.getAbstractCache(aqCache);
                loader = CacheUtil.createProxyLoader(aqCache, loader, eventConsumer);
                for (K key : keysNeedLoad) {
                    Consumer<V> cacheUpdater = (v) -> {
                        if (needUpdate(v, config.getLoader())) {
                            PUT(key, v);
                        }
                    };
                    V v = AbstractAqCache.synchronizedLoad(config, abstractCache, key, loader, cacheUpdater);
                    kvMap.put(key, v);
                }
            }
            return kvMap;
        } else {
            return aqCache.getAll(keys);
        }

    }
}
