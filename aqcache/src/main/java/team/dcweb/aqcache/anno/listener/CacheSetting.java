package team.dcweb.aqcache.anno.listener;

/**
 * Create On 2022/3/31
 *
 * @author hongkun
 * @version 1.0.0
 */
public interface CacheSetting {

    /**
     * @return cache limit
     */
    int getLocalLimit();

    /**
     * @return cache expire
     */
    int getLocalExpire();
}
