package team.dcweb.aqcache.anno.method;

import org.springframework.context.ApplicationContext;

/**
 * Create On 2021/7/19
 *
 * @author hongkun
 * @version 1.0.0
 */
public class SpringCacheInvokeContext extends CacheInvokeContext {
    protected ApplicationContext context;

    public SpringCacheInvokeContext(ApplicationContext context) {
        this.context = context;
    }

    public Object bean(String name) {
        return context.getBean(name);
    }


}