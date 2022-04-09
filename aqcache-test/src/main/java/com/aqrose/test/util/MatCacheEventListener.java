package com.aqrose.test.util;

import org.opencv.core.Mat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import team.dcweb.aqcache.anno.listener.AbstractCacheEventListener;
import team.dcweb.aqcache.embedded.AqEntry;

/**
 * Create On 2021/7/19
 *
 * @author hongkun
 * @version 1.0.0
 */
public class MatCacheEventListener extends AbstractCacheEventListener<AqEntry<String, Mat>> {

    private static Logger logger = LoggerFactory.getLogger(MatCacheEventListener.class);

    @Override
    public void onEvent(AqEntry<String, Mat> eldest) {
        logger.info("release : " + eldest);
        eldest.getValue().release();
    }
}
