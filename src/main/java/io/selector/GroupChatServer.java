package io.selector;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

@Slf4j
public class GroupChatServer {
    private static final int PORT = 6667;
    private Selector selector;
    private ServerSocketChannel listenChannel;

    public GroupChatServer() {
        try {
            selector = Selector.open();
            listenChannel = ServerSocketChannel.open();
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            listenChannel.configureBlocking(false).register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new GroupChatServer().listen();
    }

    private void listen() {
        while (true) {
            try {
                int count = selector.select(2000);
                if (count == 0) {
                    continue;
                }
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isAcceptable()) {
                        SocketChannel sc = listenChannel.accept();
                        sc.configureBlocking(false).register(selector, SelectionKey.OP_READ);
                        String remoteAddress = sc.getRemoteAddress().toString().substring(1);
                        log.info("{} 上线了", remoteAddress);
                        transformTo(remoteAddress + " 上线了", sc);
                    }
                    if (key.isReadable()) {
                        read(key);
                    }
                    iterator.remove();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void read(SelectionKey key) {
        SocketChannel sc = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        try {
            int count = sc.read(buffer);
            if (count > 0) {
                String msg = new String(buffer.array(), 0, buffer.position());
                log.info("收到消息: {}", msg);
                transformTo(msg, sc);
            } else if (count == -1) {
                log.info("读到了终止符...");
                // 说明客户端不再发生任何数据了
                // 但是依然会接收数据
            }
        } catch (IOException e) {
            try {
                log.info("read方法触发{}下线", sc.getRemoteAddress());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            downLine(key);
        }
    }

    private void downLine(SelectionKey key) {
        SocketChannel sc = (SocketChannel) key.channel();
        try {
            log.info("{} 下线了", sc.getRemoteAddress());
            key.cancel();
            sc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void transformTo(String msg, SocketChannel self) {
        log.info("服务器转发消消息: {}", msg);
        for (SelectionKey key : selector.keys()) {
            if (key.channel() instanceof SocketChannel) {
                SocketChannel targetChannel = (SocketChannel) key.channel();
                if (targetChannel != self) {
                    ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                    try {
                        // 此时客户端可能已经关闭了连接
                        // 但是缓冲区仍然存在未读的数据?
                        targetChannel.write(buffer);
                    } catch (IOException e) {
                        try {
                            log.info("write方法触发{}下线", targetChannel.getRemoteAddress());
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        downLine(key);
                    }
                }
            }
        }
    }
}
