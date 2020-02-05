package classLoader.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Test {

    @org.junit.jupiter.api.Test
    void test() throws InvocationTargetException, IllegalAccessException {
        Class clazz = Child.class;
        Method[] methods = clazz.getDeclaredMethods();
        System.out.println("===getDeclaredMethods=== " + methods.length);
        for (int i = 0; i < methods.length; ++i) {
            if ("getDesc".equals(methods[i].getName())) {
                methods[i].invoke(new Child(), "刘禅", 17);
            }
        }

        methods = clazz.getMethods();
        System.out.println("===getMethods=== " + methods.length);
        for (int i = 0; i < methods.length; ++i) {
            if ("getDesc".equals(methods[i].getName())) {
                methods[i].invoke(new Child(), "刘禅", 17);
            }
            if ("getAge".equals(methods[i].getName())) {
                methods[i].invoke(new Child(), 16, 17);
            }
        }
    }
}
