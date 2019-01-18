package cn.etcp.netty.share.aio;

/**
 * @Title: AIOTimeClient
 * @Package: cn.etcp.netty.share.aio
 * @Description:
 * @author: zhiyong.fan
 * @date: 2018/6/30 21:42
 * @version: V1.0
 */
public class AIOTimeClient {

    public static void main(String[] args) {
        new Thread(new AsyncTimeClientHandler("127.0.0.1", 8888), "AIO-TimeClient").start();
    }
}
