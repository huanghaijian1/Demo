package com.example.demo.spring.beanLifeCycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class SpringBean implements BeanNameAware, BeanFactoryAware, ApplicationContextAware,InitializingBean, DisposableBean {

    public void setApplicationContextNull(){
        applicationContext = null;
    }

    public void test(String msg){
        System.out.println(msg + "_test");
    }

    public SpringBean(){
        System.out.println("------springBean_构造方法------");
    }

    @Override
    public void setBeanName(String s) {
        System.out.println("------setBeanName_aware_"+s+"------");
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("------setBeanFactory_aware------");
    }

    private ApplicationContext applicationContext ;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext ;
        System.out.println("------springBean_ApplicationContextAware start------");
    }

    @PostConstruct
    public void myStart(){
        System.out.println("------springBean_@PostConstruct------");
    }

    @Override
    public void afterPropertiesSet(){
        System.out.println("------springBean_afterPropertiesSet------");
    }

    public void myStart2(){
        System.out.println("------springBean_@Bean_initMethod------");
    }

    @PreDestroy
    public void myDestroy(){
        System.out.println("------springBean_@PreDestroy------");
    }

    @Override
    public void destroy(){
        System.out.println("------springBean_destroy------");
    }

    public void myDestroy2(){
        System.out.println("------springBean_@Bean_destroyMethod------");
    }


//    /**
//     * 预初始化 初始化之前调用
//     * @param bean
//     * @param beanName
//     * @return
//     * @throws BeansException
//     */
//    @Override
//    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
//        if ("annotationBean".equals(beanName)){
//            System.out.println("SpringLifeCycleProcessor start beanName={}"+beanName);
//        }
//        return bean;
//    }
//
//    /**
//     * 后初始化  bean 初始化完成调用
//     * @param bean
//     * @param beanName
//     * @return
//     * @throws BeansException
//     */
//    @Override
//    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//        if ("annotationBean".equals(beanName)){
//            System.out.println("SpringLifeCycleProcessor end beanName={}"+beanName);
//        }
//        return bean;
//    }

}
