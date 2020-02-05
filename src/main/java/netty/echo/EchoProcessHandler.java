package netty.echo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EchoProcessHandler extends ChannelInboundHandlerAdapter {

    EventExecutorGroup group = new DefaultEventExecutorGroup(1);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("新的连接建立 {} ", EchoProcessHandler.class);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        log.info("EchoProcessHandler handlerAdded");
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx,
                            Object msg) throws InterruptedException {
        log.info("EchoProcessHandler channelRead");
    }
}
