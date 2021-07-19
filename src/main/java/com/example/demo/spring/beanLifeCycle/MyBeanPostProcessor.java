package com.example.demo.spring.beanLifeCycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class MyBeanPostProcessor implements BeanPostProcessor {


    public MyBeanPostProcessor(){
        System.out.println("------MyBeanPostProcessor_构造方法------");
    }

    /**
     * 预初始化 初始化之前调用
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if ("springBean".equals(beanName)||"springBean2".equals(beanName)){
            System.out.println("------"+beanName+"_BeanPostProcessor start------");
            if("springBean".equals(beanName)){
                ((SpringBean)bean).test("BeanPostProcessor_start中调用");
            }
        }
        return bean;
    }

    /**
     * 后初始化  bean 初始化完成调用
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if ("springBean".equals(beanName)||"springBean2".equals(beanName)){
            System.out.println("------"+beanName+"_BeanPostProcessor end------");
            if("springBean".equals(beanName)){
                ((SpringBean)bean).test("BeanPostProcessor_end中调用");
            }
        }
        return bean;
    }
}
