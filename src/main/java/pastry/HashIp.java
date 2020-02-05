package pastry;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

@Slf4j
public class HashIp {

    static final int IP_LENGTH = 24;

    static final int COUNT = 4;

    /**
     * 将ip字符串转为long数字，仅使用16bit
     *
     * @param ip
     * @return
     */
    public static long ipToLong(String ip) {
        String[] ips = ip.split("\\.");
        long ipValue = 0L;
        for (int i = 0, j = IP_LENGTH; i < COUNT; ++i) {
            long v = Long.parseLong(ips[i]);
            ipValue = ipValue | (v << j);
            j -= 8;
        }
        return ipValue;
    }

    /**
     * 哈希ip地址为16位的nodeId，仅支持同一网段/16
     * 待完善
     *
     * @param ip
     * @return
     */
    static int hashIpTo16(long ip) {
        long high = ip >> 16;
        long low = ip & 0xFFFF;
        return (int) (low ^ high);
    }

    /**
     * 将nodeId转为4进制串
     *
     * @param nodeId
     * @return
     */
    static char[] nodeIdTo8(int nodeId) {
        char[] chars = new char[8];
        int i = 0;
        while (i < 8) {
            chars[7 - i] = (char) ((nodeId % 4) + 48);
            nodeId = nodeId / 4;
            i++;
        }
        return chars;
    }

    @Test
    void test() {
        // 3232235521 ~ 3232301055
        long ip = ipToLong("192.168.255.255");
        log.info("ip值: {}", ip);
        Set<Integer> set = new HashSet<>();
        for (long i = ipToLong("172.30.0.1"); i < ipToLong("172.30.255.255"); i++) {
            int nodeId = hashIpTo16(i);
            set.add(nodeId);
        }
        log.info("nodeId size: {}", set.size());
        log.info("ip size: {}", 3232301055L - 3232235521L);
        // assert (3232301055L - 3232235521L) == set.size();
    }

}
