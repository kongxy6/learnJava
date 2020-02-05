package classInit;

public class TestStatic {

    // 此时将会创建对象
    static TestStatic st = new TestStatic();

    static int b = 112;

    static {
        System.out.println("1");
    }

    int a = 100;

    {
        System.out.println("2");
    }

    TestStatic() {
        System.out.println("3");
        System.out.println("a=" + a + " b=" + b);
    }

    public static void main(String args[]) {
        staticFunction();
    }

    public static void staticFunction() {
        System.out.println(b);
    }
}
