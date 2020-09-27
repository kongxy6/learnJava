package model;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.lang.reflect.Field;

@Slf4j
public class Test {

    @org.junit.jupiter.api.Test
    public void test() {
        NoMethodObject object = new NoMethodObject();
        MetaObject metaObject = SystemMetaObject.forObject(object);
        if (metaObject.hasGetter("id")) {
            metaObject.setValue("sqlObject.id", 5);
        }
        System.out.println(object);
        try {
            Field field = object.getClass().getDeclaredField("id");
            field.setAccessible(true);
            field.set(object, 9);
            System.out.println(field.get(object));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @org.junit.jupiter.api.Test
    void test1() {
        ImmutableObject i = new ImmutableObject(1);
        i.builder = new StringBuilder();
        i.builder.append("xyz");
        log.info("builder: " + i.builder);
    }

    @org.junit.jupiter.api.Test
    void test2() {
        String s1 = new StringBuilder("a").append("aa").toString();
        String s2 = "aaa";
        System.out.println(s1 == s2);           // false
        String s3 = s1.intern();
        System.out.println(s3 == s1);
        String s4 = s2.intern();
        System.out.println(s3 == s4);
        System.out.println(s1 == s2);

        String s7 = "b";

        // 只针对字面量做优化，此处使用变量，并不会优化
        String s5 = s7 + "(๑′ᴗ‵๑)Ｉ Lᵒᵛᵉᵧₒᵤ❤";
        String s6 = "b(๑′ᴗ‵๑)Ｉ Lᵒᵛᵉᵧₒᵤ❤";

        System.out.println(s5 == s6);
    }
}
