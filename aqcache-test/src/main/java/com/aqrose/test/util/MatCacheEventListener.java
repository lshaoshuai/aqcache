package com.aqrose.test.util;

import team.dcweb.aqcache.CacheValueHolder;
import team.dcweb.aqcache.anno.listener.AbstractCacheEventListener;
import org.opencv.core.Mat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Create On 2021/7/19
 *
 * @author hongkun
 * @version 1.0.0
 */
public class MatCacheEventListener extends AbstractCacheEventListener<Map.Entry> {

    private static Logger logger = LoggerFactory.getLogger(MatCacheEventListener.class);

    @Override
    public void onEvent(Map.Entry eldest) {
        //todo test
        if (eldest.getValue() instanceof CacheValueHolder) {
            CacheValueHolder h = (CacheValueHolder) eldest.getValue();
            if (h.getValue() instanceof Mat) {
                logger.info("height : " + ((Mat) h.getValue()).height());
                ((Mat) h.getValue()).release();
                logger.info("height : " + ((Mat) h.getValue()).height());
            }
        }
    }
}
