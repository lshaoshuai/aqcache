package team.dcweb.aqcache.anno.method;

import team.dcweb.aqcache.anno.support.CacheInvalidateAnnoConfig;
import team.dcweb.aqcache.anno.support.CacheUpdateAnnoConfig;
import team.dcweb.aqcache.anno.support.CachedAnnoConfig;

import java.util.List;

/**
 * Create On 2021/7/17
 *
 * @author hongkun
 * @version 1.0.0
 */
public class CacheInvokeConfig {
    private CachedAnnoConfig cachedAnnoConfig;
    private List<CacheInvalidateAnnoConfig> invalidateAnnoConfigs;
    private CacheUpdateAnnoConfig updateAnnoConfig;
    private boolean enableCacheContext;

    private static final CacheInvokeConfig noCacheInvokeConfigInstance = new CacheInvokeConfig();

    public static CacheInvokeConfig getNoCacheInvokeConfigInstance() {
        return noCacheInvokeConfigInstance;
    }

    public CachedAnnoConfig getCachedAnnoConfig() {
        return cachedAnnoConfig;
    }

    public void setCachedAnnoConfig(CachedAnnoConfig cachedAnnoConfig) {
        this.cachedAnnoConfig = cachedAnnoConfig;
    }

    public boolean isEnableCacheContext() {
        return enableCacheContext;
    }

    public void setEnableCacheContext(boolean enableCacheContext) {
        this.enableCacheContext = enableCacheContext;
    }

    public List<CacheInvalidateAnnoConfig> getInvalidateAnnoConfigs() {
        return invalidateAnnoConfigs;
    }

    public void setInvalidateAnnoConfigs(List<CacheInvalidateAnnoConfig> invalidateAnnoConfigs) {
        this.invalidateAnnoConfigs = invalidateAnnoConfigs;
    }

    public CacheUpdateAnnoConfig getUpdateAnnoConfig() {
        return updateAnnoConfig;
    }

    public void setUpdateAnnoConfig(CacheUpdateAnnoConfig updateAnnoConfig) {
        this.updateAnnoConfig = updateAnnoConfig;
    }
}