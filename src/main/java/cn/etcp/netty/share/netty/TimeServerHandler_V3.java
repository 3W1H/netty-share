package cn.etcp.netty.share.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Title: TimeServerHandler
 * @Package: cn.etcp.netty.share.netty
 * @Description:
 * @author: zhiyong.fan
 * @date: 2018/7/2 10:15
 * @version: V1.0
 */
public class TimeServerHandler_V3 extends ChannelInboundHandlerAdapter {

    private int counter;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String) msg;

        String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body)
            ? DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now())
            : "BAD ORDER";

        System.err.println(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()) + " The time server receive order: " + body + " ; the counter is : " + ++counter);
        currentTime = counter + ", adafaljfaljfaljfa;ljflajflafafdadfafdaafafaaadss";

        /*if (counter == 2) {
            System.err.println("SLeeping......");
            TimeUnit.MILLISECONDS.sleep(1);
        }*/

        if (counter == 3) {
            currentTime += System.getProperty("line.separator");
        }

        ByteBuf response = Unpooled.copiedBuffer(currentTime.getBytes());
        ctx.write(response);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
