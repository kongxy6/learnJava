package io.selector;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.concurrent.GuardedBy;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 */
@Slf4j
public class EchoServer {

    public static int PORT_NUMBER = 9999;
    TimeUnit unit = TimeUnit.SECONDS;
    BlockingQueue workQueue = new ArrayBlockingQueue(250);
    /**
     * 需要手动配置一个拒绝任务的处理器，防止直接抛出运行时异常
     */
    ThreadPoolExecutor poolExecutor =
            new ThreadPoolExecutor(5, 5, 3, unit,
                    workQueue, new MyThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
    Lock lock = new ReentrantLock();
    @GuardedBy("lock")
    Set<SocketChannel> selectionKeySet = new HashSet<>();
    Charset latin1 = Charset.forName("UTF-8");
    CharsetDecoder decoder = latin1.newDecoder();
    private ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

    public static void main(String[] argv) throws Exception {
        new EchoServer().go(argv);
    }

    public void go(String[] argv) throws Exception {
        int port = PORT_NUMBER;
        if (argv.length > 0) {
            port = Integer.parseInt(argv[0]);
        }
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.socket().bind(new InetSocketAddress(port));
        Selector selector = Selector.open();
        serverChannel.configureBlocking(false);
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            // This may block for a long time. Upon returning, the
            // selected set contains keys of the ready channels.
            // 如果新开线程去读取channel，在没排干缓冲区的这段时间内，会一直触发select
            // 所以最后还是像netty一样，用一个线程去处理selector下的所有channel
            int n = selector.select(100);
            if (n == 0) {
                continue;
                // nothing to do
            }
            // Get an iterator over the set of selected keys
            Iterator it = selector.selectedKeys().iterator();
            while (it.hasNext()) {
                SelectionKey key = (SelectionKey) it.next();
                if (key.isValid() && key.isAcceptable()) {
                    ServerSocketChannel server = (ServerSocketChannel) key.channel();
                    SocketChannel channel = server.accept();
                    registerChannel(selector, channel, SelectionKey.OP_READ);
                    sayHello(channel);
                }
                    /*
                    还有一种思路，是当进行处理时，将该兴趣从key中移除，当处理完毕，再将其加回去
                    不过需要调用wakeup()唤醒阻塞与select方法的线程，那么selector将带着更新后的keys,
                    进行select
                     */
                if (key.isValid() && key.isReadable()) {
                    lock.lock();
                    if (!selectionKeySet.contains(key.channel())) {
                        selectionKeySet.add((SocketChannel) key.channel());
                        lock.unlock();
                        poolExecutor.execute(new Worker(key));
                    } else {
                        lock.unlock();
                    }
                }
                it.remove();

            }
        }
    }

    protected void registerChannel(Selector selector, SelectableChannel channel, int ops) throws Exception {
        if (channel == null) {
            return;
        }
        channel.configureBlocking(false);
        // Register it with the selector
        SelectionKey key = channel.register(selector, ops);
        key.attach(ByteBuffer.allocate(1000));
    }

    /**
     * 尝试给key attach 一个数据缓冲区，当满足一个数据报格式时， 对其进行处理
     *
     * @param key
     * @throws Exception
     */
    protected void readDataFromSocket(SelectionKey key) throws Exception {
        log.info("触发读取事件...");
        SocketChannel socketChannel = (SocketChannel) key.channel();
        if (!socketChannel.isOpen()) {
            return;
        }
        // Loop while data is available; channel is nonblocking
        try {
            while (true) {
                int c;
                // 如果是缓冲区没空间了，就会返回0; 或者是的确没读到数据
                // 如果已经到达EOS，但没有关闭通道，会一直触发读取事件，除非取消键的读兴趣
                if ((c = socketChannel.read((ByteBuffer) key.attachment())) != -1) {
                    log.info("接收到: {} 字节", c);
                    if (c == 0) {
                        break;
                    }
                } else {
                    log.info("读到了终止符 {} ", c);
                    close(key, socketChannel);
                    break;
                }
            }
        } catch (IOException e) {
            log.info("连接已断开...");
            socketChannel.close();
        }
        /**
         * 这种写法，如果走不到下面这段代码，那么这个channel永远都移除不了
         */
        lock.lock();
        selectionKeySet.remove(socketChannel);
        lock.unlock();
        return;

    }

    private void close(SelectionKey key, SocketChannel socketChannel) throws IOException {
        ByteBuffer buffer = (ByteBuffer) key.attachment();
        buffer.flip();
        log.info("以下信息来自于: {}", socketChannel.getRemoteAddress());
        log.info("type: {}", buffer.get());
        log.info("length: {}", buffer.getInt());
        String content = decoder.decode(buffer).toString();
        log.info("content: {}", content);
        buffer.clear();
        buffer.put(content.getBytes());
        buffer.flip();
        while (buffer.hasRemaining()) {
            socketChannel.write(buffer);
        }
        key.attach(null);
        key.cancel();
        log.info("客户端关闭了连接。。。");
        socketChannel.close();
        log.info("服务端关闭了连接。。。");

    }

    private void sayHello(SocketChannel channel) throws Exception {
        buffer.clear();
        buffer.put("Hi , you have online !\r\n".getBytes());
        buffer.flip();
        channel.write(buffer);
    }

    class MyThreadFactory implements ThreadFactory {

        private final AtomicInteger threadNumber = new AtomicInteger(1);

        private String namePrefix = "echoThreadPool-concurrent.thread-";

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(null, r,
                    namePrefix + threadNumber.getAndIncrement(),
                    0);
        }
    }

    class Worker implements Runnable {

        SelectionKey key;

        public Worker(SelectionKey selectionKey) {
            this.key = selectionKey;
        }

        @Override
        public void run() {
            try {
                readDataFromSocket(key);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}