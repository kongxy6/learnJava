package spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring.bean.Person;

import java.util.Calendar;
import java.util.Date;

public class App {
    private static ApplicationContext ctx;

    public static void main(String[] args) {

        System.out.println("现在开始初始化容器 " + Calendar.getInstance().get(Calendar.YEAR) + " " + new Date());

        ctx = new ClassPathXmlApplicationContext("spring/beans.xml");
        System.out.println("容器初始化成功");

        System.out.println(((Person) ctx.getBean("person")).getMan().toString());

        System.out.println("现在开始关闭容器！");
        ((ClassPathXmlApplicationContext) ctx).registerShutdownHook();

    }
}
