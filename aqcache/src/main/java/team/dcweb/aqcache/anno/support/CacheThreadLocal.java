package team.dcweb.aqcache.anno.support;

/**
 * Create On 2021/7/17
 *
 * @author hongkun
 * @version 1.0.0
 */
class CacheThreadLocal {

    private int enabledCount = 0;

    int getEnabledCount() {
        return enabledCount;
    }

    void setEnabledCount(int enabledCount) {
        this.enabledCount = enabledCount;
    }
}

