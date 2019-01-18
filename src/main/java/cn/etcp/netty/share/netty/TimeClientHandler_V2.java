package cn.etcp.netty.share.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Title: TimeClientHandler
 * @Package: cn.etcp.netty.share.netty
 * @Description:
 * @author: zhiyong.fan
 * @date: 2018/7/2 11:54
 * @version: V1.0
 */
public class TimeClientHandler_V2 extends ChannelInboundHandlerAdapter {

    private int    counter;

    private byte[] request;

    public TimeClientHandler_V2() {
        request = ("QUERY TIME ORDER" + System.getProperty("line.separator")).getBytes();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf message;
        for (int i = 0; i < 100; i++) {
            message = Unpooled.buffer(request.length);
            message.writeBytes(request);
            ctx.writeAndFlush(message);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf respBuf = (ByteBuf) msg;
        byte[] respBytes = new byte[respBuf.readableBytes()];
        respBuf.readBytes(respBytes);
        String response = new String(respBytes, "UTF-8");
        System.err.println("Now is : " + response + " ; the counter is: " + ++counter);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
