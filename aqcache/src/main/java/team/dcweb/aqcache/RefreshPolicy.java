package team.dcweb.aqcache;

import java.util.concurrent.TimeUnit;

/**
 * @author hongkun
 * @version 1.0.0
 * @since 1.8
 **/
public class RefreshPolicy implements Cloneable {

    private long refreshMillis;
    private long stopRefreshAfterLastAccessMillis;
    private long refreshLockTimeoutMillis = 60 * 1000;

    public RefreshPolicy() {
    }

    public static RefreshPolicy newPolicy(long time, TimeUnit timeUnit) {
        RefreshPolicy p = new RefreshPolicy();
        p.refreshMillis = timeUnit.toMillis(time);
        return p;
    }

    public RefreshPolicy stopRefreshAfterLastAccess(long time, TimeUnit timeUnit) {
        this.stopRefreshAfterLastAccessMillis = timeUnit.toMillis(time);
        return this;
    }

    public RefreshPolicy refreshLockTimeout(long time, TimeUnit timeUnit) {
        this.refreshLockTimeoutMillis = timeUnit.toMillis(time);
        return this;
    }

    @Override
    public RefreshPolicy clone() {
        try {
            return (RefreshPolicy) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public long getRefreshMillis() {
        return refreshMillis;
    }

    public void setRefreshMillis(long refreshMillis) {
        this.refreshMillis = refreshMillis;
    }

    public long getStopRefreshAfterLastAccessMillis() {
        return stopRefreshAfterLastAccessMillis;
    }

    public void setStopRefreshAfterLastAccessMillis(long stopRefreshAfterLastAccessMillis) {
        this.stopRefreshAfterLastAccessMillis = stopRefreshAfterLastAccessMillis;
    }

    public long getRefreshLockTimeoutMillis() {
        return refreshLockTimeoutMillis;
    }

    public void setRefreshLockTimeoutMillis(long refreshLockTimeoutMillis) {
        this.refreshLockTimeoutMillis = refreshLockTimeoutMillis;
    }
}