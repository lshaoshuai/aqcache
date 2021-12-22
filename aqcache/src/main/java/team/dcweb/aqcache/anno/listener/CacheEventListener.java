package team.dcweb.aqcache.anno.listener;

/**
 * Create On 2021/7/19
 *
 * @author hongkun
 * @version 1.0.0
 */
public interface CacheEventListener<T> {

    /**
     * event happened
     */
    void onEvent(T t);

    /**
     * eliminate
     */
    void eliminate(T t);
}
