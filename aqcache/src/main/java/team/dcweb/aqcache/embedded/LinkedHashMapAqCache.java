package team.dcweb.aqcache.embedded;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import team.dcweb.aqcache.CacheResultCode;
import team.dcweb.aqcache.CacheValueHolder;
import team.dcweb.aqcache.anno.listener.CacheEventListener;

import java.util.*;

/**
 * Create On 2021/7/19
 *
 * @author hongkun
 * @version 1.0.0
 */
public class LinkedHashMapAqCache<K, V> extends AbstractEmbeddedAqCache<K, V> {

    private static Logger logger = LoggerFactory.getLogger(LinkedHashMapAqCache.class);

    public LinkedHashMapAqCache(EmbeddedCacheConfig<K, V> config) {
        super(config);
        addToCleaner();
    }

    protected void addToCleaner() {
        Cleaner.add(this);
    }

    @Override
    protected InnerMap createAreaCache() {
        return new LRUMap(config.getLimit(), config.getLimitMemory(), config.getCacheEventListener(), this);
    }

    @Override
    public <T> T unwrap(Class<T> clazz) {
        if (clazz.equals(AqLinkedHashMap.class)) {
            return (T) innerMap;
        }
        throw new IllegalArgumentException(clazz.getName());
    }

    public void cleanExpiredEntry() {
        ((LRUMap) innerMap).cleanExpiredEntry();
    }

    final class LRUMap extends AqLinkedHashMap implements InnerMap {

        private final int max;
        private final double memory;
        private CacheEventListener cacheEventListener;
        private Object lock;

        public LRUMap(int max, double memory, CacheEventListener cacheEventListener, Object lock) {
            super((int) (max * 1.4f), 0.75f, true);
            this.max = max;
            this.memory = memory;
            this.lock = lock;
            this.cacheEventListener = cacheEventListener;
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry eldest) {
            if (size() > max) {
                AqEntry<K, V> entry = new AqEntry<>();
                CacheValueHolder<V> holder = (CacheValueHolder<V>) eldest.getValue();
                entry.put((K) eldest.getKey(), parseHolderResult(holder).getValue());
                cacheEventListener.onEvent(entry);
            }
            return size() > max;
        }

        void cleanExpiredEntry() {
            synchronized (lock) {
                for (Iterator it = entrySet().iterator(); it.hasNext(); ) {
                    Map.Entry en = (Map.Entry) it.next();
                    Object value = en.getValue();
                    if (value != null && value instanceof CacheValueHolder) {
                        CacheValueHolder h = (CacheValueHolder) value;
                        if (System.currentTimeMillis() >= h.getExpireTime()) {
                            it.remove();
                        }
                    } else {
                        // assert false
                        if (value == null) {
                            logger.error("key " + en.getKey() + " is null");
                        } else {
                            logger.error("value of key " + en.getKey() + " is not a CacheValueHolder. type=" + value.getClass());
                        }
                    }
                }
            }
        }

        @Override
        public Object getValue(Object key) {
            synchronized (lock) {
                return get(key);
            }
        }

        @Override
        public Map getAllValues(Collection keys) {
            Map values = new HashMap();
            synchronized (lock) {
                for (Object key : keys) {
                    Object v = get(key);
                    if (v != null) {
                        values.put(key, v);
                    }
                }
            }
            return values;
        }

        @Override
        public void putValue(Object key, Object value) {
            synchronized (lock) {
                put(key, value);
            }
        }

        @Override
        public void putAllValues(Map map) {
            synchronized (lock) {
                Set<Map.Entry> set = map.entrySet();
                for (Map.Entry en : set) {
                    put(en.getKey(), en.getValue());
                }
            }
        }

        @Override
        public boolean removeValue(Object key) {
            synchronized (lock) {
                return remove(key) != null;
            }
        }

        @Override
        public void removeAllValues(Collection keys) {
            synchronized (lock) {
                for (Object k : keys) {
                    remove(k);
                }
            }
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean putIfAbsentValue(Object key, Object value) {
            synchronized (lock) {
                CacheValueHolder h = (CacheValueHolder) get(key);
                if (h == null || parseHolderResult(h).getResultCode() == CacheResultCode.EXPIRED) {
                    put(key, value);
                    return true;
                } else {
                    return false;
                }
            }
        }

        @Override
        public HashSet<K> getKeySet() {
            synchronized (lock) {
                return new HashSet<K>(keySet());
            }
        }

        @Override
        public boolean isValueExist(Object value) {
            synchronized (lock) {
                return containsValue(value);
            }
        }

        @Override
        public boolean isKeyExist(Object key) {
            return super.containsKey(key);
        }

        @Override
        public List values() {
            List list = new ArrayList<>();
            super.values().forEach(item -> {
                CacheValueHolder h = (CacheValueHolder) item;
                list.add(h.getValue());
            });
            return list;
        }

        @Override
        public Map.Entry getEldestEntry() {
            return super.getEldestEntry();
        }

        @Override
        public Set<Map.Entry> entrySet() {
            return super.entrySet();
        }
    }
}
