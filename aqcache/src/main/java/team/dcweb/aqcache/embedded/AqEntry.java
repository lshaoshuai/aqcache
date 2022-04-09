package team.dcweb.aqcache.embedded;

import java.util.Map;
import java.util.Objects;

/**
 * Create On 2022/4/10
 *
 * @author hongkun
 * @version 1.0.0
 */
public class AqEntry<K, V> implements Map.Entry<K, V> {

    AqEntry() {

    }

    K key;

    V value;

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public V setValue(V value) {
        return value;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public void put(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public final int hashCode() {
        return Objects.hashCode(key) ^ Objects.hashCode(value);
    }

    @Override
    public final boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof Map.Entry) {
            Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
            return Objects.equals(key, e.getKey()) &&
                    Objects.equals(value, e.getValue());
        }
        return false;
    }

    @Override
    public String toString() {
        return key + " : " + value;
    }
}
