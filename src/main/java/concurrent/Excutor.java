package concurrent;

import model.SqlObject;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 */
public class Excutor {

    /**
     * 表示线程池线程数的bit数，29
     * 00000000 00000000 00000000 00011101
     */
    private static final int COUNT_BITS = Integer.SIZE - 3;
    /**
     * 最大的线程数量，数量是完全够用了
     * 00011111 11111111 11111111 11111111
     */
    private static final int CAPACITY = (1 << COUNT_BITS) - 1;
    /**
     * 11100000 00000000 00000000 00000000
     */
    private static final int RUNNING = -1 << COUNT_BITS;
    /**
     * 全0
     */
    private static final int SHUTDOWN = 0 << COUNT_BITS;
    /**
     * 00100000 - - -
     */
    private static final int STOP = 1 << COUNT_BITS;
    /**
     * 01000000 - - -
     */
    private static final int TIDYING = 2 << COUNT_BITS;
    /**
     * 01100000 - - -
     */
    private static final int TERMINATED = 3 << COUNT_BITS;
    private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));
    SqlObject sqlObject = new SqlObject();

    private final Callable<String> task = () -> {
        Thread.sleep(1000);
        return "success";
    };
    private final Runnable runnable = () -> {
        System.out.println("hell0~");
        sqlObject.setField("ddd");
    };

    //组装状态和数量，成为ctl
    private static int ctlOf(int rs, int wc) {
        return rs | wc;
    }

    @Test
    public void test() throws ExecutionException, InterruptedException {
        ThreadPoolExecutor poolExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        BlockingQueue<Runnable> workQueue;
        poolExecutor.execute(runnable);

        FutureTask future1 = new FutureTask<>(task);
        poolExecutor.execute(future1);
        System.out.println(future1.get());

        FutureTask<SqlObject> future2 = new FutureTask<>(runnable, sqlObject);
        poolExecutor.execute(future2);
        System.out.println(future2.get().getField());

        poolExecutor.submit(runnable);

    }
}
