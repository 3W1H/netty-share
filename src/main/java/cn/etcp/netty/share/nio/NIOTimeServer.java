package cn.etcp.netty.share.nio;

/**
 * @Title: AIOTimeServer
 * @Package: cn.etcp.netty.share.nio
 * @Description:
 * @author: zhiyong.fan
 * @date: 2018/6/30 12:19
 * @version: V1.0
 */
public class NIOTimeServer {

    private static final int PORT = 8888;

    public static void main(String[] args) {
        MultiplexerTimeServer timeServer = new MultiplexerTimeServer(PORT);
        new Thread(timeServer, "Multiplexer--AIOTimeServer").start();
    }
}
