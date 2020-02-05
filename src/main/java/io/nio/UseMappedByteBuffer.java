package io.nio;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

public class UseMappedByteBuffer {

    @Test
    void read() {
        File file = new File("mappedFile");
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            FileChannel fileChannel = fileInputStream.getChannel();
            MappedByteBuffer buffer = fileChannel.map(MapMode.READ_ONLY, 0, fileChannel.size());
            char k = buffer.getChar();
            char x = buffer.getChar();
            char y = buffer.getChar();
            int a = buffer.getInt();
            int b = buffer.getInt();
            int c = buffer.getInt();
            System.out.println(a + b + c);
            fileChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    void write() throws IOException {
        File file = new File("mappedFile");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        FileChannel fileChannel = fileOutputStream.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(20);
        buffer.putChar('x');
        buffer.putChar('y');
        buffer.putChar('z');
        buffer.putInt(1);
        buffer.putInt(6);
        buffer.putInt(9);
        buffer.flip();
        fileChannel.write(buffer);
        buffer.clear();
        buffer.putChar('o');
        buffer.flip();
        fileChannel.position(2);
        fileChannel.write(buffer);
        fileChannel.close();

        FileInputStream fileInputStream = new FileInputStream(file);
        fileChannel = fileInputStream.getChannel();
        buffer.clear();
        fileChannel.read(buffer);
        buffer.flip();
        System.out.println(buffer.getChar());
        System.out.println(buffer.getChar());
        System.out.println(buffer.getChar());
        System.out.println(buffer.getInt());
        System.out.println(buffer.getInt());
        System.out.println(buffer.getInt());
        fileChannel.close();
    }

}
