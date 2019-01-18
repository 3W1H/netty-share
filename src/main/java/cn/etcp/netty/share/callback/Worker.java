package cn.etcp.netty.share.callback;

/**
 * @Title: Worker
 * @Package: cn.netty.action.callback
 * @Description:
 * @author: zhiyong.fan
 * @date: 2018/6/6 13:54
 * @version: V1.0
 */
public abstract class Worker {

    protected abstract void doTask(Callback callback);
}
