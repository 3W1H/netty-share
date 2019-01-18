package cn.etcp.netty.share.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.time.Instant;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * @Title: MultiplexerTimeServer
 * @Package: cn.etcp.netty.share.nio
 * @Description:
 * @author: zhiyong.fan
 * @date: 2018/6/30 12:19
 * @version: V1.0
 */
public class MultiplexerTimeServer implements Runnable {

    private ServerSocketChannel serverChannel;

    private Selector            selector;

    private volatile boolean    stopFlag;

    public MultiplexerTimeServer(int port) {
        try {
            // step 1: create ServerSocketChannel
            serverChannel = ServerSocketChannel.open();
            serverChannel.configureBlocking(false);
            serverChannel.socket().bind(new InetSocketAddress(port), 1024);

            // step 2: create Selector
            selector = Selector.open();

            // step 3: register selector to serverSocketChannel
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("Server --> The time server is start in port: " + port);
        } catch (IOException e) {
            e.printStackTrace();
            // stop();
            System.exit(1);
        }
    }

    public void stop() {
        this.stopFlag = true;
    }

    @Override
    public void run() {
        while (!stopFlag) {
            try {
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                SelectionKey key;
                while (iterator.hasNext()) {
                    key = iterator.next();
                    try {
                        // handle the request data  --> dispatch event
                        handlerInput(key);
                    } catch (IOException e) {
                        if (key != null) {
                            key.cancel();
                            if (key.channel() != null) {
                                key.channel().close();
                            }
                        }
                    }
                    iterator.remove();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // close the selector
        if (selector != null) {
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handlerInput(SelectionKey key) throws IOException {
        if (key.isValid()) {
            // deal the accept request
            if (key.isAcceptable()) {
                ServerSocketChannel server = (ServerSocketChannel) key.channel();
                SocketChannel client = server.accept();
                client.configureBlocking(false);
                client.register(selector, SelectionKey.OP_READ);
            }
            // deal the read request
            if (key.isReadable()) {
                SocketChannel client = (SocketChannel) key.channel();
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                int readBytes = client.read(readBuffer);
                if (readBytes > 0) {
                    // get the request body
                    readBuffer.flip();
                    byte[] bytes = new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);
                    String body = new String(bytes, "UTF-8");
                    System.err.println("The time server receive order: " + body);
                    String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? Date.from(Instant.now()).toString()
                        : "BAD ORDER";
                    doWrite(client, currentTime);
                } else if (readBytes < 0) {
                    // the opposite endpoint closed
                    key.cancel();
                    client.close();
                } else {
                    // ignore the non-input
                }
            }
        }
    }

    private void doWrite(SocketChannel client, String response) throws IOException {
        if (response != null && response.trim().length() > 0) {
            byte[] bytes = response.getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();
            client.write(writeBuffer);
        }
    }
}
