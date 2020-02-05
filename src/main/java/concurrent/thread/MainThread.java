package concurrent.thread;

import model.SqlObject;

import java.util.concurrent.*;

public class MainThread {
    public static void main(String[] args) {
        BlockingQueue<SqlObject> sqlObjectBlockingQueue = new LinkedBlockingQueue<>();
        Object logLock = new Object();
        Producer producer = new Producer(sqlObjectBlockingQueue, logLock);
        Consumer consumer = new Consumer(sqlObjectBlockingQueue, logLock);

        System.out.println(Runtime.getRuntime().availableProcessors());

        TimeUnit timeUnit = TimeUnit.SECONDS;
        ExecutorService service = new ThreadPoolExecutor(3, 6, 1, timeUnit, new LinkedBlockingQueue<>());
        //service= new

        // 这种方式并不能响应中断
        service.execute(producer);
        try {
            service.submit(consumer);
        } catch (RejectedExecutionException ignored) {

        }

//        producer.start();
//        consumer.start();

//        Thread concurrent.thread = new Thread(() -> {
//            for (int i = 0; i < 1000; ++i) {
//                System.out.println(i);
//            }
//        });
//
//        concurrent.thread.start();
//
//        Thread thread1 = new Thread(() -> {
//            for (int i = 1000; i > 0; --i) {
//                System.out.println(i);
//            }
//        });
//
//        thread1.start();

    }
}
