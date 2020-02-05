package concurrent.volatile_test;

public class VolatileTest {

    /**
     * 该测试要去掉a属性的volatile
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        Test test = new Test();
        new Thread(new MyRunner(test)).start();
        new Thread(new MyRunner1(test)).start();

        Thread.sleep(1000);

        /**
         * 堆即主存
         * 当开始执行时，将堆数据读取到CPU缓存，然后CPU将不再读取堆中数据
         * 当修改缓存时，数据会被刷新到堆，这个刷新动作不受控制
         *
         * 注意：以下示例使用输出流做了同步
         *
         * 线程长时间未被调度，cpu缓存可能被清除，当被重新调度时，会从主存加载数据
         */
        while (true) {
            System.out.println(" 必然是5 " + test.a);
            System.out.println(" 值不确定 " + test.a);
            test.a = 5;
        }

    }

    static class MyRunner implements Runnable {
        Test volatileTest;

        public MyRunner(Test volatileTest) {
            this.volatileTest = volatileTest;
        }

        @Override
        public void run() {
            while (true) {
                System.out.println(Thread.currentThread() + " " + volatileTest.a);
                volatileTest.a = 2;
            }
        }
    }

    static class MyRunner1 implements Runnable {
        Test volatileTest;

        public MyRunner1(Test volatileTest) {
            this.volatileTest = volatileTest;
        }

        @Override
        public void run() {
            while (true) {
                System.out.println(Thread.currentThread() + " " + volatileTest.a);
                volatileTest.a = 3;
            }
        }
    }

}
