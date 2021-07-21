package com.example.demo.spring.circularReference;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
public class CircularReference{

//    private final CircularReference2 circularReference2;
//
//    public CircularReference(CircularReference2 circularReference2){
//        this.circularReference2 = circularReference2;
//    }

//    @Autowired
//    private CircularReference2 circularReference2;


    private CircularReference2 circularReference2;

//    @Lazy
    @Autowired
    public void setCircularReference2(CircularReference2 circularReference2) {
        this.circularReference2 = circularReference2;
    }

    public void test(){
       circularReference2.test2();
    }



    @Async
//    @Transactional
    public void test2(){
        System.out.println("CircularReference test2");
    }

}
