package annotation;

import annotation.model.Member;
import annotation.model.TableCreator;

class Test {
    @org.junit.jupiter.api.Test
    void test() throws ClassNotFoundException {
        System.out.println(TableCreator.createTableSQL(Member.class.getName()));
    }
}
