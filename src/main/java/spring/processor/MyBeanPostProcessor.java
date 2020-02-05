package spring.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import spring.bean.Person;

import java.util.logging.Logger;

public class MyBeanPostProcessor implements BeanPostProcessor {

    Logger logger = Logger.getLogger(this.getClass().getName());

    public MyBeanPostProcessor() {
        super();
        System.out.println("BeanPostProcessor实现类构造器！！");
    }

    public Object postProcessAfterInitialization(Object arg, String beanName) throws BeansException {
        if (beanName.equals("person")) {
            if (arg instanceof Person) {
                Person person = (Person) arg;
                logger.info("person->phone " + person.getPhone());
                person.setPhone(1562646);
            }
        }
        System.out.println("BeanPostProcessor调用postProcessAfterInitialization对对象的属性进行更改！");
        return arg;
    }

    public Object postProcessBeforeInitialization(Object arg, String beanName) throws BeansException {
        System.out.println("BeanPostProcessor调用postProcessBeforeInitialization对对象的属性进行更改！");
        return arg;
    }
}