package cn.etcp.netty.share.nio;

/**
 * @Title: AIOTimeClient
 * @Package: cn.etcp.netty.share.nio
 * @Description:
 * @author: zhiyong.fan
 * @date: 2018/6/30 13:44
 * @version: V1.0
 */
public class NIOTimeClient {

    public static void main(String[] args) {
        new Thread(new TimeClientHandler("127.0.0.1", 8888), "Multiplexer--NIOTimeClient").start();
    }
}
