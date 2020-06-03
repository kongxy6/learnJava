package io.socket;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class Client {

    @Test
    public static void client_send_recv(int i) throws IOException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("127.0.0.1", 9999), 1000);
        OutputStream writer = socket.getOutputStream();
        String str = "你好，这是我的第一个socket---" + i;
        byte[] data = str.getBytes(StandardCharsets.UTF_8);
        // 可以使用DataOutputStrem包装这个对象，直接写入一个int值
        // 避免直接使用write方法而写入int的最低8位
        writer.write((byte) 6);
        writer.write(data.length >> 24);
        writer.write(data.length >> 16);
        writer.write(data.length >> 8);
        writer.write(data.length);
        writer.write(data);
        // 关闭之后就无法写入
        socket.shutdownOutput();
        // 到达流尾，返回null
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        int count = 0;
        while (true) {
            str = reader.readLine();
            if (str == null) {
                break;
            }
            ++count;
            log.info("recv: {}", str);
        }
        socket.close();
    }

    /**
     * 注意不要使用junit测试，主线程退出会导致子线程死掉
     *
     * @param args
     */
    public static void main1(String[] args) {
        final AtomicInteger atomicInteger = new AtomicInteger(0);
        CountDownLatch count = new CountDownLatch(1);
        for (int i = 0; i < 1; i++) {
            new Thread(() -> {
                try {
                    count.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    client_send_recv(atomicInteger.getAndIncrement());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        count.countDown();
    }

    @Test
    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 9999);
        // 此处会将\n读入，导致不可预测的换行行为
        InputStream reader = System.in;
        while (true) {
            byte[] data = new byte[1024];
            int len = reader.read(data);
            socket.send(new DatagramPacket(data, len, address));
        }
    }

}
