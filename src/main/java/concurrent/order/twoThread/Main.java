package concurrent.order.twoThread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    volatile boolean mark = true;

    int count = 0;

    @Test
    public void main() {
        AtomicInteger num = new AtomicInteger();
        Atom atom = new Atom(num, true);
        Thread thread = new Odd(atom);
        Thread thread1 = new Even(atom);
        Thread thread2 = new Even(atom);
        thread.start();
        thread1.start();
        thread2.start();
    }

    @Test
    public void test() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            while (true) {
                if (mark) {
                    System.out.println("t1 === " + count++);
                    if (count == 1000000) {
                        break;
                    }
                    mark = false;
                }
            }
        });
        Thread t2 = new Thread(() -> {
            while (true) {
                if (!mark) {
                    System.out.println("t2 === " + count++);
                    if (count == 1000000) {
                        break;
                    }
                    mark = true;
                }
            }
        });
        t1.start();
        t2.start();

        Thread.sleep(10000);
    }

    class Atom {
        AtomicInteger atomicInteger;

        volatile boolean flag;

        Atom(AtomicInteger atomicInteger, boolean flag) {
            this.atomicInteger = atomicInteger;
            this.flag = flag;
        }
    }

}
