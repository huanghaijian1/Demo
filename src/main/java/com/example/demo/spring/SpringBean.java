package com.example.demo.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class SpringBean implements InitializingBean, DisposableBean, BeanPostProcessor {

    public void test(){
        System.out.println("test");
    }

    public SpringBean(){
        System.out.println("------------");
    }


    @Override
    public void afterPropertiesSet(){
        System.out.println("afterPropertiesSet");
    }

    @PostConstruct
    public void myStart(){
        System.out.println("myStart");
    }


    public void myStart2(){
        System.out.println("myStart2");
    }

    public void myDestroy2(){
        System.out.println("myDestroy2");
    }


    @Override
    public void destroy(){
        System.out.println("destroy");
    }

    @PreDestroy
    public void myDestroy(){
        System.out.println("myDestroy");
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
        if ("springBean".equals(beanName)){
            System.out.println("SpringLifeCycleProcessor start beanName={}"+beanName);
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
        if ("springBean".equals(beanName)){
            System.out.println("SpringLifeCycleProcessor end beanName={}"+beanName);
        }
        return bean;
    }

}
