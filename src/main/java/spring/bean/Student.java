package spring.bean;

public class Student extends Person {

    private int age;

    public Student(int i) {
        super(i);
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student [age=" + age + "]";
    }

}
