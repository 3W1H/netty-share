package cn.etcp.netty.share.callback;

/**
 * @Title: Callback
 * @Package: cn.netty.action.callback
 * @Description:
 * @author: zhiyong.fan
 * @date: 2018/6/6 13:54
 * @version: V1.0
 */
public interface Callback {

    void onFinish(Task task) throws Exception;

    void onDelay(Throwable cause);
}
