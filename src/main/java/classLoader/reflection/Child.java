package classLoader.reflection;

public class Child extends Father {

    @Override
    public void getDesc(String name, int age) {
        System.out.println("child：" + name + " " + age);
    }
}
