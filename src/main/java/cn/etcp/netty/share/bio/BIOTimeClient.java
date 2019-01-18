package cn.etcp.netty.share.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @Title: AIOTimeClient
 * @Package: cn.etcp.netty.share.bio
 * @Description:
 * @author: zhiyong.fan
 * @date: 2018/6/30 9:45
 * @version: V1.0
 */
public class BIOTimeClient {

    private static final String IP   = "127.0.0.1";

    private static final int    PORT = 8888;

    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket(IP, PORT);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            out.println("QUERY TIME ORDER");
            System.out.println("Client --> Send order to server succeed.");
            String response = in.readLine();
            System.err.println("Server Response --> Now is:" + response);
        }
    }
}
