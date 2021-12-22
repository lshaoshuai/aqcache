package team.dcweb.aqcache;

/**
 * Create On 2021/7/17
 *
 * @author hongkun
 * @version 1.0.0
 */
public interface CacheBuilder {
    <K, V> AqCache<K, V> buildCache();
}
