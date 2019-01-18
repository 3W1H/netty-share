package cn.etcp.netty.share.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.Instant;
import java.util.Date;

/**
 * @Title: TimeServerHandler
 * @Package: cn.etcp.netty.share.bio
 * @Description:
 * @author: zhiyong.fan
 * @date: 2018/6/30 9:26
 * @version: V1.0
 */
public class TimeServerHandler implements Runnable {

    private Socket socket;

    public TimeServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            String body;
            String currentTime;
            while (true) {
                body = in.readLine();
                if (body == null) {
                    break;
                }
                System.err.println("Client Request --> The time server receive order:" + body);
                currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? Date.from(Instant.now()).toString()
                        : "BAD ORDER";
                out.println(currentTime);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                socket = null;
            }
        }
    }
}
