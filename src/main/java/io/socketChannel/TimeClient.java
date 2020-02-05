package io.socketChannel;

import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.DatagramChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@Slf4j
public class TimeClient {

    private static final int DEFAULT_TIME_PORT = 37;
    private static final long DIFF_1900 = 2208988800L;
    protected int port = DEFAULT_TIME_PORT;
    protected List remoteHosts;
    protected DatagramChannel channel;

    public TimeClient(String[] argv) throws Exception {
        if (argv.length == 0) {
            throw new Exception("Usage: [ -p port ] host ...");
        }
        parseArgs(argv);
        this.channel = DatagramChannel.open();
        channel.configureBlocking(false);
    }

    protected InetSocketAddress receivePacket(DatagramChannel channel, ByteBuffer buffer) throws Exception {
        buffer.clear();
        // Receive an unsigned 32-bit, big-endian value
        return ((InetSocketAddress) channel.receive(buffer));
    }

    protected void sendRequests() throws Exception {
        ByteBuffer buffer = ByteBuffer.allocate(1);
        Iterator it = remoteHosts.iterator();
        while (it.hasNext()) {
            InetSocketAddress sa = (InetSocketAddress) it.next();
            log.info("Requesting time from " + sa.getHostName() + ":" + sa.getPort());
            buffer.clear().flip();
            // Fire and forget
            channel.send(buffer, sa);
        }
    }

    public void getReplies() throws Exception {
        // Allocate a buffer to hold a long value
        ByteBuffer longBuffer = ByteBuffer.allocate(8);
        // Assure big-endian (network) byte order
        longBuffer.order(ByteOrder.BIG_ENDIAN);
        // Zero the whole buffer to be sure
        longBuffer.putLong(0, 0);
        // Position to first byte of the low-order 32 bits
        longBuffer.position(4);
        // Slice the buffer; gives view of the low-order 32 bits
        ByteBuffer buffer = longBuffer.slice();
        int expect = remoteHosts.size();
        int replies = 0;
        long beginTime = System.currentTimeMillis();
        while (true) {
            InetSocketAddress sa;
            // 不绑定端口，服务器端判断远端端口，直接发回
            sa = receivePacket(channel, buffer);
            // 如果丢失回复，势必无限阻塞或无限为null
            if (sa == null) {
                if (System.currentTimeMillis() > beginTime + 10000) {
                    break;
                }
                // 设置一个延时，到达一定时间即不进行重试
                log.info("Waiting for replies...");
                Thread.sleep(2000);
                continue;
            }
            buffer.flip();
            replies++;
            printTime(longBuffer.getLong(0), sa);
            if (replies == expect) {
                log.info("All packets answered");
                break;
            }
            // Some replies haven't shown up yet
            log.info("Received " + replies + " of " + expect + " replies");
        }
    }

    protected void printTime(long remote1900, InetSocketAddress sa) {
        // local time as seconds since Jan 1, 1970
        long local = System.currentTimeMillis() / 1000;
        // remote time as seconds since Jan 1, 1970
        long remote = remote1900 - DIFF_1900;
        Date remoteDate = new Date(remote * 1000);
        Date localDate = new Date(local * 1000);
        long skew = remote - local;
        log.info("Reply from " + sa.getHostName() + ":" + sa.getPort());
        log.info(" there: " + remoteDate);
        log.info(" here: " + localDate);
        if (skew == 0) {
            log.info(" skew: none");
        } else if (skew > 0) {
            log.info(skew + " skew: seconds ahead");
        } else {
            log.info((-skew) + " skew: seconds behind");
        }
    }

    protected void parseArgs(String[] argv) {
        remoteHosts = new LinkedList();
        for (int i = 0; i < argv.length; i++) {
            String arg = argv[i];
            // Send client requests to the given port
            if (arg.equals("-p")) {
                i++;
                this.port = Integer.parseInt(argv[i]);
                continue;
            }
            // Create an address object for the hostname
            InetSocketAddress sa = new InetSocketAddress(arg, port);
            // Validate that it has an address
            if (sa.getAddress() == null) {
                log.info("Cannot resolve address: " + arg);
                continue;
            }
            remoteHosts.add(sa);
        }
    }

}
