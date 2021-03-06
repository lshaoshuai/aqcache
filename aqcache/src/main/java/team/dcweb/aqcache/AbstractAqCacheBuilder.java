package team.dcweb.aqcache;

import team.dcweb.aqcache.exception.CacheConfigException;
import team.dcweb.aqcache.exception.CacheException;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * Create On 2021/7/17
 *
 * @author hongkun
 * @version 1.0.0
 */
public abstract class AbstractAqCacheBuilder<T extends AbstractAqCacheBuilder<T>> implements CacheBuilder, Cloneable {

    protected CacheConfig config;
    private Function<CacheConfig, AqCache> buildFunc;

    public abstract CacheConfig getConfig();

    protected T self() {
        return (T) this;
    }

    public T buildFunc(Function<CacheConfig, AqCache> buildFunc) {
        this.buildFunc = buildFunc;
        return self();
    }

    protected void beforeBuild() {
    }

    @Deprecated
    public final <K, V> AqCache<K, V> build() {
        return buildCache();
    }

    @Override
    public final <K, V> AqCache<K, V> buildCache() {
        if (buildFunc == null) {
            throw new CacheConfigException("no buildFunc");
        }
        beforeBuild();
        CacheConfig c = getConfig().clone();
        AqCache<K, V> aqCache = buildFunc.apply(c);
        if (c.getLoader() != null) {
            if (c.getRefreshPolicy() == null) {
                aqCache = new LoadingAqCache<>(aqCache);
            } else {
                aqCache = new RefreshAqCache<>(aqCache);
            }
        }
        return aqCache;
    }

    @Override
    public Object clone() {
        AbstractAqCacheBuilder copy = null;
        try {
            copy = (AbstractAqCacheBuilder) super.clone();
            copy.config = getConfig().clone();
            return copy;
        } catch (CloneNotSupportedException e) {
            throw new CacheException(e);
        }
    }

    public T keyConvertor(Function<Object, Object> keyConvertor) {
        getConfig().setKeyConvertor(keyConvertor);
        return self();
    }

    public void setKeyConvertor(Function<Object, Object> keyConvertor) {
        getConfig().setKeyConvertor(keyConvertor);
    }

    public T expireAfterAccess(long defaultExpire, TimeUnit timeUnit) {
        getConfig().setExpireAfterAccessInMillis(timeUnit.toMillis(defaultExpire));
        return self();
    }

    public void setExpireAfterAccessInMillis(long expireAfterAccessInMillis) {
        getConfig().setExpireAfterAccessInMillis(expireAfterAccessInMillis);
    }

    public T expireAfterWrite(long defaultExpire, TimeUnit timeUnit) {
        getConfig().setExpireAfterWriteInMillis(timeUnit.toMillis(defaultExpire));
        return self();
    }

    public void setExpireAfterWriteInMillis(long expireAfterWriteInMillis) {
        getConfig().setExpireAfterWriteInMillis(expireAfterWriteInMillis);
    }

    public T addMonitor(CacheMonitor monitor) {
        getConfig().getMonitors().add(monitor);
        return self();
    }

    public void setMonitors(List<CacheMonitor> monitors) {
        getConfig().setMonitors(monitors);
    }

    public T cacheNullValue(boolean cacheNullValue) {
        getConfig().setCacheNullValue(cacheNullValue);
        return self();
    }

    public void setCacheNullValue(boolean cacheNullValue) {
        getConfig().setCacheNullValue(cacheNullValue);
    }

    public <K, V> T loader(CacheLoader<K, V> loader) {
        getConfig().setLoader(loader);
        return self();
    }

    public <K, V> void setLoader(CacheLoader<K, V> loader) {
        getConfig().setLoader(loader);
    }

    public T refreshPolicy(RefreshPolicy refreshPolicy) {
        getConfig().setRefreshPolicy(refreshPolicy);
        return self();
    }

    public void setRefreshPolicy(RefreshPolicy refreshPolicy) {
        getConfig().setRefreshPolicy(refreshPolicy);
    }

    public T cachePenetrateProtect(boolean cachePenetrateProtect) {
        getConfig().setCachePenetrationProtect(cachePenetrateProtect);
        return self();
    }

    public void setCachePenetrateProtect(boolean cachePenetrateProtect) {
        getConfig().setCachePenetrationProtect(cachePenetrateProtect);
    }


}
