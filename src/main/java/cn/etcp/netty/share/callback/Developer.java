package cn.etcp.netty.share.callback;

/**
 * @Title: Developer
 * @Package: cn.netty.action.callback
 * @Description:
 * @author: zhiyong.fan
 * @date: 2018/6/6 13:56
 * @version: V1.0
 */
public class Developer extends Worker {

    private final Task task;

    public Developer(Task data) {
        this.task = data;
    }

    @Override
    public void doTask(Callback callback) {
        try {
            callback.onFinish(task);
        } catch (Exception e) {
            callback.onDelay(e);
        }
    }
}
