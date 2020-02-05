package io.socketChannel;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

@Slf4j
public class ChannelAccept {
    public static final String GREETING = "Hello I must be going.\r\n";

    Charset latin1 = Charset.forName("UTF-8");
    CharsetDecoder decoder = latin1.newDecoder();

    @Test
    void server_recv_send() throws Exception {
        int port = 9999;
        ByteBuffer buffer = ByteBuffer.wrap(GREETING.getBytes());
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress(port));
        ssc.configureBlocking(false);
        while (true) {
            log.info("Waiting for connections...");
            SocketChannel sc = ssc.accept();
            // no connections, snooze a while
            if (sc == null) {
                Thread.sleep(2000);
            } else {
                log.info("Incoming connection from: " + sc.socket().getRemoteSocketAddress());
//                ByteBuffer buffer1 = ByteBuffer.allocate(5);
//                byte[] data = new byte[100];
//                int len = 0;
//                while (true) {
//                    int len1 = sc.read(buffer1);
//                    if (len1 == -1) {
//                        break;
//                    }
//                    buffer1.flip();
//                    buffer1.get(data, len, len1);
//                    buffer1.clear();
//                    len += len1;
//                }
//                ByteBuffer buffer2 = ByteBuffer.wrap(data, 0, len);
//                log.info("type: {}", buffer2.get());
//                log.info("length: {}", buffer2.getInt());
//                log.info("content: {}", new String(data, 5, len - 5));
                ByteBuffer buffer1 = ByteBuffer.allocate(100);
                while (true) {
                    if (sc.read(buffer1) == -1) {
                        log.info("读取结束");
                        break;
                    }
                    log.info("buffer position: {}", buffer1.position());
                }
                buffer1.flip();
                log.info("type: {}", buffer1.get());
                log.info("length: {}", buffer1.getInt());
                log.info("content: {}", decoder.decode(buffer1).toString());
                // 发俩次
                buffer.rewind();
                sc.write(buffer);
                buffer.rewind();
                sc.write(buffer);
                sc.close();
            }
        }
    }

    @Test
    public void server_simple() throws IOException, InterruptedException {
        int port = 9999;
        InetSocketAddress addr = new InetSocketAddress(port);
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.socket().bind(addr);
        ssc.configureBlocking(false);
        while (true) {
            log.info("Waiting for connections...");
            SocketChannel sc = ssc.accept();
            if (sc == null) { // no connections, snooze a while
                Thread.sleep(2000);
            } else {
                log.info("connection established");
                sc.close();
            }
        }
    }

    @Test
    public void server_udp() throws IOException, InterruptedException {
        DatagramChannel channel = DatagramChannel.open();
        channel.configureBlocking(false);
        DatagramSocket socket = channel.socket();
        socket.bind(new InetSocketAddress(9999));
        // 无需接收，它可以接收来自任意地址的数据包
        // 一个未绑定的channel仍可以使用，动态端口号自动分配
        // 那么是如何知道对端的ip？
        ByteBuffer buffer = ByteBuffer.allocate(100);
        while (true) {
            InetSocketAddress address = (InetSocketAddress) channel.receive(buffer);
            if (address == null) {
                log.info("持续监听中...");
                Thread.sleep(2000);
                continue;
            }
            buffer.flip();
            log.info("recv port: {} content: {}",
                    address.getPort(),
                    decoder.decode(buffer).toString());
            buffer.clear();
        }
    }
}