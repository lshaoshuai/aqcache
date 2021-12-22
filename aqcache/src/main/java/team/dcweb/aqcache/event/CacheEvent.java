package team.dcweb.aqcache.event;

import team.dcweb.aqcache.AqCache;

/**
 * @author hongkun
 * @version 1.0.0
 * @since 1.8
 * The CacheEvent is used in single JVM while CacheMessage used for distributed message.
 **/
public class CacheEvent {

    protected AqCache aqCache;

    public CacheEvent(AqCache aqCache) {
        this.aqCache = aqCache;
    }

    public AqCache getCache() {
        return aqCache;
    }

}