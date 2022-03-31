package team.dcweb.aqcache.autoconfigure;


import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import team.dcweb.aqcache.anno.listener.DefaultCacheSetting;
import team.dcweb.aqcache.anno.support.GlobalAqCacheConfig;
import team.dcweb.aqcache.anno.support.SpringConfigProvider;

/**
 * Created on 2016/11/17.
 *
 * @author <a href="mailto:areyouok@gmail.com">huangli</a>
 */
@Configuration
@ConditionalOnClass(GlobalAqCacheConfig.class)
@ConditionalOnMissingBean(GlobalAqCacheConfig.class)
@EnableConfigurationProperties(AqCacheProperties.class)
@Import({LinkedHashMapAutoConfiguration.class, DefaultCacheSetting.class})
public class AqCacheAutoConfiguration {

    public static final String GLOBAL_AQCACHE_CONFIG_NAME = "globalAqCacheConfig";

    private SpringConfigProvider _springConfigProvider = new SpringConfigProvider();

    private AutoConfigureBeans _autoConfigureBeans = new AutoConfigureBeans();

    private GlobalAqCacheConfig _globalAqCacheConfig;

    @Bean
    @ConditionalOnMissingBean
    public SpringConfigProvider springConfigProviderByAqrose() {
        return _springConfigProvider;
    }

    @Bean
    public AutoConfigureBeans autoConfigureBeansByAqrose() {
        return _autoConfigureBeans;
    }

    @Bean
    public static BeanDependencyManager beanDependencyManagerByAqrose() {
        return new BeanDependencyManager();
    }

    @Bean(name = GLOBAL_AQCACHE_CONFIG_NAME)
    public GlobalAqCacheConfig globalAqCacheConfig(SpringConfigProvider configProvider,
                                                   AutoConfigureBeans autoConfigureBeans,
                                                   AqCacheProperties props) {
        if (_globalAqCacheConfig != null) {
            return _globalAqCacheConfig;
        }
        _globalAqCacheConfig = new GlobalAqCacheConfig();
        _globalAqCacheConfig.setHiddenPackages(props.getHiddenPackages());
        _globalAqCacheConfig.setStatIntervalMinutes(props.getStatIntervalMinutes());
        _globalAqCacheConfig.setAreaInCacheName(props.isAreaInCacheName());
        _globalAqCacheConfig.setPenetrationProtect(props.isPenetrationProtect());
        _globalAqCacheConfig.setEnableMethodCache(props.isEnableMethodCache());
        _globalAqCacheConfig.setLocalCacheBuilders(autoConfigureBeans.getLocalCacheBuilders());
        _globalAqCacheConfig.setRemoteCacheBuilders(autoConfigureBeans.getRemoteCacheBuilders());
        return _globalAqCacheConfig;
    }

}
