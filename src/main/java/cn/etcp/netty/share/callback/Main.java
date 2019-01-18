package cn.etcp.netty.share.callback;

/**
 * @Title: Main
 * @Package: cn.netty.action.callback
 * @Description:
 * @author: zhiyong.fan
 * @date: 2018/6/6 13:51
 * @version: V1.0
 */
public class Main {

    public static void main(String[] args) {
        Worker worker = new Developer(new Task("V1.0 Requirement", 1, 2));
        worker.doTask(new Callback() {

            @Override
            public void onFinish(Task task) {
                System.err.println(String.format("Task:[%s] finished.", task));
                know();
            }

            @Override
            public void onDelay(Throwable cause) {
                System.err.println("Task delayed:" + cause.getMessage());
                know();
            }

            public void know() {
                System.out.println("I know....");
            }
        });
    }
}
