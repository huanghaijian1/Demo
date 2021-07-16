package com.example.demo.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MySpringTest {

    private final SpringBean springBean;

    public MySpringTest(SpringBean springBean){
        this.springBean = springBean;
    }
//
//    @GetMapping("/springTest")
//    public String mytest(){
//        springBean.test();
//        return "123";
//    }

    public static void main(String[] args){
        ConfigurableApplicationContext ctx = new AnnotationConfigApplicationContext(SpringBean.class);
        ctx.registerShutdownHook();

        log.info("line ----------------------------- line");
        ctx.start();
        ctx.stop();
        log.info("line ----------------------------- line");

    }


}
