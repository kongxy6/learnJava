package classInit;

public interface Family {

    // 当类
    static String a = getA();

    static String getA() {
        System.out.println("接口初始化被触发");
        return "a";
    }

}
