package team.dcweb.aqcache.event;

import team.dcweb.aqcache.AqCache;

import java.util.Map;
import java.util.Set;

/**
 * Create On 2021/7/17
 *
 * @author hongkun
 * @version 1.0.0
 */
public class CacheLoadAllEvent extends CacheEvent {

    private long millis;
    private Set keys;
    private Map loadedValue;
    private boolean success;

    public CacheLoadAllEvent(AqCache aqCache, long millis, Set keys, Map loadedValue, boolean success) {
        super(aqCache);
        this.millis = millis;
        this.keys = keys;
        this.loadedValue = loadedValue;
        this.success = success;
    }

    public long getMillis() {
        return millis;
    }

    public Set getKeys() {
        return keys;
    }

    public Map getLoadedValue() {
        return loadedValue;
    }

    public boolean isSuccess() {
        return success;
    }
}
