package team.dcweb.aqcache.anno.listener;

import org.springframework.stereotype.Component;
import team.dcweb.aqcache.anno.CacheConsts;

/**
 * Create On 2022/3/31
 *
 * @author hongkun
 * @version 1.0.0
 */
@Component
public class DefaultCacheSetting extends AbstractCacheSetting {

    @Override
    public int getLocalLimit() {
        return CacheConsts.UNDEFINED_INT;
    }

    @Override
    public int getLocalExpire() {
        return CacheConsts.UNDEFINED_INT;
    }
}
