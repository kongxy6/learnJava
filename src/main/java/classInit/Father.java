package classInit;

class Father {
    private static final String s = print();

    static {
        System.out.println("2.  父类静态代码块初始化");
    }

    {
        System.out.println("4.  父类代码块初始化");
    }

    public Father() {
        System.out.println("5.  父类无参构造函数初始化完成");
        show();
    }

    public static String print() {
        System.out.println("1.  父类静态方法");
        return "父类静态成员变量的初始化";
    }

    public void show() {
        System.out.println("6.  " + s);
    }
}
