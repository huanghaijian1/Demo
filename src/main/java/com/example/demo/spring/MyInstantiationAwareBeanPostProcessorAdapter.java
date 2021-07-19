package com.example.demo.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.stereotype.Component;

import java.beans.PropertyDescriptor;

@Component
public class MyInstantiationAwareBeanPostProcessorAdapter extends InstantiationAwareBeanPostProcessorAdapter {

    public MyInstantiationAwareBeanPostProcessorAdapter() {
        super();
        System.out.println("------MyInstantiationAwareBeanPostProcessorAdapter构造方法------");
    }

    // 接口方法、实例化Bean之前调用
    @Override
    public Object postProcessBeforeInstantiation(Class beanClass,String beanName) throws BeansException {
        if ("springBean".equals(beanName)||"springBean2".equals(beanName)){
            System.out.println("------"+beanName+"_调用postProcessBeforeInstantiation方法------");
        }
        return null;
    }


    // 接口方法、设置某个属性时调用 已过时
    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName)throws BeansException {
        System.out.println("------postProcessPropertyValues方法------");
        return pvs;
    }


    // 接口方法、实例化Bean之后调用
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)throws BeansException {
        if ("springBean".equals(beanName)||"springBean2".equals(beanName)){
            System.out.println("------"+beanName+"_调用postProcessAfterInitialization方法------");
            if("springBean".equals(beanName)){
                ((SpringBean)bean).test("postProcessAfterInitialization中调用");
            }
        }
        return bean;
    }



}
