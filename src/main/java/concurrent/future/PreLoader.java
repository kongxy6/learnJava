package concurrent.future;

import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

public class PreLoader {

    // 定义一个任务
    private Callable<String> task = () -> {
        Thread.sleep(1000);
        return "success";
    };

    @Test
    void test() {
        FutureTask<String> future = new FutureTask<>(task);
        Thread thread = new Thread(future);
        thread.start();
        while (true) {
            try {
                String result = future.get(0, TimeUnit.SECONDS);
                if (result != null) {
                    System.out.println(result);
                    break;
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                System.out.println("未获取到结果");
            }
        }
    }


}
