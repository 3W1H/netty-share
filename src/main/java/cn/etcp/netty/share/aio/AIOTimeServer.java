package cn.etcp.netty.share.aio;

/**
 * @Title: AIOTimeServer
 * @Package: cn.etcp.netty.share.aio
 * @Description:
 * @author: zhiyong.fan
 * @date: 2018/6/30 20:08
 * @version: V1.0
 */
public class AIOTimeServer {

    private static final int PORT = 8888;

    public static void main(String[] args) {
        AsyncTimeServerHandler timeServer = new AsyncTimeServerHandler(PORT);
        new Thread(timeServer, "AIO-AsyncTimeServer").start();
    }
}
