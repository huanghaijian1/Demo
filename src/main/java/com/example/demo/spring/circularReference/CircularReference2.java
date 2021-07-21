package com.example.demo.spring.circularReference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CircularReference2{

//    private final CircularReference circularReference;
//
//    public CircularReference2(CircularReference circularReference){
//        this.circularReference = circularReference;
//    }
//    @Autowired
//    private CircularReference circularReference;


    private CircularReference circularReference;

//    @Lazy
    @Autowired
    public void setCircularReference(CircularReference circularReference) {
        this.circularReference = circularReference;
    }

    public void test(){
        circularReference.test2();
    }

//    @Async    会导致spring无法解决循环依赖
//    @Transactional
    public void test2(){
        System.out.println("CircularReference2 test2");
    }


}
