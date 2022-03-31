package team.dcweb.aqcache.anno.support;

import team.dcweb.aqcache.RefreshPolicy;
import team.dcweb.aqcache.anno.AqCacheType;
import team.dcweb.aqcache.anno.listener.CacheEventListener;
import team.dcweb.aqcache.anno.listener.CacheSetting;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * Create On 2021/7/17
 *
 * @author hongkun
 * @version 1.0.0
 */
public class CachedAnnoConfig extends CacheAnnoConfig {

    private boolean enabled;
    private TimeUnit timeUnit;
    private long expire;
    private long localExpire;
    private AqCacheType aqCacheType;
    private int localLimit;
    private boolean cacheNullValue;
    private String serialPolicy;
    private String keyConvertor;
    private String postCondition;
    private CacheEventListener cacheEventListener;
    private CacheSetting cacheSetting;
    private double limitMemory;

    private Function<Object, Boolean> postConditionEvaluator;
    private RefreshPolicy refreshPolicy;
    private PenetrationProtectConfig penetrationProtectConfig;

    public boolean isEnabled() {
        return enabled;
    }

    public long getExpire() {
        return expire;
    }

    public AqCacheType getCacheType() {
        return aqCacheType;
    }

    public int getLocalLimit() {
        return localLimit;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }

    public void setCacheType(AqCacheType aqCacheType) {
        this.aqCacheType = aqCacheType;
    }

    public void setLocalLimit(int localLimit) {
        this.localLimit = localLimit;
    }

    public boolean isCacheNullValue() {
        return cacheNullValue;
    }

    public void setCacheNullValue(boolean cacheNullValue) {
        this.cacheNullValue = cacheNullValue;
    }

    public String getSerialPolicy() {
        return serialPolicy;
    }

    public void setSerialPolicy(String serialPolicy) {
        this.serialPolicy = serialPolicy;
    }

    public String getKeyConvertor() {
        return keyConvertor;
    }

    public void setKeyConvertor(String keyConvertor) {
        this.keyConvertor = keyConvertor;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }


    public String getPostCondition() {
        return postCondition;
    }

    public void setPostCondition(String postCondition) {
        this.postCondition = postCondition;
    }

    public Function<Object, Boolean> getPostConditionEvaluator() {
        return postConditionEvaluator;
    }

    public void setPostConditionEvaluator(Function<Object, Boolean> postConditionEvaluator) {
        this.postConditionEvaluator = postConditionEvaluator;
    }

    public RefreshPolicy getRefreshPolicy() {
        return refreshPolicy;
    }

    public void setRefreshPolicy(RefreshPolicy refreshPolicy) {
        this.refreshPolicy = refreshPolicy;
    }

    public PenetrationProtectConfig getPenetrationProtectConfig() {
        return penetrationProtectConfig;
    }

    public void setPenetrationProtectConfig(PenetrationProtectConfig penetrationProtectConfig) {
        this.penetrationProtectConfig = penetrationProtectConfig;
    }

    public long getLocalExpire() {
        return localExpire;
    }

    public void setLocalExpire(long localExpire) {
        this.localExpire = localExpire;
    }

    public CacheEventListener getCacheEventListener() {
        return cacheEventListener;
    }

    public void setCacheEventListener(CacheEventListener cacheEventListener) {
        this.cacheEventListener = cacheEventListener;
    }

    public CacheSetting getCacheSetting() {
        return cacheSetting;
    }

    public void setCacheSetting(CacheSetting cacheSetting) {
        this.cacheSetting = cacheSetting;
    }

    public double getLimitMemory() {
        return limitMemory;
    }

    public void setLimitMemory(double limitMemory) {
        this.limitMemory = limitMemory;
    }
}
