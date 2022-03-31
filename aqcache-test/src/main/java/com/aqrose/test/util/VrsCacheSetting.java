package com.aqrose.test.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import team.dcweb.aqcache.anno.CacheConsts;
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
    private String limit;

    @Override
    public int getLocalLimit() {
        try {
            return Integer.parseInt(limit);
        } catch (Exception e) {
            System.out.println("获取不到配置文件数量");
        }
        return CacheConsts.UNDEFINED_INT;
    }
}
