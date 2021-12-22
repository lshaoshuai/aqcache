package team.dcweb.aqcache.embedded;

import team.dcweb.aqcache.CacheConfig;
import team.dcweb.aqcache.anno.CacheConsts;
import team.dcweb.aqcache.anno.listener.CacheEventListener;

/**
 * Create On 2021/7/17
 *
 * @author hongkun
 * @version 1.0.0
 */
public class EmbeddedCacheConfig<K, V> extends CacheConfig<K, V> {
    private int limit = CacheConsts.DEFAULT_LOCAL_LIMIT;

    private CacheEventListener cacheEventListener;

    private double limitMemory = CacheConsts.LIMIT_MEMORY;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public CacheEventListener getCacheEventListener() {
        return cacheEventListener;
    }

    public void setCacheEventListener(CacheEventListener cacheEventListener) {
        this.cacheEventListener = cacheEventListener;
    }

    public double getLimitMemory() {
        return limitMemory;
    }

    public void setLimitMemory(double limitMemory) {
        this.limitMemory = limitMemory;
    }
}

