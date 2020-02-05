package concurrent.volatile_test;

// TODO 基本类型volatile和引用类型volatile的区别
public class RefVolatileTest {

    static Test test = new Test();

    static volatile int a = 0;

    public static void main(String[] args) throws InterruptedException {
        new Thread(new RefVolatileTest.MyRunner()).start();
        new Thread(new RefVolatileTest.MyRunner1()).start();

        while (true) {
            System.out.println(a);
        }
    }

    /**
     * volatile 会保证同时只有一个线程去修改CPU缓存的副本，
     * 修改完会立即写入主存，并使其他该变量的CPU缓存失效。
     * <p>
     * 后启动的线程获取到a，使用寄存器对其执行加法，可是实际上此时a = a + n，后启动线程将a+1后写入主存，
     * 另一线程恰好执行读取，那么a就损失了已经加的n。
     */
    static class MyRunner implements Runnable {
        Test volatileTest;

        @Override
        public void run() {
            for (int i = 0; i < 500000; i++) {
                ++a;
            }
        }
    }

    static class MyRunner1 implements Runnable {
        Test volatileTest;

        @Override
        public void run() {
            for (int i = 0; i < 500000; i++) {
                ++a;
            }
        }
    }

}
