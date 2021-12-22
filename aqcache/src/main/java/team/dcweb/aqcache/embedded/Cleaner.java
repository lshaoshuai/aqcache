package team.dcweb.aqcache.embedded;

import team.dcweb.aqcache.support.AqCacheExecutor;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Create On 2021/7/19
 *
 * @author hongkun
 * @version 1.0.0
 */
class Cleaner {

    static LinkedList<WeakReference<LinkedHashMapAqCache>> linkedHashMapCaches = new LinkedList<>();

    static {
        ScheduledExecutorService executorService = AqCacheExecutor.defaultExecutor();
        executorService.scheduleWithFixedDelay(() -> run(), 60, 60, TimeUnit.SECONDS);
    }

    static void add(LinkedHashMapAqCache cache) {
        synchronized (linkedHashMapCaches) {
            linkedHashMapCaches.add(new WeakReference<>(cache));
        }
    }

    static void run() {
        synchronized (linkedHashMapCaches) {
            Iterator<WeakReference<LinkedHashMapAqCache>> it = linkedHashMapCaches.iterator();
            while (it.hasNext()) {
                WeakReference<LinkedHashMapAqCache> ref = it.next();
                LinkedHashMapAqCache c = ref.get();
                if (c == null) {
                    it.remove();
                } else {
                    c.cleanExpiredEntry();
                }
            }
        }
    }
}
