package io.socket;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

@Slf4j
public class Server {
    private static final String LINE_SEP = "\r\n";
    private static final String SERVER_ID = "Server: KXY Server";
    private static final String HTTP_HDR =
            "HTTP/1.0 200 OK" + LINE_SEP + SERVER_ID + LINE_SEP;

    @Test
    void main() throws IOException {
        ServerSocket serverSocket = new ServerSocket(9999);
        while (true) {
            Socket socket = serverSocket.accept();
            new Thread(() -> {
                BufferedReader in = null;
                try {
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String str = null;
                while (true) {
                    try {
                        if (in == null) {
                            break;
                        }
                        str = in.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // 客户端输入空，退出程序
                    if ("".equals(str)) {
                        break;
                    }
                    System.out.println(str);
                }
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    @Test
    void server_recv_send() throws IOException {
        ServerSocket serverSocket = new ServerSocket(9999);
        Socket socket = serverSocket.accept();
        DataInputStream stream = new DataInputStream(socket.getInputStream());
        while (true) {
            byte type = stream.readByte();
            int len = stream.readInt();
            byte[] origin = new byte[len];
            stream.readFully(origin);
            // 解码
            String data = new String(origin, StandardCharsets.UTF_8);
            log.debug("type:   " + type + "    len:   " + len + "  data:    " + data);
            break;
        }
        // 在接收到EOF之后，就无法使用输入流了  byte type = stream.readByte();
        DataOutputStream stream1 = new DataOutputStream(socket.getOutputStream());
        // 如果使用writeUTF会在开头加入长度
        stream1.write("Server 接收到了数据\r\n".getBytes());
        stream1.flush();
        socket.close();
    }

    /**
     * @throws IOException
     */
    @Test
    void server_http() throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        Socket socket = serverSocket.accept();
        byte[] response = "{\"kxy\":\"ddd\"}\n".getBytes();
        byte[] response1 = "{\"kxy\":".getBytes();
        byte[] response2 = "\"ddd\"}\n".getBytes();
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
        writer.write(HTTP_HDR + "Content-Type: application/json;charset=utf-8\r\n");
        writer.write("Content-Length: " + response.length + "\n\n" + new String(response1));
        writer.flush();
        writer.write(new String(response2));
        writer.flush();
        // 客户端会判断内容到达Content-Length，而终止读取
        socket.shutdownOutput();
        BufferedReader stream = new BufferedReader(
                new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        String header = stream.readLine();
        log.debug("recv: {}", header);
        int len = 0;
        while (true) {
            header = stream.readLine();
            // 判断长度，跳出循环去读取正文
            String[] contentLength = header.split(": ");
            if (contentLength.length == 1) {
                break;
            } else {
                if ("Content-Length".equals(contentLength[0])) {
                    len = Integer.parseInt(contentLength[1]);
                }
            }
            log.debug("recv: {}", header);
        }
        char[] content = new char[len];
        stream.read(content);
        log.debug("recv: {}", new String(content));
        socket.close();
    }
}
