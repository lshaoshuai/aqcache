package team.dcweb.aqcache.anno.config;

import team.dcweb.aqcache.anno.support.ConfigMap;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;

/**
 * Create On 2021/7/19
 *
 * @author hongkun
 * @version 1.0.0
 */
@Configuration
public class AqCommonConfiguration {
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public ConfigMap aqcacheConfigMap() {
        return new ConfigMap();
    }
}

