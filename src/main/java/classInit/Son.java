package classInit;

class Son extends Father implements Family {
    static {
        System.out.println("3.  子类静态代码块初始化");
    }

    private int i;
    private String s = "子类私有成员变量";

    {
        System.out.println("7.  子类代码块初始化");
    }

    public Son() {
        i = 1;
        System.out.println("8.  子类构造函数初始化完成");
        System.out.println("9.  子类成员变量初始化完成：s=" + s);
        show();
    }

    @Override
    public void show() {
        System.out.println("10. 子类show()方法：i=" + i);
    }
}
