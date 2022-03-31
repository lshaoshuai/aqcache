package team.dcweb.aqcache.anno.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import team.dcweb.aqcache.AqCache;
import team.dcweb.aqcache.anno.CacheConsts;
import team.dcweb.aqcache.anno.EnableCache;
import team.dcweb.aqcache.anno.method.CacheHandler;
import team.dcweb.aqcache.anno.method.CacheInvokeConfig;
import team.dcweb.aqcache.anno.method.CacheInvokeContext;
import team.dcweb.aqcache.embedded.EmbeddedAqCacheBuilder;
import team.dcweb.aqcache.exception.CacheConfigException;

import java.util.function.Supplier;

/**
 * Create On 2021/7/17
 *
 * @author hongkun
 * @version 1.0.0
 */

public class CacheContext {

    private static Logger logger = LoggerFactory.getLogger(CacheContext.class);

    private static ThreadLocal<CacheThreadLocal> cacheThreadLocal = new ThreadLocal<CacheThreadLocal>() {
        @Override
        protected CacheThreadLocal initialValue() {
            return new CacheThreadLocal();
        }
    };

    private ConfigProvider configProvider;
    private GlobalAqCacheConfig globalAqCacheConfig;

    protected SimpleCacheManager cacheManager;

    public CacheContext(ConfigProvider configProvider, GlobalAqCacheConfig globalAqCacheConfig) {
        this.globalAqCacheConfig = globalAqCacheConfig;
        this.configProvider = configProvider;
        cacheManager = configProvider.getCacheManager();
    }

    public CacheInvokeContext createCacheInvokeContext(ConfigMap configMap) {
        CacheInvokeContext c = newCacheInvokeContext();
        c.setCacheFunction((invokeContext, cacheAnnoConfig) -> {
            AqCache aqCache = cacheAnnoConfig.getCache();
            if (aqCache == null) {
                if (cacheAnnoConfig instanceof CachedAnnoConfig) {
                    aqCache = createCacheByCachedConfig((CachedAnnoConfig) cacheAnnoConfig, invokeContext);
                } else if ((cacheAnnoConfig instanceof CacheInvalidateAnnoConfig) || (cacheAnnoConfig instanceof CacheUpdateAnnoConfig)) {
                    CacheInvokeConfig cacheDefineConfig = configMap.getByCacheName(cacheAnnoConfig.getArea(), cacheAnnoConfig.getName());
                    if (cacheDefineConfig == null) {
                        String message = "can't find @Cached definition with area=" + cacheAnnoConfig.getArea()
                                + " name=" + cacheAnnoConfig.getName() +
                                ", specified in " + cacheAnnoConfig.getDefineMethod();
                        CacheConfigException e = new CacheConfigException(message);
                        logger.error("Cache operation aborted because can't find @Cached definition", e);
                        return null;
                    }
                    aqCache = createCacheByCachedConfig(cacheDefineConfig.getCachedAnnoConfig(), invokeContext);
                }
                cacheAnnoConfig.setCache(aqCache);
            }
            return aqCache;
        });
        return c;
    }

    private AqCache createCacheByCachedConfig(CachedAnnoConfig ac, CacheInvokeContext invokeContext) {
        String area = ac.getArea();
        String cacheName = ac.getName();
        if (CacheConsts.isUndefined(cacheName)) {

            cacheName = configProvider.createCacheNameGenerator(invokeContext.getHiddenPackages())
                    .generateCacheName(invokeContext.getMethod(), invokeContext.getTargetObject());
        }
        AqCache aqCache = __createOrGetCache(ac, area, cacheName);
        return aqCache;
    }

    @Deprecated
    public <K, V> AqCache<K, V> getCache(String cacheName) {
        return getCache(CacheConsts.DEFAULT_AREA, cacheName);
    }

    @Deprecated
    public <K, V> AqCache<K, V> getCache(String area, String cacheName) {
        AqCache aqCache = cacheManager.getCacheWithoutCreate(area, cacheName);
        return aqCache;
    }

    public AqCache __createOrGetCache(CachedAnnoConfig cachedAnnoConfig, String area, String cacheName) {
        String fullCacheName = area + "_" + cacheName;
        AqCache aqCache = cacheManager.getCacheWithoutCreate(area, cacheName);
        if (aqCache == null) {
            synchronized (this) {
                aqCache = cacheManager.getCacheWithoutCreate(area, cacheName);
                if (aqCache == null) {
                    if (globalAqCacheConfig.isAreaInCacheName()) {
                        // for compatible reason, if we use default configuration, the prefix should same to that version <=2.4.3
                        aqCache = buildCache(cachedAnnoConfig, area, fullCacheName);
                    } else {
                        aqCache = buildCache(cachedAnnoConfig, area, cacheName);
                    }
                    cacheManager.putCache(area, cacheName, aqCache);
                }
            }
        }
        return aqCache;
    }

    protected AqCache buildCache(CachedAnnoConfig cachedAnnoConfig, String area, String cacheName) {
        AqCache aqCache;
//        if (cachedAnnoConfig.getCacheType() == CacheType.LOCAL) {
//            cache = buildLocal(cachedAnnoConfig, area);
//        } else {
//            cache = buildLocal(cachedAnnoConfig, area);
//        }
        aqCache = buildLocal(cachedAnnoConfig, area);
        aqCache.config().setRefreshPolicy(cachedAnnoConfig.getRefreshPolicy());
        aqCache = new CacheHandler.CacheHandlerRefreshAqCache(aqCache);

        aqCache.config().setCachePenetrationProtect(globalAqCacheConfig.isPenetrationProtect());
        PenetrationProtectConfig protectConfig = cachedAnnoConfig.getPenetrationProtectConfig();
        if (protectConfig != null) {
            aqCache.config().setCachePenetrationProtect(protectConfig.isPenetrationProtect());
            aqCache.config().setPenetrationProtectTimeout(protectConfig.getPenetrationProtectTimeout());
        }

        if (configProvider.getCacheMonitorManager() != null) {
            configProvider.getCacheMonitorManager().addMonitors(area, cacheName, aqCache);
        }
        return aqCache;
    }

    protected AqCache buildLocal(CachedAnnoConfig cachedAnnoConfig, String area) {
        EmbeddedAqCacheBuilder cacheBuilder = (EmbeddedAqCacheBuilder) globalAqCacheConfig.getLocalCacheBuilders().get(area);
        if (cacheBuilder == null) {
            throw new CacheConfigException("no local cache builder: " + area);
        }
        cacheBuilder = (EmbeddedAqCacheBuilder) cacheBuilder.clone();

        if (cachedAnnoConfig.getLocalLimit() != CacheConsts.UNDEFINED_INT) {
            cacheBuilder.setLimit(cachedAnnoConfig.getLocalLimit());
        }
        if (cachedAnnoConfig.getCacheEventListener() != null) {
            cacheBuilder.setCacheEventListener(cachedAnnoConfig.getCacheEventListener());
        }
        if (cachedAnnoConfig.getLimitMemory() > 0) {
            cacheBuilder.setLimitMemory(cachedAnnoConfig.getLimitMemory());
        }
        if (cachedAnnoConfig.getExpire() > 0) {
            cacheBuilder.expireAfterWrite(cachedAnnoConfig.getExpire(), cachedAnnoConfig.getTimeUnit());
        }
        if (!CacheConsts.isUndefined(cachedAnnoConfig.getKeyConvertor())) {
            cacheBuilder.setKeyConvertor(configProvider.parseKeyConvertor(cachedAnnoConfig.getKeyConvertor()));
        }
        cacheBuilder.setCacheNullValue(cachedAnnoConfig.isCacheNullValue());
        return cacheBuilder.buildCache();
    }

    protected CacheInvokeContext newCacheInvokeContext() {
        return new CacheInvokeContext();
    }

    /**
     * Enable cache in current thread, for @Cached(enabled=false).
     *
     * @param callback
     * @see EnableCache
     */
    public static <T> T enableCache(Supplier<T> callback) {
        CacheThreadLocal var = cacheThreadLocal.get();
        try {
            var.setEnabledCount(var.getEnabledCount() + 1);
            return callback.get();
        } finally {
            var.setEnabledCount(var.getEnabledCount() - 1);
        }
    }

    protected static void enable() {
        CacheThreadLocal var = cacheThreadLocal.get();
        var.setEnabledCount(var.getEnabledCount() + 1);
    }

    protected static void disable() {
        CacheThreadLocal var = cacheThreadLocal.get();
        var.setEnabledCount(var.getEnabledCount() - 1);
    }

    protected static boolean isEnabled() {
        return cacheThreadLocal.get().getEnabledCount() > 0;
    }

}
