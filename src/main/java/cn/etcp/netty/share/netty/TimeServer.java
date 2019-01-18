package cn.etcp.netty.share.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @Title: TimeServer
 * @Package: cn.etcp.netty.share.netty
 * @Description:
 * @author: zhiyong.fan
 * @date: 2018/7/1 3:47
 * @version: V1.0
 */
public class TimeServer {

    private static final int PORT = 9999;

    public static void main(String[] args) {
        // step 1: config the nio thread group of server endpoint
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // step 2: create ServerBootstrap
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            // step 3: config the ServerBootstrap
            serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024).childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
                        socketChannel.pipeline().addLast(new StringDecoder());

                        // socketChannel.pipeline().addLast(new TimeServerHandler());
                        // socketChannel.pipeline().addLast(new TimeServerHandler_V2());
                        socketChannel.pipeline().addLast(new TimeServerHandler_V3());
                    }
                });

            // step 4: bind serverBootstrap to the port
            ChannelFuture future = serverBootstrap.bind(PORT).sync();
            System.out.println("Server --> The time server is start in port: " + PORT);
            // step 5:
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // step 6: shutdown the eventGroup
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
