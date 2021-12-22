package team.dcweb.aqcache.event;

import team.dcweb.aqcache.AqCache;
import team.dcweb.aqcache.CacheResult;

import java.util.Set;

/**
 * Create On 2021/7/17
 *
 * @author hongkun
 * @version 1.0.0
 */
public class CacheRemoveAllEvent extends CacheEvent {
    private final long millis;
    private final Set keys;
    private final CacheResult result;

    public CacheRemoveAllEvent(AqCache aqCache, long millis, Set keys, CacheResult result) {
        super(aqCache);
        this.millis = millis;
        this.keys = keys;
        this.result = result;
    }

    public long getMillis() {
        return millis;
    }

    public Set getKeys() {
        return keys;
    }

    public CacheResult getResult() {
        return result;
    }
}
