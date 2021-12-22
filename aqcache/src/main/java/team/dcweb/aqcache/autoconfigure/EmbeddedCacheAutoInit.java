package team.dcweb.aqcache.autoconfigure;


import team.dcweb.aqcache.CacheBuilder;
import team.dcweb.aqcache.anno.CacheConsts;
import team.dcweb.aqcache.embedded.EmbeddedAqCacheBuilder;

/**
 * Created on 2016/12/2.
 *
 * @author <a href="mailto:areyouok@gmail.com">huangli</a>
 */
public abstract class EmbeddedCacheAutoInit extends AbstractCacheAutoInit {

    public EmbeddedCacheAutoInit(String... cacheTypes) {
        super(cacheTypes);
    }

    @Override
    protected void parseGeneralConfig(CacheBuilder builder, ConfigTree ct) {
        super.parseGeneralConfig(builder, ct);
        EmbeddedAqCacheBuilder ecb = (EmbeddedAqCacheBuilder) builder;

        ecb.limit(Integer.parseInt(ct.getProperty("limit", String.valueOf(CacheConsts.DEFAULT_LOCAL_LIMIT))));
    }
}
