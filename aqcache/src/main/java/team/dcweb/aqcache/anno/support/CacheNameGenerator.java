package team.dcweb.aqcache.anno.support;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Create On 2021/7/17
 *
 * @author hongkun
 * @version 1.0.0
 */
public interface CacheNameGenerator {

    String generateCacheName(Method method, Object targetObject);

    String generateCacheName(Field field);
}