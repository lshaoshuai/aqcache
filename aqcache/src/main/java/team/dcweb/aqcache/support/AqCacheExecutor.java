package team.dcweb.aqcache.support;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Create On 2021/7/19
 *
 * @author hongkun
 * @version 1.0.0
 */
public class AqCacheExecutor {
    protected static ScheduledExecutorService defaultExecutor;
    protected static ScheduledExecutorService heavyIOExecutor;

    private static int threadCount;

    static {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                if (defaultExecutor != null) {
                    defaultExecutor.shutdownNow();
                }
                if (heavyIOExecutor != null) {
                    heavyIOExecutor.shutdownNow();
                }
            }
        });
    }

    public static ScheduledExecutorService defaultExecutor() {
        if (defaultExecutor != null) {
            return defaultExecutor;
        }
        synchronized (AqCacheExecutor.class) {
            if (defaultExecutor == null) {
                ThreadFactory tf = r -> {
                    Thread t = new Thread(r, "JetCacheDefaultExecutor");
                    t.setDaemon(true);
                    return t;
                };
                defaultExecutor = new ScheduledThreadPoolExecutor(
                        1, tf, new ThreadPoolExecutor.DiscardPolicy());
            }
        }
        return defaultExecutor;
    }

    public static ScheduledExecutorService heavyIOExecutor() {
        if (heavyIOExecutor != null) {
            return heavyIOExecutor;
        }
        synchronized (AqCacheExecutor.class) {
            if (heavyIOExecutor == null) {
                ThreadFactory tf = r -> {
                    Thread t = new Thread(r, "JetCacheHeavyIOExecutor" + threadCount++);
                    t.setDaemon(true);
                    return t;
                };
                heavyIOExecutor = new ScheduledThreadPoolExecutor(
                        10, tf, new ThreadPoolExecutor.DiscardPolicy());
            }
        }
        return heavyIOExecutor;
    }

    public static void setDefaultExecutor(ScheduledExecutorService executor) {
        AqCacheExecutor.defaultExecutor = executor;
    }

    public static void setHeavyIOExecutor(ScheduledExecutorService heavyIOExecutor) {
        AqCacheExecutor.heavyIOExecutor = heavyIOExecutor;
    }
}