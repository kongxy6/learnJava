package io.nio;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.FloatBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

public class NIOFileUtils {

    /**
     * some about Java NIO
     */

    static private final int firstHeaderLength = 2;
    static private final int secondHeaderLength = 4;
    static private final int bodyLength = 6;

    @Test
    void useFloatBuffer() {
        FloatBuffer buffer = FloatBuffer.allocate(10);
        for (int i = 0; i < buffer.capacity(); ++i) {
            float f = (float) Math.sin((((float) i) / 10) * (2 * Math.PI));
            buffer.put(f);
        }
        buffer.flip();
        while (buffer.hasRemaining()) {
            float f = buffer.get();
            System.out.println(f);
        }
    }

    @Test
    void copyFile() throws IOException {
        String infile = "tiktok.mp4";
        String outfile = "tiktok" + System.currentTimeMillis() + ".mp4";
        FileInputStream fIn = new FileInputStream(infile);
        FileOutputStream fOut = new FileOutputStream(outfile);
        FileChannel fCin = fIn.getChannel();
        FileChannel fCout = fOut.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        loop(fCin, fCout, buffer);
        buffer.flip();
        while (buffer.hasRemaining()) {
            fCout.write(buffer);
        }
        fCin.close();
        fCout.close();
    }

    private void loop(FileChannel fCin, FileChannel fCout, ByteBuffer buffer) throws IOException {
        while (true) {
            int r = fCin.read(buffer);
            if (r == -1) {
                break;
            }
            buffer.flip();
            // Tips: flip之后才可以从buffer中get
            // 或者是确保输出完全，直接clear
            fCout.write(buffer);
            buffer.compact();
        }
    }

    @Test
    void childBuffer() {
        ByteBuffer buffer = ByteBuffer.allocate(10);

        for (int i = 0; i < buffer.capacity(); ++i) {
            buffer.put((byte) i);
        }

        buffer.position(3);
        buffer.limit(7);
        ByteBuffer slice = buffer.slice();

        //片段是缓冲区的子缓冲区。不过，片段和缓冲区共享同一个底层数据数组。

        for (int i = 0; i < slice.capacity(); ++i) {
            // 这种get和put是绝对路径的方法
            byte b = slice.get(i);
            b *= 11;
            slice.put(i, b);
        }

        buffer.flip();

        while (buffer.remaining() > 0) {
            System.out.println(buffer.get());
        }

    }

    // 使用直接内存，不具备备份数组
    // TODO 测试一下使用直接内存和普通缓冲区的速度差异
    @Test
    void fastCopyFile() throws IOException {
        String infile = "tiktok.mp4";
        String outfile = "tiktok" + System.currentTimeMillis() + ".mp4";
        FileInputStream fIn = new FileInputStream(infile);
        FileOutputStream fOut = new FileOutputStream(outfile);
        FileChannel fCin = fIn.getChannel();
        FileChannel fCout = fOut.getChannel();
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
        loop(fCin, fCout, buffer);
        fCin.close();
        fOut.close();
    }

    @Test
    void useScatterGather() throws IOException {
        int port = 8080;
        ServerSocketChannel ssc = ServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(port);
        ssc.socket().bind(address);
        int messageLength =
                firstHeaderLength + secondHeaderLength + bodyLength;
        ByteBuffer buffers[] = new ByteBuffer[3];
        buffers[0] = ByteBuffer.allocate(firstHeaderLength);
        buffers[1] = ByteBuffer.allocate(secondHeaderLength);
        buffers[2] = ByteBuffer.allocate(bodyLength);
        SocketChannel sc = ssc.accept();

        ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);
        sc.read(buffer);
        buffer.flip();
        Charset latin1 = Charset.forName("UTF-8");
        CharsetDecoder decoder = latin1.newDecoder();
        CharBuffer charBuffer = decoder.decode(buffer);
        String content = charBuffer.toString();

        String[] contents = content.split("\r\n");

        while (true) {
            // Scatter-read into buffers
            int bytesRead = 0;
            while (bytesRead < messageLength) {
                long r = sc.read(buffers);
                bytesRead += r;

                System.out.println("r " + r);
                for (int i = 0; i < buffers.length; ++i) {
                    ByteBuffer bb = buffers[i];
                    System.out.println((char) (((bb.get(0) & 0xFF) << 8) | (bb.get(1) & 0xFF)));
                    System.out.println("b " + i + " " + bb.position() + " " + bb.limit());
                }
            }
            // Process message here

            // Flip buffers
            for (int i = 0; i < buffers.length; ++i) {
                ByteBuffer bb = buffers[i];
                bb.flip();
            }

            // Scatter-write back out
            long bytesWritten = 0;
            while (bytesWritten < messageLength) {
                long r = sc.write(buffers);
                bytesWritten += r;
            }

            // Clear buffers
            for (int i = 0; i < buffers.length; ++i) {
                ByteBuffer bb = buffers[i];
                bb.clear();
            }

            System.out.println(bytesRead + " " + bytesWritten + " " + messageLength);
        }
    }

    /**
     * 直接映射一个文件，然后使用特定解码器解码数据，而解码后的数据同样存放于一个buffer中。
     *
     * @throws IOException
     */
    @Test
    void useCharsets() throws IOException {
        String inputFile = "sampleIn";
        String outputFile = "sampleOut";

        RandomAccessFile inf = new RandomAccessFile(inputFile, "r");
        RandomAccessFile outf = new RandomAccessFile(outputFile, "rw");

        FileChannel inc = inf.getChannel();
        FileChannel outc = outf.getChannel();
        long inputLength = inc.size();

        // position = 0
        MappedByteBuffer inputData =
                inc.map(FileChannel.MapMode.READ_ONLY, 0, inputLength);

        Charset latin1 = Charset.forName("UTF-8");
        CharsetDecoder decoder = latin1.newDecoder();
        CharsetEncoder encoder = latin1.newEncoder();

        // 使用特定解码器解码字节流，翻译为可识别数据，position = 0
        CharBuffer cb = decoder.decode(inputData);

        // Process char data here
        for (int i = 0; i < cb.length(); ++i) {
            System.out.println(cb.get(i));
        }

        // 这个方法返回的buffer, position = 0, limit = the last byte written.
        ByteBuffer outputData = encoder.encode(cb);

        outc.write(outputData);

        inc.close();
        outc.close();
    }

    @Test
    void useCharsetsToWriteFile() throws IOException {
        Charset latin1 = Charset.forName("GBK");
        CharsetEncoder encoder = latin1.newEncoder();
        String outputFile = "sampleOut";
        FileOutputStream fileOutputStream = new FileOutputStream(new File(outputFile));
        FileChannel fileChannel = fileOutputStream.getChannel();

        char[] chars = {'龘', '龖', '亣', 'r', 't', 'y', 'u', 'i', 'o'};
        CharBuffer cb = CharBuffer.allocate(chars.length);
        cb.put(chars, 0, chars.length);

        cb.flip();

        ByteBuffer outputData = encoder.encode(cb);
        fileChannel.write(outputData);

        fileChannel.close();
    }

    @Test
    public void main() throws CharacterCodingException, UnsupportedEncodingException {
        Charset charset = Charset.forName("GBK");
        CharsetDecoder chdecoder = charset.newDecoder();
        CharsetEncoder chencoder = charset.newEncoder();
        String s = "龘code and Decode Example.";
        ByteBuffer byteBuffer = ByteBuffer.wrap(s.getBytes());
        CharBuffer charBuffer = chdecoder.decode(byteBuffer);
        ByteBuffer newByteBuff = chencoder.encode(charBuffer);
        while (newByteBuff.hasRemaining()) {
            char ch = (char) newByteBuff.get();
            System.out.print(ch);
        }
        newByteBuff.clear();
    }

    /**
     * 间接缓冲区使用备份数组
     */
    @Test
    void test_hasArray() {
        char[] array = new char[1024];
        CharBuffer charBuffer = CharBuffer.wrap(array);
        System.out.println(charBuffer.hasArray());
        charBuffer = CharBuffer.allocate(1024);
        array = charBuffer.array();
        charBuffer.put('b');
        array[1] = 'a';
        System.out.println(array);
        charBuffer.put('c');
        System.out.println(array);
    }

    // 复制缓冲区，共用底层备份数组
    @Test
    void test_copy() {
        CharBuffer buffer = CharBuffer.allocate(8);
        System.out.println(buffer.limit());
        char[] array = buffer.array();
        for (int i = 0; i < buffer.capacity(); ++i) {
            array[i] = 'x';
        }
        buffer.position(3).limit(6).mark().position(5);
        CharBuffer dupeBuffer = buffer.duplicate();
        buffer.clear();
        System.out.println(buffer.position() + " " + buffer.limit());
        dupeBuffer.reset();
        System.out.println(dupeBuffer.position() + " " + dupeBuffer.limit() + " " + dupeBuffer.put('c'));
        System.out.println(dupeBuffer.position() + " " + dupeBuffer.limit());
    }


}
