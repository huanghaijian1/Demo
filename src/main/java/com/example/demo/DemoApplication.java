package com.example.demo;

import com.example.demo.spring.beanLifeCycle.SpringBean;
import com.example.demo.spring.circularReference.CircularReference;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
//@ComponentScan(lazyInit = true)   解决@Async导致的spring解决循环依赖失效问题
//@EnableAspectJAutoProxy(proxyTargetClass = true) 强制使用cglib jdk1.8后jdk动态代理效率优于cglib动态代理
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
