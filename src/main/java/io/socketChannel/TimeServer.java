package io.socketChannel;

import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.DatagramChannel;

@Slf4j
public class TimeServer {
    private static final long DIFF_1900 = 2208988800L;
    protected DatagramChannel channel;

    public TimeServer(int port) throws Exception {
        this.channel = DatagramChannel.open();
        this.channel.socket().bind(new InetSocketAddress(port));
        channel.configureBlocking(false);
        log.info("Listening on port " + port + " for time requests");
    }

    public void listen() throws Exception {
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
        while (true) {
            buffer.clear();
            SocketAddress sa = this.channel.receive(buffer);
            if (sa == null) {
                log.info("等待请求... ");
                Thread.sleep(2000);
                continue;
                // defensive programming
            }
            log.info("处理中... ");
            Thread.sleep(5000);
            // Ignore content of received datagram per RFC 868
            log.info("Time request from " + sa);
            log.info("position " + buffer.position());
            buffer.clear();
            // sets pos/limit correctly
            // Set 64-bit value; slice buffer sees low 32 bits
            longBuffer.putLong(0, (System.currentTimeMillis() / 1000) + DIFF_1900);
            log.info("发送了:  " + this.channel.send(buffer, sa) + " B");
        }
    }
}
