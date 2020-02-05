package io.bio;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Base64;

public class UsePipedStream {

    @Test
    void test() {
        String hello = "hello world";
        PipedInputStream pipedInputStream = new PipedInputStream();
        PipedOutputStream pipedOutputStream = null;
        try {
            pipedOutputStream = new PipedOutputStream(pipedInputStream);
            pipedOutputStream.write(hello.getBytes());
            byte[] bytes = new byte[1024];
            int length = pipedInputStream.read(bytes);
            System.out.println(new String(bytes, 0, length));
            // 在输入结束后，关闭管道
            pipedOutputStream.close();
            if (-1 == pipedInputStream.read(bytes)) {
                System.out.println("读入结束");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pipedOutputStream != null) {
                    pipedOutputStream.close();
                }
                pipedInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    void test1() {
        String hello = "hello world";

        //Original byte[]
        byte[] bytes = {32, 32, 32, 32, 32};

        //Base64 Encoded
        byte[] encoded = Base64.getEncoder().encode(bytes);

        //Base64 Decoded
        byte[] decoded = Base64.getDecoder().decode(encoded);

        //Verify original content
        System.out.println("     ".equals(new String(decoded)));

    }
}
