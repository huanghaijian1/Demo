package com.example.demo.spring.beanLifeCycle.common;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory;
import org.springframework.stereotype.Component;

@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    public MyBeanFactoryPostProcessor(){
        System.out.println("------MyBeanFactoryPostProcessor构造方法------");
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        //解决@Async导致的spring解决循环依赖失效问题
        ((AbstractAutowireCapableBeanFactory) configurableListableBeanFactory).setAllowRawInjectionDespiteWrapping(true);
        System.out.println("-------postProcessBeanFactory------");
    }

}
