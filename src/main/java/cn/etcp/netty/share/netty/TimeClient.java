package cn.etcp.netty.share.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @Title: TimeClient
 * @Package: cn.etcp.netty.share.netty
 * @Description:
 * @author: zhiyong.fan
 * @date: 2018/7/2 11:32
 * @version: V1.0
 */
public class TimeClient {

    public static void main(String[] args) {
        // step 1: config client thread group
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
            .handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
                    socketChannel.pipeline().addLast(new StringDecoder());

                    // socketChannel.pipeline().addLast(new TimeClientHandler());
                    // socketChannel.pipeline().addLast(new TimeClientHandler_V2());
                    socketChannel.pipeline().addLast(new TimeClientHandler_V3());
                }
            });
        try {
            ChannelFuture future = bootstrap.connect("127.0.0.1", 9999).sync();

            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}
