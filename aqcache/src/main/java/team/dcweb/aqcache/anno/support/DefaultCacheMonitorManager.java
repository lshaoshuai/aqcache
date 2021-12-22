package team.dcweb.aqcache.anno.support;

import org.springframework.beans.factory.annotation.Autowired;
import team.dcweb.aqcache.AqCache;
import team.dcweb.aqcache.CacheMonitor;
import team.dcweb.aqcache.CacheUtil;
import team.dcweb.aqcache.event.CachePutAllEvent;
import team.dcweb.aqcache.event.CachePutEvent;
import team.dcweb.aqcache.event.CacheRemoveAllEvent;
import team.dcweb.aqcache.event.CacheRemoveEvent;
import team.dcweb.aqcache.support.*;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * Create On 2021/7/17
 *
 * @author hongkun
 * @version 1.0.0
 */
public class DefaultCacheMonitorManager extends AbstractLifecycle implements CacheMonitorManager {

    private DefaultMetricsManager defaultMetricsManager;

    @Resource
    private GlobalAqCacheConfig globalAqCacheConfig;

    @Autowired(required = false)
    private Consumer<StatInfo> metricsCallback;

    @Autowired(required = false)
    private CacheMessagePublisher cacheMessagePublisher;

    @Override
    public void addMonitors(String area, String cacheName, AqCache aqCache) {
        addMetricsMonitor(area, cacheName, aqCache);
        addCacheUpdateMonitor(area, cacheName, aqCache);
    }

    protected void addCacheUpdateMonitor(String area, String cacheName, AqCache aqCache) {
        if (cacheMessagePublisher != null) {
            CacheMonitor monitor = event -> {
                if (event instanceof CachePutEvent) {
                    CacheMessage m = new CacheMessage();
                    CachePutEvent e = (CachePutEvent) event;
                    m.setType(CacheMessage.TYPE_PUT);
                    m.setKeys(new Object[]{e.getKey()});
                    cacheMessagePublisher.publish(area, cacheName, m);
                } else if (event instanceof CacheRemoveEvent) {
                    CacheMessage m = new CacheMessage();
                    CacheRemoveEvent e = (CacheRemoveEvent) event;
                    m.setType(CacheMessage.TYPE_REMOVE);
                    m.setKeys(new Object[]{e.getKey()});
                    cacheMessagePublisher.publish(area, cacheName, m);
                } else if (event instanceof CachePutAllEvent) {
                    CacheMessage m = new CacheMessage();
                    CachePutAllEvent e = (CachePutAllEvent) event;
                    m.setType(CacheMessage.TYPE_PUT_ALL);
                    if (e.getMap() != null) {
                        m.setKeys(e.getMap().keySet().toArray());
                    }
                    cacheMessagePublisher.publish(area, cacheName, m);
                } else if (event instanceof CacheRemoveAllEvent) {
                    CacheMessage m = new CacheMessage();
                    CacheRemoveAllEvent e = (CacheRemoveAllEvent) event;
                    m.setType(CacheMessage.TYPE_REMOVE_ALL);
                    if (e.getKeys() != null) {
                        m.setKeys(e.getKeys().toArray());
                    }
                    cacheMessagePublisher.publish(area, cacheName, m);
                }
            };
            aqCache.config().getMonitors().add(monitor);
        }
    }

    protected void addMetricsMonitor(String area, String cacheName, AqCache aqCache) {
        if (defaultMetricsManager != null) {
            aqCache = CacheUtil.getAbstractCache(aqCache);
            DefaultCacheMonitor monitor = new DefaultCacheMonitor(cacheName);
            aqCache.config().getMonitors().add(monitor);
            defaultMetricsManager.add(monitor);
        }
    }

    @Override
    protected void doInit() {
        initMetricsMonitor();
    }

    protected void initMetricsMonitor() {
        if (globalAqCacheConfig.getStatIntervalMinutes() > 0) {
            defaultMetricsManager = new DefaultMetricsManager(globalAqCacheConfig.getStatIntervalMinutes(),
                    TimeUnit.MINUTES, metricsCallback);
            defaultMetricsManager.start();
        }
    }

    @Override
    protected void doShutdown() {
        shutdownMetricsMonitor();
    }

    protected void shutdownMetricsMonitor() {
        if (defaultMetricsManager != null) {
            defaultMetricsManager.stop();
        }
        defaultMetricsManager = null;
    }

    public void setGlobalCacheConfig(GlobalAqCacheConfig globalAqCacheConfig) {
        this.globalAqCacheConfig = globalAqCacheConfig;
    }

    public void setMetricsCallback(Consumer<StatInfo> metricsCallback) {
        this.metricsCallback = metricsCallback;
    }

    public void setCacheMessagePublisher(CacheMessagePublisher cacheMessagePublisher) {
        this.cacheMessagePublisher = cacheMessagePublisher;
    }

}

