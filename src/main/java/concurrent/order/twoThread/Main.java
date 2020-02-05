package concurrent.order.twoThread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

public class Main {

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

    class Atom {
        AtomicInteger atomicInteger;

        volatile boolean flag;

        Atom(AtomicInteger atomicInteger, boolean flag) {
            this.atomicInteger = atomicInteger;
            this.flag = flag;
        }
    }

}
