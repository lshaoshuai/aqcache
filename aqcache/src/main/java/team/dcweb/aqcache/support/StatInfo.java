package team.dcweb.aqcache.support;

import java.util.List;

/**
 * Create On 2021/7/17
 *
 * @author hongkun
 * @version 1.0.0
 */
public class StatInfo {
    private List<CacheStat> stats;
    private long startTime;
    private long endTime;

    public List<CacheStat> getStats() {
        return stats;
    }

    public void setStats(List<CacheStat> stats) {
        this.stats = stats;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
}