package netty.echo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import static netty.proto.Data.Message;
import static netty.proto.Data.ProtocolMessage;

// 实际上静态导入可以导入静态方法和类属性，这是普通导入无法做到的

@Slf4j
public class EchoClientHandler extends
        ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ProtocolMessage message = ProtocolMessage.newBuilder()
                .setType(ProtocolMessage.Type.MESSAGE)
                .setMessage(Message.newBuilder().setId(3).setText("玉麒麟").build())
                .build();
        ctx.writeAndFlush(message);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ProtocolMessage message;
        if (msg instanceof ProtocolMessage) {
            message = (ProtocolMessage) msg;
            if (message.getType() == ProtocolMessage.Type.MESSAGE) {
                Message data = message.getMessage();
                log.info("接收到服务端信息: id = {} , text = {}", data.getId(), data.getText());
            }
        }
        // 已经达到了最后一个handler，就别往tail传了
        // super.channelRead(ctx, msg);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            // 如果写通道处于空闲状态,就发送心跳命令
            if (IdleState.WRITER_IDLE.equals(event.state())) {
                ProtocolMessage message = ProtocolMessage.newBuilder()
                        .setType(ProtocolMessage.Type.BEAT)
                        .build();
                ctx.writeAndFlush(message);
            }
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
                                Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }
}