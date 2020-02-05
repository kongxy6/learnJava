package io.selector;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

@Slf4j
public class GroupChatClient {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 6667;
    private Selector selector;
    private SocketChannel socketChannel;
    private String userName;

    public GroupChatClient() {
        try {
            selector = Selector.open();
            // 打开的时候连接可以获取本地地址，但是使用connect就获取不到
            socketChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
            socketChannel.configureBlocking(false).register(selector, SelectionKey.OP_READ);
            SocketAddress address = socketChannel.getLocalAddress();
            userName = address.toString().substring(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        GroupChatClient client = new GroupChatClient();
        new Thread() {
            @Override
            public void run() {
                client.readMsg();
            }
        }.start();

        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String msg = scanner.nextLine();
            client.sendMsg(msg);
        }
    }

    public void sendMsg(String msg) {
        msg = userName + " 说: " + msg;
        try {
            socketChannel.write(ByteBuffer.wrap(msg.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readMsg() {
        try {
            while (true) {
                int count = selector.select();
                if (count > 0) {
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        if (key.isReadable()) {
                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            socketChannel.read(buffer);
                            log.info(new String(buffer.array(), 0, buffer.position()));
                        }
                        iterator.remove();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
