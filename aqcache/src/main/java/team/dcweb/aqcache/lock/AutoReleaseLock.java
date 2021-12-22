package team.dcweb.aqcache.lock;

/**
 * @author hongkun
 * @version 1.0.0
 * @since 1.8
 **/
public interface AutoReleaseLock extends AutoCloseable {
    /**
     * Release the lock use Java 7 try-with-resources.
     */
    @Override
    void close();
}