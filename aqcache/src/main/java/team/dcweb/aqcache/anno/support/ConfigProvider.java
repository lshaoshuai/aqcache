package team.dcweb.aqcache.anno.support;


import team.dcweb.aqcache.support.CacheMessagePublisher;
import team.dcweb.aqcache.support.DefaultCacheNameGenerator;
import team.dcweb.aqcache.support.StatInfo;
import team.dcweb.aqcache.support.StatInfoLogger;

import javax.annotation.Resource;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Create On 2021/7/17
 *
 * @author hongkun
 * @version 1.0.0
 */
public class ConfigProvider extends AbstractLifecycle {

    @Resource
    protected GlobalAqCacheConfig globalAqCacheConfig;

    protected SimpleCacheManager cacheManager;
    protected EncoderParser encoderParser;
    protected KeyConvertorParser keyConvertorParser;
    protected CacheMonitorManager cacheMonitorManager;
    private Consumer<StatInfo> metricsCallback = new StatInfoLogger(false);
    private CacheMessagePublisher cacheMessagePublisher;

    private CacheMonitorManager defaultCacheMonitorManager = new DefaultCacheMonitorManager();

    private CacheContext cacheContext;

    public ConfigProvider() {
        cacheManager = SimpleCacheManager.defaultManager;
        encoderParser = new DefaultEncoderParser();
        keyConvertorParser = new DefaultKeyConvertorParser();
        cacheMonitorManager = defaultCacheMonitorManager;
    }

    @Override
    public void doInit() {
        initDefaultCacheMonitorInstaller();
        cacheContext = newContext();
    }

    protected void initDefaultCacheMonitorInstaller() {
        if (cacheMonitorManager == defaultCacheMonitorManager) {
            DefaultCacheMonitorManager installer = (DefaultCacheMonitorManager) cacheMonitorManager;
            installer.setGlobalCacheConfig(globalAqCacheConfig);
            installer.setMetricsCallback(metricsCallback);
            if (cacheMessagePublisher != null) {
                installer.setCacheMessagePublisher(cacheMessagePublisher);
            }
            installer.init();
        }
    }

    @Override
    public void doShutdown() {
        shutdownDefaultCacheMonitorInstaller();
        cacheManager.rebuild();
    }

    protected void shutdownDefaultCacheMonitorInstaller() {
        if (cacheMonitorManager == defaultCacheMonitorManager) {
            ((DefaultCacheMonitorManager) cacheMonitorManager).shutdown();
        }
    }

    /**
     * Keep this method for backward compatibility.
     * NOTICE: there is no getter for encoderParser.
     */
    public Function<Object, byte[]> parseValueEncoder(String valueEncoder) {
        return encoderParser.parseEncoder(valueEncoder);
    }

    /**
     * Keep this method for backward compatibility.
     * NOTICE: there is no getter for encoderParser.
     */
    public Function<byte[], Object> parseValueDecoder(String valueDecoder) {
        return encoderParser.parseDecoder(valueDecoder);
    }

    /**
     * Keep this method for backward compatibility.
     * NOTICE: there is no getter for keyConvertorParser.
     */
    public Function<Object, Object> parseKeyConvertor(String convertor) {
        return keyConvertorParser.parseKeyConvertor(convertor);
    }

    public CacheNameGenerator createCacheNameGenerator(String[] hiddenPackages) {
        return new DefaultCacheNameGenerator(hiddenPackages);
    }

    protected CacheContext newContext() {
        return new CacheContext(this, globalAqCacheConfig);
    }

    public void setCacheManager(SimpleCacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public SimpleCacheManager getCacheManager() {
        return cacheManager;
    }

    public void setEncoderParser(EncoderParser encoderParser) {
        this.encoderParser = encoderParser;
    }

    public void setKeyConvertorParser(KeyConvertorParser keyConvertorParser) {
        this.keyConvertorParser = keyConvertorParser;
    }

    public CacheMonitorManager getCacheMonitorManager() {
        return cacheMonitorManager;
    }

    public void setCacheMonitorManager(CacheMonitorManager cacheMonitorManager) {
        this.cacheMonitorManager = cacheMonitorManager;
    }

    public GlobalAqCacheConfig getGlobalCacheConfig() {
        return globalAqCacheConfig;
    }

    public void setGlobalCacheConfig(GlobalAqCacheConfig globalAqCacheConfig) {
        this.globalAqCacheConfig = globalAqCacheConfig;
    }

    public CacheContext getCacheContext() {
        return cacheContext;
    }

    public void setMetricsCallback(Consumer<StatInfo> metricsCallback) {
        this.metricsCallback = metricsCallback;
    }

    public void setCacheMessagePublisher(CacheMessagePublisher cacheMessagePublisher) {
        this.cacheMessagePublisher = cacheMessagePublisher;
    }
}
