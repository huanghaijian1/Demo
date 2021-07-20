package com.example.demo.spring.circularReference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CircularReference2{

//    private final CircularReference circularReference;
//
//    public CircularReference2(CircularReference circularReference){
//        this.circularReference = circularReference;
//    }

    private CircularReference circularReference;

    @Autowired
    public void setCircularReference(CircularReference circularReference) {
        this.circularReference = circularReference;
    }

    public void test(){
        circularReference.test2();
    }

    public void test2(){
        System.out.println("CircularReference2 test2");
    }


}
