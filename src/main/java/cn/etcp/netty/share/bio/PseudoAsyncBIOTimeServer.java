package cn.etcp.netty.share.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Title: PseudoAsyncBIOTimeServer
 * @Package: cn.etcp.netty.share.bio
 * @Description:
 * @author: zhiyong.fan
 * @date: 2018/6/30 11:30
 * @version: V1.0
 */
public class PseudoAsyncBIOTimeServer {

    private static final int PORT = 8888;

    public static void main(String[] args) throws IOException {
        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("Server --> The time server is start in port: " + PORT);
            PseudoAsyncTimeServerHandler handler = new PseudoAsyncTimeServerHandler(50, 10000);
            Socket socket;
            while (true) {
                socket = server.accept();
                handler.execute(new TimeServerHandler(socket));
            }
        }
    }
}
