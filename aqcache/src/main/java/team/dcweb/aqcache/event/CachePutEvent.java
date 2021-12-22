package team.dcweb.aqcache.event;

import team.dcweb.aqcache.AqCache;
import team.dcweb.aqcache.CacheResult;

/**
 * Create On 2021/7/17
 *
 * @author hongkun
 * @version 1.0.0
 */
public class CachePutEvent extends CacheEvent {
    private long millis;
    private Object key;
    private Object value;
    private CacheResult result;

    public CachePutEvent(AqCache aqCache, long millis, Object key, Object value, CacheResult result) {
        super(aqCache);
        this.millis = millis;
        this.key = key;
        this.value = value;
        this.result = result;
    }

    public long getMillis() {
        return millis;
    }

    public Object getKey() {
        return key;
    }

    public CacheResult getResult() {
        return result;
    }

    public Object getValue() {
        return value;
    }

}
