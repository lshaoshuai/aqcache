package team.dcweb.aqcache;

import team.dcweb.aqcache.event.CacheEvent;

/**
 * @author hongkun
 * @version 1.0.0
 * @since 1.8
 **/
@FunctionalInterface
public interface CacheMonitor {

    void afterOperation(CacheEvent event);

}
