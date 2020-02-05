package spring.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class ModifyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    public ModifyBeanFactoryPostProcessor() {
        super();
        System.out.println("BeanFactoryPostProcessor实现类构造器！！");
    }

    // 该接口只具备修改能力，而不具备注册能力
    public void postProcessBeanFactory(ConfigurableListableBeanFactory arg) throws BeansException {
        System.out.println("BeanFactoryPostProcessor调用postProcessBeanFactory方法 -> 直接修改bean定义");
        BeanDefinition bd = arg.getBeanDefinition("person");
        bd.getPropertyValues().addPropertyValue("phone", "156");
    }
}