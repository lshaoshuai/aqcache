package team.dcweb.aqcache.anno.aop;

import team.dcweb.aqcache.anno.method.CacheHandler;
import team.dcweb.aqcache.anno.method.CacheInvokeConfig;
import team.dcweb.aqcache.anno.method.CacheInvokeContext;
import team.dcweb.aqcache.anno.support.ConfigMap;
import team.dcweb.aqcache.anno.support.ConfigProvider;
import team.dcweb.aqcache.anno.support.GlobalAqCacheConfig;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Method;

/**
 * Create On 2021/7/19
 *
 * @author hongkun
 * @version 1.0.0
 */
public class AqCacheInterceptor implements MethodInterceptor, ApplicationContextAware {

    //private static final Logger logger = LoggerFactory.getLogger(JetCacheInterceptor.class);

    @Autowired
    private ConfigMap cacheConfigMap;
    private ApplicationContext applicationContext;
    private GlobalAqCacheConfig globalAqCacheConfig;
    ConfigProvider configProvider;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object invoke(final MethodInvocation invocation) throws Throwable {
        if (configProvider == null) {
            configProvider = applicationContext.getBean(ConfigProvider.class);
        }
        if (configProvider != null && globalAqCacheConfig == null) {
            globalAqCacheConfig = configProvider.getGlobalCacheConfig();
        }
        if (globalAqCacheConfig == null || !globalAqCacheConfig.isEnableMethodCache()) {
            return invocation.proceed();
        }

        Method method = invocation.getMethod();
        Object obj = invocation.getThis();
        CacheInvokeConfig cac = null;
        if (obj != null) {
            String key = CachePointcut.getKey(method, obj.getClass());
            cac = cacheConfigMap.getByMethodInfo(key);
        }

        if (cac == null || cac == CacheInvokeConfig.getNoCacheInvokeConfigInstance()) {
            return invocation.proceed();
        }

        CacheInvokeContext context = configProvider.getCacheContext().createCacheInvokeContext(cacheConfigMap);
        context.setTargetObject(invocation.getThis());
        context.setInvoker(invocation::proceed);
        context.setMethod(method);
        context.setArgs(invocation.getArguments());
        context.setCacheInvokeConfig(cac);
        context.setHiddenPackages(globalAqCacheConfig.getHiddenPackages());
        return CacheHandler.invoke(context);
    }

    public void setCacheConfigMap(ConfigMap cacheConfigMap) {
        this.cacheConfigMap = cacheConfigMap;
    }

}
