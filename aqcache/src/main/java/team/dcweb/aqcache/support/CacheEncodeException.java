package team.dcweb.aqcache.support;

import team.dcweb.aqcache.exception.CacheException;

/**
 * Create On 2021/7/17
 *
 * @author hongkun
 * @version 1.0.0
 */
public class CacheEncodeException extends CacheException {

    private static final long serialVersionUID = -1768444197009616269L;

    public CacheEncodeException(String message, Throwable cause) {
        super(message, cause);
    }

}