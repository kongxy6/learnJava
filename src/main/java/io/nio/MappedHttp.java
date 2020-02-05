package io.nio;

import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

public class MappedHttp {
    private static final String OUTPUT_FILE = "MappedHttp.out";
    private static final String LINE_SEP = "\r\n";
    private static final String SERVER_ID = "Server: KXY Server";
    private static final String HTTP_HDR =
            "HTTP/1.0 200 OK" + LINE_SEP + SERVER_ID + LINE_SEP;
    private static final String HTTP_404_HDR =
            "HTTP/1.0 404 Not Found" + LINE_SEP + SERVER_ID + LINE_SEP;
    private static final String MSG_404 =
            "Could not open file: ";

    private static byte[] bytes(String str) throws Exception {
        return str.getBytes(StandardCharsets.UTF_8);
    }

    @Test
    public void main() throws Exception {
        String file = "http.in";
        ByteBuffer header = ByteBuffer.wrap(bytes(HTTP_HDR));
        ByteBuffer dynhdrs = ByteBuffer.allocate(128);
        ByteBuffer[] gather = {header, dynhdrs, null};
        String contentType = "unknown/unknown";
        long contentLength;
        try {
            FileInputStream fis = new FileInputStream(file);
            FileChannel fc = fis.getChannel();
            MappedByteBuffer fileData = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
            gather[2] = fileData;
            contentLength = fc.size();
        } catch (IOException e) {
            ByteBuffer buffer = ByteBuffer.allocate(128);
            String msg = MSG_404 + e + LINE_SEP;
            buffer.put(bytes(msg));
            buffer.flip();
            gather[0] = ByteBuffer.wrap(bytes(HTTP_404_HDR));
            gather[2] = buffer;
            contentLength = buffer.limit();
            contentType = "text/plain";
        }
        StringBuffer sb = new StringBuffer();
        sb.append("Content-Length: ").append(contentLength);
        sb.append(LINE_SEP);
        sb.append("Content-Type: ").append(contentType);
        sb.append(LINE_SEP).append(LINE_SEP);
        dynhdrs.put(bytes(sb.toString()));
        dynhdrs.flip();
        FileOutputStream fos = new FileOutputStream(OUTPUT_FILE);
        FileChannel out = fos.getChannel();
        out.write(gather);
        out.close();
        System.out.println("output written to " + OUTPUT_FILE);
    }

}
