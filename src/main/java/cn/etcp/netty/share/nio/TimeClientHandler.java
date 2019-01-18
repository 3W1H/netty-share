package cn.etcp.netty.share.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @Title: TimeClientHandler
 * @Package: cn.etcp.netty.share.nio
 * @Description:
 * @author: zhiyong.fan
 * @date: 2018/6/30 13:44
 * @version: V1.0
 */
public class TimeClientHandler implements Runnable {

    private String           host;

    private int              port;

    private SocketChannel    clientChannel;

    private Selector         selector;

    private volatile boolean stopFlag;

    public TimeClientHandler(String host, int port) {
        this.host = host;
        this.port = port;
        try {
            clientChannel = SocketChannel.open();
            clientChannel.configureBlocking(false);
            selector = Selector.open();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void run() {
        try {
            doConnect();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        // do request
        while (!stopFlag) {
            try {
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> keys = selectionKeys.iterator();
                SelectionKey key;
                while (keys.hasNext()) {
                    key = keys.next();
                    try {
                        handleInput(key);
                    } catch (Exception e) {
                        if (key != null) {
                            key.cancel();
                            if (key.channel() != null) {
                                key.channel().close();
                            }
                        }
                    }
                    keys.remove();
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        // close selector release the resource
        if (selector != null) {
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void doConnect() throws IOException {
        if (clientChannel.connect(new InetSocketAddress(host, port))) {
            clientChannel.register(selector, SelectionKey.OP_READ);
            doWrite(clientChannel);
        } else {
            clientChannel.register(selector, SelectionKey.OP_CONNECT);
        }
    }

    private void handleInput(SelectionKey key) throws IOException {
        if (key.isValid()) {
            SocketChannel client = (SocketChannel) key.channel();
            // handle connection event
            if (key.isConnectable()) {
                if (client.finishConnect()) {
                    client.register(selector, SelectionKey.OP_READ);
                    doWrite(client);
                } else {
                    // connection fail
                    System.exit(1);
                }
            }
            // handle read event
            if (key.isReadable()) {
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                int readBytes = client.read(readBuffer);
                if (readBytes > 0) {
                    readBuffer.flip();
                    byte[] bytes = new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);
                    String body = new String(bytes, "UTF-8");
                    System.err.println("Server Response --> Now is: " + body);
                    this.stopFlag = true;
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

    private void doWrite(SocketChannel client) throws IOException {
        byte[] request = "QUERY TIME ORDER".getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(request.length);
        writeBuffer.put(request);
        writeBuffer.flip();
        client.write(writeBuffer);
        if (!writeBuffer.hasRemaining()) {
            System.out.println("Client --> Send order to server succeed.....");
        }
    }
}
