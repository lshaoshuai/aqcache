package team.dcweb.aqcache.anno.listener;

/**
 * Create On 2021/7/19
 *
 * @author hongkun
 * @version 1.0.0
 */
public abstract class AbstractCacheEventListener<T> implements CacheEventListener<T> {

    @Override
    public void onEvent(T eldest) {

    }

    @Override
    public void eliminate(T eldest) {

    }

}
