package cn.etcp.netty.share.bio;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Title: PseudoAsyncTimeServerHandler
 * @Package: cn.etcp.netty.share.bio
 * @Description:
 * @author: zhiyong.fan
 * @date: 2018/6/30 11:30
 * @version: V1.0
 */
public class PseudoAsyncTimeServerHandler {

    private ExecutorService executor;

    public PseudoAsyncTimeServerHandler(int maxPoolSize, int queueSize) {
        executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), maxPoolSize, 120L,
            TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(queueSize));
    }

    public void execute(Runnable task) {
        executor.execute(task);
    }
}
