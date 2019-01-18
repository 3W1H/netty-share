package cn.etcp.netty.share.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Title: AIOTimeServer
 * @Package: cn.etcp.netty.share.bio
 * @Description:
 * @author: zhiyong.fan
 * @date: 2018/6/30 9:13
 * @version: V1.0
 */
public class BIOTimeServer {

    private static final int PORT = 8888;

    public static void main(String[] args) throws IOException {
        // step 1: create ServerSocket
        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("Server --> The time server is start in port: " + PORT);
            // step 2: listening the port
            Socket socket;
            while (true) {
                socket = server.accept();
                new Thread(new TimeServerHandler(socket)).start();
            }
        }
    }
}
