package team.dcweb.aqcache;

import team.dcweb.aqcache.event.CacheEvent;
import team.dcweb.aqcache.event.CacheLoadAllEvent;
import team.dcweb.aqcache.event.CacheLoadEvent;

import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Create On 2021/7/17
 *
 * @author hongkun
 * @version 1.0.0
 */
public class CacheUtil {

    private interface ProxyLoader<K, V> extends CacheLoader<K, V> {
    }

    public static <K, V> ProxyLoader<K, V> createProxyLoader(AqCache<K, V> aqCache,
                                                             CacheLoader<K, V> loader,
                                                             Consumer<CacheEvent> eventConsumer) {
        if (loader instanceof ProxyLoader) {
            return (ProxyLoader<K, V>) loader;
        }
        return new ProxyLoader<K, V>() {
            @Override
            public V load(K key) throws Throwable {
                long t = System.currentTimeMillis();
                V v = null;
                boolean success = false;
                try {
                    v = loader.load(key);
                    success = true;
                } finally {
                    t = System.currentTimeMillis() - t;
                    CacheLoadEvent event = new CacheLoadEvent(aqCache, t, key, v, success);
                    eventConsumer.accept(event);
                }
                return v;
            }

            @Override
            public Map<K, V> loadAll(Set<K> keys) throws Throwable {
                long t = System.currentTimeMillis();
                boolean success = false;
                Map<K, V> kvMap = null;
                try {
                    kvMap = loader.loadAll(keys);
                    success = true;
                } finally {
                    t = System.currentTimeMillis() - t;
                    CacheLoadAllEvent event = new CacheLoadAllEvent(aqCache, t, keys, kvMap, success);
                    eventConsumer.accept(event);
                }
                return kvMap;
            }

            @Override
            public boolean vetoCacheUpdate() {
                return loader.vetoCacheUpdate();
            }
        };
    }

    public static <K, V> ProxyLoader<K, V> createProxyLoader(AqCache<K, V> aqCache,
                                                             Function<K, V> loader,
                                                             Consumer<CacheEvent> eventConsumer) {
        if (loader instanceof ProxyLoader) {
            return (ProxyLoader<K, V>) loader;
        }
        if (loader instanceof CacheLoader) {
            return createProxyLoader(aqCache, (CacheLoader) loader, eventConsumer);
        }
        return k -> {
            long t = System.currentTimeMillis();
            V v = null;
            boolean success = false;
            try {
                v = loader.apply(k);
                success = true;
            } finally {
                t = System.currentTimeMillis() - t;
                CacheLoadEvent event = new CacheLoadEvent(aqCache, t, k, v, success);
                eventConsumer.accept(event);
            }
            return v;
        };
    }


    public static <K, V> AbstractAqCache<K, V> getAbstractCache(AqCache<K, V> c) {
        while (c instanceof ProxyAqCache) {
            c = ((ProxyAqCache) c).getTargetCache();
        }
        return (AbstractAqCache) c;
    }

}

