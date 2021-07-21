package com.example.demo;

import com.example.demo.spring.beanLifeCycle.SpringBean;
import com.example.demo.spring.circularReference.CircularReference;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
//@ComponentScan(lazyInit = true)
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean(name = "springBean",initMethod = "myStart2",destroyMethod = "myDestroy2")
    public SpringBean springBean(){
        return new SpringBean();
    }

//    @Bean(name = "springBean2")
//    public SpringBean2 springBean2(){
//        return new SpringBean2();
//    }

}
