package cn.etcp.netty.share.callback;

/**
 * @Title: Task
 * @Package: cn.netty.action.callback
 * @Description:
 * @author: zhiyong.fan
 * @date: 2018/6/6 13:51
 * @version: V1.0
 */
public class Task {

    private String name;

    private int    m;

    private int    n;

    public Task(String name, int m, int n) {
        this.name = name;
        this.m = m;
        this.n = n;
    }

    @Override
    public String toString() {
        int r = m / n;
        return String.format("%s, calculate m / n = %d", name, r);
    }
}
