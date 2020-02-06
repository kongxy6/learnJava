package concurrent.order.twoThread;

import org.apache.log4j.Logger;

public class Odd extends Thread {

    private static Logger log = Logger.getLogger(Odd.class);

    private final Main.Atom atom;

    public Odd(Main.Atom atom) {
        this.atom = atom;
    }

    @Override
    public void run() {
        for (; atom.atomicInteger.get() < 100; ) {
            synchronized (atom) {
                while (!atom.flag) {
                    try {
                        atom.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.info(atom.atomicInteger.get());
                atom.atomicInteger.incrementAndGet();
                atom.flag = false;
                atom.notifyAll();
            }
        }
    }
}
