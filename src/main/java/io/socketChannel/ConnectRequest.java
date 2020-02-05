package io.socketChannel;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SocketChannel;

@Slf4j
public class ConnectRequest {

    String hello = "hello~";

    void doSomething() {
        log.info("doSomething...");
    }

    @Test
    public void client_tcp() throws IOException {
        String host = "localhost";
        int port = 9999;
        InetSocketAddress address = new InetSocketAddress(host, port);
        SocketChannel sc = SocketChannel.open();
        sc.configureBlocking(false);
        log.info("initiating connection...");
        sc.connect(address);
        // 当连接失败后，下一次调用该方法会抛出受检查的异常
        // 指出问题的性质，且通道将成为不可用
        while (!sc.finishConnect()) {
            log.info("连接状态isConnectionPending {}", sc.isConnectionPending());
            doSomething();
        }
        log.info("连接状态isConnected {}", sc.isConnected());
        log.info("connection established");
        sc.close();
    }

    @Test
    public void client_udp() throws IOException {
        String host = "localhost";
        int port = 9999;
        InetSocketAddress address = new InetSocketAddress(host, port);
        DatagramChannel dc = DatagramChannel.open();
        dc.configureBlocking(false);
        ByteBuffer buffer = ByteBuffer.wrap(hello.getBytes());
        // 如果传输队列没有空间，则可能需要重试
        log.info("发送状态 {}", dc.send(buffer, address));
        buffer.rewind();
        log.info("发送状态 {}", dc.send(buffer, address));
        dc.close();
    }

    @Test
    public void client_udp2() throws IOException {
        String host = "localhost";
        int port = 9999;
        InetSocketAddress address = new InetSocketAddress(host, port);
        DatagramChannel dc = DatagramChannel.open();
        dc.configureBlocking(false);
        dc.connect(address);
        ByteBuffer buffer = ByteBuffer.wrap(hello.getBytes());
        log.info("发送状态 {}", dc.write(buffer));
        buffer.rewind();
        log.info("发送状态 {}", dc.write(buffer));
        dc.close();
    }

    @Test
    public void client_http() throws IOException, InterruptedException {
        String host = "localhost";
        int port = 8080;
        InetSocketAddress address = new InetSocketAddress(host, port);
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(address);
        while (!socketChannel.finishConnect()) {
            Thread.sleep(1000);
        }
        ByteBuffer buffer = ByteBuffer.allocate(1000);
        while (true) {
            int count = socketChannel.read(buffer);
            log.info("count++: {}", count);
            if (count == -1) {
                break;
            } else if (count == 0) {
                Thread.sleep(1000);
            }
        }
        log.info("收到字节数: {}", buffer.position());
        buffer.flip();
        socketChannel.write(buffer);
        socketChannel.close();
    }

}
