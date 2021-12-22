package team.dcweb.aqcache.embedded;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Create On 2021/7/17
 *
 * @author hongkun
 * @version 1.0.0
 */
public interface InnerMap {
    Object getValue(Object key);

    Map getAllValues(Collection keys);

    void putValue(Object key, Object value);

    void putAllValues(Map map);

    boolean removeValue(Object key);

    boolean putIfAbsentValue(Object key, Object value);

    void removeAllValues(Collection keys);

    Set getKeySet();

    boolean isValueExist(Object value);

    boolean isKeyExist(Object key);

    Collection values();
}