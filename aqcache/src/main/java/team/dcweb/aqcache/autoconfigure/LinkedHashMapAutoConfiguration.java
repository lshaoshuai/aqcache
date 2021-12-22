package team.dcweb.aqcache.autoconfigure;

import team.dcweb.aqcache.CacheBuilder;
import team.dcweb.aqcache.embedded.LinkedHashMapAqCacheBuilder;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

/**
 * Created on 2016/12/2.
 *
 * @author <a href="mailto:areyouok@gmail.com">huangli</a>
 */
@Component
@Conditional(LinkedHashMapAutoConfiguration.LinkedHashMapCondition.class)
public class LinkedHashMapAutoConfiguration extends EmbeddedCacheAutoInit {
    public LinkedHashMapAutoConfiguration() {
        super("linkedhashmap");
    }

    @Override
    protected CacheBuilder initCache(ConfigTree ct, String cacheAreaWithPrefix) {
        LinkedHashMapAqCacheBuilder builder = LinkedHashMapAqCacheBuilder.createLinkedHashMapCacheBuilder();
        parseGeneralConfig(builder, ct);
        return builder;
    }

    public static class LinkedHashMapCondition extends AqCacheCondition {
        public LinkedHashMapCondition() {
            super("linkedhashmap");
        }
    }
}
