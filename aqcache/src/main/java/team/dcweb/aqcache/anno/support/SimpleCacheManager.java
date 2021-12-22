package team.dcweb.aqcache.anno.support;

import team.dcweb.aqcache.AqCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

/**
 * Create On 2021/7/17
 *
 * @author hongkun
 * @version 1.0.0
 */
public class SimpleCacheManager implements CacheManager {

    private static final Logger logger = LoggerFactory.getLogger(SimpleCacheManager.class);

    private ConcurrentHashMap<String, ConcurrentHashMap<String, AqCache>> caches = new ConcurrentHashMap<>();
    private BiFunction<String, String, AqCache> cacheCreator;

    static SimpleCacheManager defaultManager = new SimpleCacheManager();

    public SimpleCacheManager() {
    }

    public void rebuild() {
        caches.forEach((area, areaMap) -> {
            areaMap.forEach((cacheName, cache) -> {
                try {
                    cache.close();
                } catch (Exception e) {
                    logger.error("error during close", e);
                }
            });
        });
        caches.clear();
        cacheCreator = null;
    }

    private ConcurrentHashMap<String, AqCache> getCachesByArea(String area) {
        return caches.computeIfAbsent(area, (key) -> new ConcurrentHashMap<>());
    }

    @Override
    public AqCache getCache(String area, String cacheName) {
        ConcurrentHashMap<String, AqCache> areaMap = getCachesByArea(area);
        AqCache c = areaMap.get(cacheName);
        if (c == null && cacheCreator != null) {
            return cacheCreator.apply(area, cacheName);
        } else {
            return c;
        }
    }

    public AqCache getCacheWithoutCreate(String area, String cacheName) {
        ConcurrentHashMap<String, AqCache> areaMap = getCachesByArea(area);
        return areaMap.get(cacheName);
    }

    public void putCache(String area, String cacheName, AqCache aqCache) {
        ConcurrentHashMap<String, AqCache> areaMap = getCachesByArea(area);
        areaMap.put(cacheName, aqCache);
    }

    public void setCacheCreator(BiFunction<String, String, AqCache> cacheCreator) {
        this.cacheCreator = cacheCreator;
    }
}
