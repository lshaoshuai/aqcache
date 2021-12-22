package team.dcweb.aqcache.anno.method;

import team.dcweb.aqcache.AqCache;
import team.dcweb.aqcache.anno.support.CacheAnnoConfig;

import java.lang.reflect.Method;
import java.util.function.BiFunction;

/**
 * Create On 2021/7/17
 *
 * @author hongkun
 * @version 1.0.0
 */
public class CacheInvokeContext {
    private Invoker invoker;
    private Method method;
    private Object[] args;
    private CacheInvokeConfig cacheInvokeConfig;
    private Object targetObject;
    private Object result;

    private BiFunction<CacheInvokeContext, CacheAnnoConfig, AqCache> cacheFunction;
    private String[] hiddenPackages;

    public CacheInvokeContext() {
    }


    public void setInvoker(Invoker invoker) {
        this.invoker = invoker;
    }

    public Invoker getInvoker() {
        return invoker;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public void setCacheInvokeConfig(CacheInvokeConfig cacheInvokeConfig) {
        this.cacheInvokeConfig = cacheInvokeConfig;
    }

    public CacheInvokeConfig getCacheInvokeConfig() {
        return cacheInvokeConfig;
    }

    public void setHiddenPackages(String[] hiddenPackages) {
        this.hiddenPackages = hiddenPackages;
    }

    public String[] getHiddenPackages() {
        return hiddenPackages;
    }

    public void setCacheFunction(BiFunction<CacheInvokeContext, CacheAnnoConfig, AqCache> cacheFunction) {
        this.cacheFunction = cacheFunction;
    }

    public BiFunction<CacheInvokeContext, CacheAnnoConfig, AqCache> getCacheFunction() {
        return cacheFunction;
    }

    public Object[] getArgs() {
        return args;
    }

    public Object getTargetObject() {
        return targetObject;
    }

    public void setTargetObject(Object targetObject) {
        this.targetObject = targetObject;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}

