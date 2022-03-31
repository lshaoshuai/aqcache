package com.aqrose.test.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import team.dcweb.aqcache.anno.listener.AbstractCacheSetting;

/**
 * Create On 2022/3/31
 *
 * @author hongkun
 * @version 1.0.0
 */
@Component
public class VrsCacheSetting extends AbstractCacheSetting {

    @Value("${aqcache.local.default.gerber:}")
    private int limit;

    @Override
    public int getLocalLimit() {
        return limit;
    }
}
