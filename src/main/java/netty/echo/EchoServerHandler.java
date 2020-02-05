package netty.echo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

import static netty.proto.Data.Message;
import static netty.proto.Data.ProtocolMessage;

/**
 * 使用DefaultChannelGroup提交任务，是由哪个线程执行“写入”操作的？
 * 使用EventExecutorGroup，是由哪个线程执行“写入”操作的？
 * 将EventExecutorGroup作为参数构建ctx，是由哪个线程执行“写入”操作的？
 * 如果存在多个功能相同的handler，是如何调用的？ctx.fireChannelRead(msg);是如何从上一个handler传到下一个handler？
 */
@Slf4j
public class EchoServerHandler extends
        ChannelInboundHandlerAdapter {

    ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    EventExecutorGroup group = new DefaultEventExecutorGroup(1);

    int readIdleCount = 0;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("新的连接建立 {} ", EchoServerHandler.class);
        super.channelActive(ctx);
    }

    /**
     * 这种事件不需要往后传，因为是建立ctx时一次性执行的，不同于其它socket事件
     *
     * @param ctx
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        log.info("EchoServerHandler handlerAdded");
        channels.add(ctx.channel());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx,
                            Object msg) throws Exception {
        // 复位空闲读事件，触发空闲事件的线程是否与事件循环线程一致？
        readIdleCount = 0;
        ProtocolMessage message;
        if (msg instanceof ProtocolMessage) {
            message = (ProtocolMessage) msg;
            if (message.getType() == ProtocolMessage.Type.BEAT) {
                log.info("接收到心跳信息...");
            } else if (message.getType() == ProtocolMessage.Type.MESSAGE) {
                Message data = message.getMessage();
                log.info("接收到客户端信息: id = {} , text = {}", data.getId(), data.getText());
                group.execute(() -> {
                    ProtocolMessage returnMsg = ProtocolMessage
                            .newBuilder()
                            .setType(ProtocolMessage.Type.MESSAGE)
                            .setMessage(Message.newBuilder().setId(data.getId()).setText("服务器收到了你的信息...").build())
                            .build();
                    // 根据实际客户端实际请求，做出对应的回复
                    ctx.writeAndFlush(returnMsg);
                });
            }
        }
        super.channelRead(ctx, msg);
    }

//    @Override
//    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
//                .addListener(ChannelFutureListener.CLOSE);
//    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (IdleState.READER_IDLE.equals(event.state())) {
                ++readIdleCount;
                log.info("已经" + 10 * readIdleCount + "秒没有接收到客户端的信息了");
                if (readIdleCount == 3) {
                    ctx.channel().close();
                }
            }
        }
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
                                Throwable cause) {
        log.info("连接已断开...");
        ctx.close();
    }

    @Override
    public String toString() {
        return "this is EchoServerHandler";
    }
}