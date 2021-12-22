package com.aqrose.test;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import team.dcweb.aqcache.anno.config.EnableCreateAqCacheAnnotation;
import org.opencv.core.Core;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author hongkun
 * @version 1.0.0
 * @since 1.8
 **/
@SpringBootApplication(scanBasePackages = {"com.aqrose.*"})
@EnableCreateAqCacheAnnotation
@EnableCreateCacheAnnotation
public class CacheApplication {

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        new SpringApplicationBuilder(CacheApplication.class).run(args);
    }
}