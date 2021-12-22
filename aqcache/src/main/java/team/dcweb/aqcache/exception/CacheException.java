package team.dcweb.aqcache.exception;

/**
 * @author hongkun
 * @version 1.0.0
 * @since 1.8
 **/
public class CacheException extends RuntimeException {
    private static final long serialVersionUID = -9066209768410752667L;

    public CacheException(String message) {
        super(message);
    }

    public CacheException(String message, Throwable cause) {
        super(message, cause);
    }

    public CacheException(Throwable cause) {
        super(cause);
    }
}