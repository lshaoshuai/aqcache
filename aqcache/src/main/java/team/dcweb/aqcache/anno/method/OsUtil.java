package team.dcweb.aqcache.anno.method;

import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;

/**
 * Create On 2021/7/20
 *
 * @author hongkun
 * @version 1.0.0
 */
public class OsUtil {

    public static double getMemUsage() {
        SystemInfo systemInfo = new SystemInfo();
        GlobalMemory memory = systemInfo.getHardware().getMemory();
        long totalByte = memory.getTotal();
        long availableByte = memory.getAvailable();
        return (totalByte - availableByte) * 1.0 / totalByte;
    }
}
