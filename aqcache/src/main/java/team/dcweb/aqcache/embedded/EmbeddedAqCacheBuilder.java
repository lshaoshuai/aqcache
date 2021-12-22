package team.dcweb.aqcache.embedded;

import team.dcweb.aqcache.AbstractAqCacheBuilder;
import team.dcweb.aqcache.anno.listener.CacheEventListener;

/**
 * Create On 2021/7/17
 *
 * @author hongkun
 * @version 1.0.0
 */
public class EmbeddedAqCacheBuilder<T extends EmbeddedAqCacheBuilder<T>> extends AbstractAqCacheBuilder<T> {

    public EmbeddedAqCacheBuilder() {
    }

    public static class EmbeddedAqCacheBuilderImpl extends EmbeddedAqCacheBuilder<EmbeddedAqCacheBuilderImpl> {
    }

    public static EmbeddedAqCacheBuilderImpl createEmbeddedCacheBuilder() {
        return new EmbeddedAqCacheBuilderImpl();
    }

    @Override
    public EmbeddedCacheConfig getConfig() {
        if (config == null) {
            config = new EmbeddedCacheConfig();
        }
        return (EmbeddedCacheConfig) config;
    }

    public T limit(int limit) {
        getConfig().setLimit(limit);
        return self();
    }

    public void setLimit(int limit) {
        getConfig().setLimit(limit);
    }

    public T createCacheEventListener(CacheEventListener cacheEventListener) {
        getConfig().setCacheEventListener(cacheEventListener);
        return self();
    }

    public void setCacheEventListener(CacheEventListener cacheEventListener) {
        getConfig().setCacheEventListener(cacheEventListener);
    }


    public void setLimitMemory(double limitMemory) {
        getConfig().setLimitMemory(limitMemory);
    }
}