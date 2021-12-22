package team.dcweb.aqcache.anno;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * Create On 2021/7/17
 *
 * @author hongkun
 * @version 1.0.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface CachePenetrationProtect {
    boolean value() default true;

    int timeout() default CacheConsts.UNDEFINED_INT;

    TimeUnit timeUnit() default TimeUnit.SECONDS;
}