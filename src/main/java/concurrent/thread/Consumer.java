package concurrent.thread;

import model.SqlObject;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class Consumer extends Thread {

    private BlockingQueue<SqlObject> sqlObjectBlockingQueue;

    Consumer(BlockingQueue<SqlObject> sqlObjectBlockingQueue) {
        this.sqlObjectBlockingQueue = sqlObjectBlockingQueue;
    }

    @Override
    public void run() {
        // 设置响应中断的检查方式
        while (!Thread.currentThread().isInterrupted()) {
            List<SqlObject> sqlObjectList = new LinkedList<>();
            //take()
            sqlObjectBlockingQueue.drainTo(sqlObjectList, 5);

            // 模拟消费过程
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            for (SqlObject sqlObject : sqlObjectList) {
                System.out.println("线程---" + Thread.currentThread().toString() + "执行了一次消费任务，对象id为---" + sqlObject.getId());
                if (sqlObject.getId() == 100) {
                    cancel();
                }
            }

        }
        System.out.println("===== 消费者线程结束 =====");
    }

    private void cancel() {
        // 如果作为任务提交的，使用this.interrupt();是无作用的。
        Thread.currentThread().interrupt();
    }
}

