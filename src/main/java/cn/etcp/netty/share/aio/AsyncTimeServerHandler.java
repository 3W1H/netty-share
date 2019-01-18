package cn.etcp.netty.share.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

/**
 * @Title: AsyncTimeServerHandler
 * @Package: cn.etcp.netty.share.aio
 * @Description:
 * @author: zhiyong.fan
 * @date: 2018/6/30 20:08
 * @version: V1.0
 */
public class AsyncTimeServerHandler implements Runnable {

    private int                             port;

    private CountDownLatch                  latch;

    private AsynchronousServerSocketChannel serverChannel;

    public AsyncTimeServerHandler(int port) {
        this.port = port;
        latch = new CountDownLatch(1);
        try {
            serverChannel = AsynchronousServerSocketChannel.open();
            serverChannel.bind(new InetSocketAddress(port));
            System.out.println("Server --> The time server is start in port: " + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        // do accept
        serverChannel.accept(this, new AcceptCompletionHandler());
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    class AcceptCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, AsyncTimeServerHandler> {
        @Override
        public void completed(AsynchronousSocketChannel result, AsyncTimeServerHandler attachment) {
            attachment.serverChannel.accept(attachment, this);
            ByteBuffer readBuffer = ByteBuffer.allocate(1024);
            result.read(readBuffer, readBuffer, new ReadCompletionHandler(result));
        }

        @Override
        public void failed(Throwable exc, AsyncTimeServerHandler attachment) {
            exc.printStackTrace();
            attachment.latch.countDown();
        }
    }
}
