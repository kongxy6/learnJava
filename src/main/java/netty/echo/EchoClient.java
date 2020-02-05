package netty.echo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import netty.proto.Data;

import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import static netty.proto.Data.Message;
import static netty.proto.Data.ProtocolMessage;

public class EchoClient {

    private final String host;
    private final int port;

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        new EchoClient("localhost", 65535).start();
    }

    public void start() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class).remoteAddress(new InetSocketAddress(host, port))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new IdleStateHandler(0, 10, 0, TimeUnit.SECONDS))
                                    .addLast(new LengthFieldBasedFrameDecoder(Short.MAX_VALUE, 0, 2, 0, 2))
                                    .addLast("decoder", new ProtobufDecoder(Data.ProtocolMessage.getDefaultInstance()))
                                    .addLast(new LengthFieldPrepender(2))
                                    .addLast(new ProtobufEncoder())
                                    .addLast(new EchoClientHandler());
                        }
                    });
            ChannelFuture f = b.connect().sync();
            Channel channel = f.channel();
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                String text = scanner.nextLine();
                for (int i = 0; i < 15; i++) {
                    ProtocolMessage message = ProtocolMessage.newBuilder()
                            .setType(ProtocolMessage.Type.MESSAGE)
                            .setMessage(Message.newBuilder().setId(i).setText(text).build())
                            .build();
                    channel.writeAndFlush(message);
                }
            }
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }
}