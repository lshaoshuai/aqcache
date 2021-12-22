package team.dcweb.aqcache.event;

import team.dcweb.aqcache.AqCache;
import team.dcweb.aqcache.CacheGetResult;

/**
 * Create On 2021/7/17
 *
 * @author hongkun
 * @version 1.0.0
 */
public class CacheGetEvent extends CacheEvent {

    private long millis;
    private Object key;
    private CacheGetResult result;

    public CacheGetEvent(AqCache aqCache, long millis, Object key, CacheGetResult result) {
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

    public CacheGetResult getResult() {
        return result;
    }

}
