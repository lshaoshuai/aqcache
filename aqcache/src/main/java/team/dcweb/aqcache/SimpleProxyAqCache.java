package team.dcweb.aqcache;

import team.dcweb.aqcache.lock.AutoReleaseLock;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * Create On 2021/7/17
 *
 * @author hongkun
 * @version 1.0.0
 */
public class SimpleProxyAqCache<K, V> implements ProxyAqCache<K, V> {

    protected AqCache<K, V> aqCache;

    public SimpleProxyAqCache(AqCache<K, V> aqCache) {
        this.aqCache = aqCache;
    }

    @Override
    public CacheConfig<K, V> config() {
        return aqCache.config();
    }

    @Override
    public AqCache<K, V> getTargetCache() {
        return aqCache;
    }

    @Override
    public V get(K key) {
        return aqCache.get(key);
    }

    @Override
    public Map<K, V> getAll(Set<? extends K> keys) {
        return aqCache.getAll(keys);
    }

    @Override
    public void put(K key, V value) {
        aqCache.put(key, value);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        aqCache.putAll(map);
    }

    @Override
    public boolean putIfAbsent(K key, V value) {
        return aqCache.putIfAbsent(key, value);
    }

    @Override
    public boolean remove(K key) {
        return aqCache.remove(key);
    }

    @Override
    public void removeAll(Set<? extends K> keys) {
        aqCache.removeAll(keys);
    }

    @Override
    public <T> T unwrap(Class<T> clazz) {
        return aqCache.unwrap(clazz);
    }

    @Override
    public AutoReleaseLock tryLock(K key, long expire, TimeUnit timeUnit) {
        return aqCache.tryLock(key, expire, timeUnit);
    }

    @Override
    public boolean tryLockAndRun(K key, long expire, TimeUnit timeUnit, Runnable action) {
        return aqCache.tryLockAndRun(key, expire, timeUnit, action);
    }

    @Override
    public CacheGetResult<V> GET(K key) {
        return aqCache.GET(key);
    }

    @Override
    public MultiGetResult<K, V> GET_ALL(Set<? extends K> keys) {
        return aqCache.GET_ALL(keys);
    }

    @Override
    public V computeIfAbsent(K key, Function<K, V> loader) {
        return aqCache.computeIfAbsent(key, loader);
    }

    @Override
    public V computeIfAbsent(K key, Function<K, V> loader, boolean cacheNullWhenLoaderReturnNull) {
        return aqCache.computeIfAbsent(key, loader, cacheNullWhenLoaderReturnNull);
    }

    @Override
    public V computeIfAbsent(K key, Function<K, V> loader, boolean cacheNullWhenLoaderReturnNull, long expireAfterWrite, TimeUnit timeUnit) {
        return aqCache.computeIfAbsent(key, loader, cacheNullWhenLoaderReturnNull, expireAfterWrite, timeUnit);
    }

    @Override
    public void put(K key, V value, long expireAfterWrite, TimeUnit timeUnit) {
        aqCache.put(key, value, expireAfterWrite, timeUnit);
    }

    @Override
    public CacheResult PUT(K key, V value) {
        return aqCache.PUT(key, value);
    }

    @Override
    public CacheResult PUT(K key, V value, long expireAfterWrite, TimeUnit timeUnit) {
        return aqCache.PUT(key, value, expireAfterWrite, timeUnit);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map, long expireAfterWrite, TimeUnit timeUnit) {
        aqCache.putAll(map, expireAfterWrite, timeUnit);
    }

    @Override
    public CacheResult PUT_ALL(Map<? extends K, ? extends V> map) {
        return aqCache.PUT_ALL(map);
    }

    @Override
    public CacheResult PUT_ALL(Map<? extends K, ? extends V> map, long expireAfterWrite, TimeUnit timeUnit) {
        return aqCache.PUT_ALL(map, expireAfterWrite, timeUnit);
    }

    @Override
    public CacheResult REMOVE(K key) {
        return aqCache.REMOVE(key);
    }

    @Override
    public CacheResult REMOVE_ALL(Set<? extends K> keys) {
        return aqCache.REMOVE_ALL(keys);
    }

    @Override
    public CacheResult PUT_IF_ABSENT(K key, V value, long expireAfterWrite, TimeUnit timeUnit) {
        return aqCache.PUT_IF_ABSENT(key, value, expireAfterWrite, timeUnit);
    }

    @Override
    public void close() {
        aqCache.close();
    }

    @Override
    public Set<K> getKeySet() {
        return aqCache.getKeySet();
    }

    @Override
    public boolean isValueExist(V value) {
        return aqCache.isValueExist(value);
    }

    @Override
    public boolean isKeyExist(K key) {
        return aqCache.isKeyExist(key);
    }

    @Override
    public Collection<V> values() {
        return aqCache.values();
    }
}
