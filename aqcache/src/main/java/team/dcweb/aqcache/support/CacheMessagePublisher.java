package team.dcweb.aqcache.support;

/**
 * Create On 2021/7/17
 *
 * @author hongkun
 * @version 1.0.0
 */
public interface CacheMessagePublisher {
    void publish(String area, String cacheName, CacheMessage cacheMessage);
}

