package team.dcweb.aqcache.anno.listener;

import team.dcweb.aqcache.anno.CacheConsts;

/**
 * Create On 2022/3/31
 *
 * @author hongkun
 * @version 1.0.0
 */
public abstract class AbstractCacheSetting {

    public int getLocalLimit() {
        return CacheConsts.UNDEFINED_INT;
    }

    public int getLocalExpire() {
        return CacheConsts.UNDEFINED_INT;
    }
}
