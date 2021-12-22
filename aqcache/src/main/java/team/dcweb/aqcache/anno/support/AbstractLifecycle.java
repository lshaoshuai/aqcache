package team.dcweb.aqcache.anno.support;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Create On 2021/7/17
 *
 * @author hongkun
 * @version 1.0.0
 */
class AbstractLifecycle {
    private boolean inited;

    @PostConstruct
    public final synchronized void init() {
        if (!inited) {
            doInit();
            inited = true;
        }
    }

    protected void doInit() {
    }

    @PreDestroy
    public final synchronized void shutdown() {
        if (inited) {
            doShutdown();
            inited = false;
        }
    }

    protected void doShutdown() {
    }
}