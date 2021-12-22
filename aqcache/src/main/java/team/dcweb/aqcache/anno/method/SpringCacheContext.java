package team.dcweb.aqcache.anno.method;

import team.dcweb.aqcache.anno.support.CacheContext;
import team.dcweb.aqcache.anno.support.ConfigMap;
import team.dcweb.aqcache.anno.support.GlobalAqCacheConfig;
import team.dcweb.aqcache.anno.support.SpringConfigProvider;
import org.springframework.context.ApplicationContext;

/**
 * Create On 2021/7/19
 *
 * @author hongkun
 * @version 1.0.0
 */
public class SpringCacheContext extends CacheContext {

    private ApplicationContext applicationContext;

    public SpringCacheContext(SpringConfigProvider configProvider, GlobalAqCacheConfig globalAqCacheConfig, ApplicationContext applicationContext) {
        super(configProvider, globalAqCacheConfig);
        this.applicationContext = applicationContext;
        init();
    }

    @Override
    protected CacheInvokeContext newCacheInvokeContext() {
        return new SpringCacheInvokeContext(applicationContext);
    }

    public void init() {
        if (applicationContext != null) {
            ConfigMap configMap = applicationContext.getBean(ConfigMap.class);
            cacheManager.setCacheCreator((area, cacheName) -> {
                CacheInvokeConfig cic = configMap.getByCacheName(area, cacheName);
                if (cic == null) {
                    throw new IllegalArgumentException("cache definition not found: area=" + area + ",cacheName=" + cacheName);
                }
                return __createOrGetCache(cic.getCachedAnnoConfig(), area, cacheName);
            });
        }
    }
}
