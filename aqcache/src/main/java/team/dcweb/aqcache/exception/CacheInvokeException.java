package team.dcweb.aqcache.exception;

/**
 * @author hongkun
 * @version 1.0.0
 * @since 1.8
 **/
public class CacheInvokeException extends CacheException {

    private static final long serialVersionUID = -9002505061387176702L;

    public CacheInvokeException(String message, Throwable cause) {
        super(message, cause);
    }

    public CacheInvokeException(Throwable cause) {
        super(cause);
    }

}
