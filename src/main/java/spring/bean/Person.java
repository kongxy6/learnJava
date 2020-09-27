package spring.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;

public class Person implements BeanFactoryAware, BeanNameAware, InitializingBean, DisposableBean {

    public int c_arg;
    private String name;
    private String address;
    private int phone;
    private Man man;
    private BeanFactory beanFactory;
    private String beanName;

    public Person(int c_arg) {
        this.c_arg = c_arg;
        System.out.println("【构造器】调用Person的构造器实例化【此时是没有注入属性的】，而对于合并的bean定义仍然可以再注入属性之前进行post-process");
    }

    public Man getMan() {
        return man;
    }

    public void setMan(Man man) {
        System.out.println("【注入属性】注入属性man");
        this.man = man;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    // 这是BeanFactoryAware接口方法
    @Override
    public void setBeanFactory(BeanFactory arg) throws BeansException {
        System.out.println("【BeanFactoryAware接口】调用BeanFactoryAware.setBeanFactory()");
        this.beanFactory = arg;
    }

    public String getBeanName() {
        return beanName;
    }

    // 这是BeanNameAware接口方法
    @Override
    public void setBeanName(String arg) {
        System.out.println("【BeanNameAware接口】调用BeanNameAware.setBeanName()");
        this.beanName = arg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        System.out.println("【注入属性】注入属性name");
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        System.out.println("【注入属性】注入属性address");
        this.address = address;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        System.out.println("【注入属性】注入属性phone");
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Person [address=" + address + ", name=" + name + ", phone=" + phone + "]";
    }

    // 这是InitializingBean接口方法
    @Override
    public void afterPropertiesSet() throws Exception {
        this.phone = 0;
        System.out.println("【InitializingBean接口】调用afterPropertiesSet()");
    }

    // 这是DiposibleBean接口方法
    @Override
    public void destroy() throws Exception {
        System.out.println("【DiposibleBean接口】调用destory()");
    }

    // 通过<bean>的init-method属性指定的初始化方法
    public void myInit() {
        System.out.println("【init-method】调用<bean>的init-method属性指定的初始化方法");
    }

    // 通过<bean>的destroy-method属性指定的初始化方法
    public void myDestroy() {
        System.out.println("【destroy-method】调用<bean>的destroy-method属性指定的初始化方法");
    }
}
