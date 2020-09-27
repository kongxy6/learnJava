package model;

import org.junit.jupiter.api.Test;

public class NoMethodObject {
    // 如果是基本类型，可能会存在编译时固定
    private final int id = 7;

    private String desc;

    private final SqlObject sqlObject = null;

    @Override
    public String toString() {
        return "NoMethodObject{" +
                "id=" + id +
                ", desc='" + desc + '\'' +
                '}';
    }

    @Test
    public void test() {

    }
}
