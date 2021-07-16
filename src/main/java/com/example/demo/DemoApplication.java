package com.example.demo;

import com.example.demo.spring.SpringBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean(name = "springBean",initMethod = "myStart2",destroyMethod = "myDestroy2")
    public SpringBean springBean(){
        return new SpringBean();
    }

}
