package concurrent.order.threeThread;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Main {

    private static final Logger log = Logger.getLogger(Main.class);

    @Test
    void main() {
        final Three[] flag = {Three.ONE};
        Lock lock = new ReentrantLock();
        final Condition oneCondition = lock.newCondition();
        final Condition twoCondition = lock.newCondition();
        final Condition threeCondition = lock.newCondition();
        AtomicInteger integer = new AtomicInteger();
        integer.getAndIncrement();

        Thread thread1 = new Thread(() -> {
            for (; integer.get() < 100; ) {
                lock.lock();
                // 当得到唤醒，并再次获得锁，可能条件谓词已经发生了变化，所以需要循环测试
                while (flag[0] != Three.ONE) {
                    try {
                        oneCondition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.info(integer.getAndIncrement());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                flag[0] = Three.TWO;
                twoCondition.signal();
                lock.unlock();
            }
        });

        Thread thread2 = new Thread(() -> {
            for (; integer.get() < 100; ) {
                lock.lock();
                while (flag[0] != Three.TWO) {
                    try {
                        twoCondition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.info(integer.getAndIncrement());
                flag[0] = Three.THREE;
                threeCondition.signal();
                lock.unlock();
            }
        });

        Thread thread3 = new Thread(() -> {
            for (; integer.get() < 100; ) {
                lock.lock();
                while (flag[0] != Three.THREE) {
                    try {
                        threeCondition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.info(integer.getAndIncrement());
                flag[0] = Three.ONE;
                oneCondition.signal();
                lock.unlock();
            }
        });

        thread2.start();
        thread3.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread1.start();

        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
