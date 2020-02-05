package netty.echo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import netty.proto.Data;

import java.util.concurrent.TimeUnit;

public class EchoServer {

    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        new EchoServer(65535).start();
    }

    public void start() throws Exception {
        EventLoopGroup children = new NioEventLoopGroup(1);
        EventLoopGroup boss = new NioEventLoopGroup(1);
        try {
            //create ServerBootstrap instance
            ServerBootstrap b = new ServerBootstrap();
            //Specifies NIO transport, local socket address
            //Adds handler to channel pipeline
            b.group(boss, children).channel(NioServerSocketChannel.class).localAddress(port)
                    .handler(new LoggingHandler())
                    .childHandler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new IdleStateHandler(10, 0, 0, TimeUnit.SECONDS))
                                    .addLast(new LengthFieldBasedFrameDecoder(Short.MAX_VALUE, 0, 2, 0, 2))
                                    .addLast("decoder", new ProtobufDecoder(Data.ProtocolMessage.getDefaultInstance()))
                                    .addLast(new LengthFieldPrepender(2))
                                    .addLast(new ProtobufEncoder())
                                    .addLast(new EchoServerHandler())
                                    .addLast(new EchoProcessHandler());
                        }
                    });
            //Binds server, waits for server to close, and releases resources
            ChannelFuture f = b.bind().sync();
            System.out.println(EchoServer.class.getName() + "started and listen on " + f.channel().localAddress());
            f.channel().closeFuture().sync();
        } finally {
            boss.shutdownGracefully().sync();
            children.shutdownGracefully().sync();
        }
    }

}