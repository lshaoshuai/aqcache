package team.dcweb.aqcache.anno.support;

import team.dcweb.aqcache.AqCache;
import team.dcweb.aqcache.anno.CacheConsts;

/**
 * Create On 2021/7/17
 *
 * @author hongkun
 * @version 1.0.0
 */
public interface CacheManager {
    AqCache getCache(String area, String cacheName);

    default AqCache getCache(String cacheName) {
        return getCache(CacheConsts.DEFAULT_AREA, cacheName);
    }

    static CacheManager defaultManager() {
        return SimpleCacheManager.defaultManager;
    }
}

