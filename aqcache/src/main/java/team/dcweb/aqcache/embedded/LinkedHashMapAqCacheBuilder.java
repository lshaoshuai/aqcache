package team.dcweb.aqcache.embedded;

/**
 * Create On 2021/7/19
 *
 * @author hongkun
 * @version 1.0.0
 */
public class LinkedHashMapAqCacheBuilder<T extends EmbeddedAqCacheBuilder<T>> extends EmbeddedAqCacheBuilder<T> {
    public static class LinkedHashMapAqCacheBuilderImpl extends LinkedHashMapAqCacheBuilder<LinkedHashMapAqCacheBuilderImpl> {
    }

    public static LinkedHashMapAqCacheBuilderImpl createLinkedHashMapCacheBuilder() {
        return new LinkedHashMapAqCacheBuilderImpl();
    }

    protected LinkedHashMapAqCacheBuilder() {
        buildFunc((c) -> new LinkedHashMapAqCache((EmbeddedCacheConfig) c));
    }
}
