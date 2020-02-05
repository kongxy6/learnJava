package spring.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;

import java.beans.PropertyDescriptor;

public class MyInstantiationAwareBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter {

    public MyInstantiationAwareBeanPostProcessor() {
        super();
        System.out.println("InstantiationAwareBeanPostProcessorAdapter实现类构造器！！");
    }

    // 接口方法、实例化Bean之前调用
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        System.out.println("InstantiationAwareBeanPostProcessorAdapter 调用postProcessBeforeInstantiation方法");
        return null;
    }

    // 接口方法、实例化Bean之后调用
    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        System.out.println("InstantiationAwareBeanPostProcessorAdapter 调用postProcessAfterInstantiation方法");
        // 要是返回false会导致无法触发注入属性的方法
        return true;
    }

    // 接口方法、设置某个属性时调用
    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds, Object bean,
                                                    String beanName) throws BeansException {
        if (beanName.equals("person")) {
            if (pvs instanceof MutablePropertyValues) {
                MutablePropertyValues mpvs = (MutablePropertyValues) pvs;
                mpvs.add("phone", 156264);
            }
        }
        if (beanName.equals("student")) {
            if (pvs instanceof MutablePropertyValues) {
                MutablePropertyValues mpvs = (MutablePropertyValues) pvs;
                mpvs.add("age", 99);
            }
        }
        System.out.println("InstantiationAwareBeanPostProcessor调用postProcessPropertyValues方法 -> 可以对属性进行修改");
        return pvs;
    }
}