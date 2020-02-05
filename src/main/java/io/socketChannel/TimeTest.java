package io.socketChannel;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.net.SocketException;

@Slf4j
public class TimeTest {

    private static final int DEFAULT_TIME_PORT = 37;

    @Test
    public void test_client() throws Exception {
        String[] argv = {"-p", "37", "127.0.0.1"};
        TimeClient client = new TimeClient(argv);
        client.sendRequests();
        client.getReplies();
    }

    @Test
    public void test_server() throws Exception {
        int port = DEFAULT_TIME_PORT;
        try {
            TimeServer server = new TimeServer(port);
            server.listen();
        } catch (SocketException e) {
            log.info("Can't bind to port " + port + ", try a different one");
        }
    }

}
