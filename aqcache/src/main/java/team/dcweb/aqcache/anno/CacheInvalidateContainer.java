package team.dcweb.aqcache.anno;

import java.lang.annotation.*;

/**
 * Create On 2021/7/17
 *
 * @author hongkun
 * @version 1.0.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CacheInvalidateContainer {

    CacheInvalidate[] value();
}