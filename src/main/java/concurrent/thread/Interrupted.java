package concurrent.thread;

public class Interrupted {

    static Thread thread = new Thread(() -> {
        System.out.println("run " + Thread.interrupted());
        for (int i = 0; i < 5; i++) {
            System.out.println(i);
        }
        System.out.println("run " + Thread.interrupted());
    });

    public static void main(String... args) {
        thread.start();
        thread.interrupt();
        System.out.println(Thread.interrupted());
    }

}
