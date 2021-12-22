package team.dcweb.aqcache;

/**
 * @author hongkun
 * @version 1.0.0
 * @since 1.8
 **/
public interface ProxyAqCache<K, V> extends AqCache<K, V> {
    AqCache<K, V> getTargetCache();

    @Override
    default <T> T unwrap(Class<T> clazz) {
        return getTargetCache().unwrap(clazz);
    }
}
