package concurrent.thread;

import model.SqlObject;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Producer extends Thread {

    private static final AtomicInteger num = new AtomicInteger();
    private final BlockingQueue<SqlObject> sqlObjectBlockingQueue;

    Producer(BlockingQueue<SqlObject> sqlObjectBlockingQueue) {
        this.sqlObjectBlockingQueue = sqlObjectBlockingQueue;
    }

    @Override
    public void run() {
        try {
            // 显示判断提示响应速度
            while (!Thread.currentThread().isInterrupted()) {
                SqlObject sqlObject = new SqlObject();
                sqlObject.setId(num.incrementAndGet());
                if (num.get() == 101) {
                    cancel();
                }
                // 该方法会响应中断
                Thread.sleep(5);
                sqlObjectBlockingQueue.put(sqlObject);
                System.out.println("线程---" + Thread.currentThread().toString() + "执行了一次生产任务，对象id为---" + sqlObject.getId());

            }
        } catch (InterruptedException e) {
            System.out.println("===== 生产者线程结束 =====");
            // 恢复中断状态
            Thread.currentThread().interrupt();
        }
    }

    public void cancel() {
        // 如果作为任务提交的，使用this.interrupt();是无作用的。
        Thread.currentThread().interrupt();
    }
}
