package team.dcweb.aqcache.anno.support;

/**
 * Create On 2021/7/17
 *
 * @author hongkun
 * @version 1.0.0
 */
public class CacheInvalidateAnnoConfig extends CacheAnnoConfig {
    private boolean multi;

    public boolean isMulti() {
        return multi;
    }

    public void setMulti(boolean multi) {
        this.multi = multi;
    }
}