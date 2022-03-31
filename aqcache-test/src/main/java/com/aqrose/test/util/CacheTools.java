package com.aqrose.test.util;

import org.opencv.core.Mat;
import org.springframework.stereotype.Component;
import team.dcweb.aqcache.AqCache;
import team.dcweb.aqcache.anno.AqCacheType;
import team.dcweb.aqcache.anno.CreateAqCache;

import javax.annotation.Resource;

/**
 * @author hongkun
 * @version 1.0.0
 * @since 1.8
 **/
@Component
public class CacheTools {

    @Resource
    VrsCacheSetting vrsCacheSetting;

    @CreateAqCache(cacheType = AqCacheType.LOCAL, localLimit = 5, method = MatCacheEventListener.class, limitMemory = 0.4, cacheSetting = VrsCacheSetting.class)
    public AqCache<String, Mat> test;
}
