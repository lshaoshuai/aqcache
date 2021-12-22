package team.dcweb.aqcache.anno.config;

import team.dcweb.aqcache.anno.field.CreateAqCacheAnnotationBeanPostProcessor;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Create On 2021/7/19
 *
 * @author hongkun
 * @version 1.0.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({AqCommonConfiguration.class, CreateAqCacheAnnotationBeanPostProcessor.class})
public @interface EnableCreateAqCacheAnnotation {
}