package concurrent.thread;

import model.SqlObject;

import java.util.concurrent.*;

public class MainThread {
    public static void main(String[] args) {
        BlockingQueue<SqlObject> sqlObjectBlockingQueue = new LinkedBlockingQueue<>();
        Producer producer = new Producer(sqlObjectBlockingQueue);
        Consumer consumer = new Consumer(sqlObjectBlockingQueue);

        System.out.println(Runtime.getRuntime().availableProcessors());

        TimeUnit timeUnit = TimeUnit.SECONDS;
        ExecutorService service = new ThreadPoolExecutor(3, 6, 1, timeUnit, new LinkedBlockingQueue<>());

        service.execute(producer);
        try {
            service.submit(consumer);
        } catch (RejectedExecutionException ignored) {

        }

//        producer.start();
//        consumer.start();
    }
}
