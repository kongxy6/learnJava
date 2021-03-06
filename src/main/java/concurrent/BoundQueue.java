package concurrent;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class BoundQueue {

    private final Queue<String> queue = new LinkedList<>();
    Semaphore semaphore = new Semaphore(5);

    public String remove() {
        String s;
        try {
            // 需要同步
            s = queue.remove();
        } catch (Exception e) {
            return null;
        }
        semaphore.release();
        return s;
    }

    public boolean add(String s) {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 需要同步
        return queue.add(s);
    }

}
