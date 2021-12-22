package team.dcweb.aqcache.event;

import team.dcweb.aqcache.AqCache;

/**
 * Create On 2021/7/17
 *
 * @author hongkun
 * @version 1.0.0
 */
public class CacheLoadEvent extends CacheEvent {
    private final long millis;
    private final Object key;
    private final Object loadedValue;
    private final boolean success;

    public CacheLoadEvent(AqCache aqCache, long millis, Object key, Object loadedValue, boolean success) {
        super(aqCache);
        this.millis = millis;
        this.key = key;
        this.loadedValue = loadedValue;
        this.success = success;
    }

    public long getMillis() {
        return millis;
    }

    public Object getKey() {
        return key;
    }

    public Object getLoadedValue() {
        return loadedValue;
    }

    public boolean isSuccess() {
        return success;
    }
}