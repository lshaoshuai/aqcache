package team.dcweb.aqcache.event;

import team.dcweb.aqcache.AqCache;
import team.dcweb.aqcache.CacheResult;

import java.util.Map;

/**
 * Create On 2021/7/17
 *
 * @author hongkun
 * @version 1.0.0
 */
public class CachePutAllEvent extends CacheEvent {
    private final long millis;
    /**
     * key, value map.
     */
    private final Map map;
    private final CacheResult result;

    public CachePutAllEvent(AqCache aqCache, long millis, Map map, CacheResult result) {
        super(aqCache);
        this.millis = millis;
        this.map = map;
        this.result = result;
    }

    public long getMillis() {
        return millis;
    }

    public Map getMap() {
        return map;
    }

    public CacheResult getResult() {
        return result;
    }
}
