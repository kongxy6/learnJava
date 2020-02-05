package concurrent.order.twoThread;

import org.apache.log4j.Logger;

public class Even extends Thread {

    private static Logger log = Logger.getLogger(Even.class);

    private final Main.Atom atom;

    public Even(Main.Atom atom) {
        this.atom = atom;
    }

    @Override
    public void run() {
        for (; atom.atomicInteger.get() < 100; ) {
//            if (!atom.flag) {
//                log.info(atom.atomicInteger.incrementAndGet());
//                atom.flag = true;
//            }
            synchronized (atom) {
                while (atom.flag) {
                    try {
                        atom.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.info(atom.atomicInteger.get());
                atom.atomicInteger.incrementAndGet();
                atom.flag = true;
                atom.notifyAll();
            }
        }
    }
}
