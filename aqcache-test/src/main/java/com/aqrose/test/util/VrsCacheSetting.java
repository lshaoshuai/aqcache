package com.aqrose.test.util;

import team.dcweb.aqcache.anno.listener.AbstractCacheSetting;

/**
 * Create On 2022/3/31
 *
 * @author hongkun
 * @version 1.0.0
 */
public class VrsCacheSetting extends AbstractCacheSetting {
    
    @Override
    public int getLocalLimit() {
        return 100;
    }
}
