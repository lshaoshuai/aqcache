package team.dcweb.aqcache.event;

import team.dcweb.aqcache.AqCache;
import team.dcweb.aqcache.CacheResult;

/**
 * Create On 2021/7/17
 *
 * @author hongkun
 * @version 1.0.0
 */
public class CacheRemoveEvent extends CacheEvent {

    private long millis;
    private Object key;
    private CacheResult result;

    public CacheRemoveEvent(AqCache aqCache, long millis, Object key, CacheResult result) {
        super(aqCache);
        this.millis = millis;
        this.key = key;
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

}