package team.dcweb.aqcache.anno.support;

import team.dcweb.aqcache.AqCache;

/**
 * Create On 2021/7/17
 *
 * @author hongkun
 * @version 1.0.0
 */
public interface CacheMonitorManager {
    void addMonitors(String area, String cacheName, AqCache aqCache);
}
