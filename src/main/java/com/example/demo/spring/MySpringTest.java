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

//    private final SpringBean springBean;
//
//    public MySpringTest(SpringBean springBean){
//        this.springBean = springBean;
//    }
//
//    @GetMapping("/springTest")
//    public String mytest(){
//        springBean.test();
//        return "123";
//    }


    /**
     * Spring 只帮我们管理单例模式 Bean 的完整生命周期，对于 prototype 的 bean ，Spring 在创建好交给使用者之后则不会再管理后续的生命周期。
     * springBean生命周期执行顺序：
     * -->构造方法
     * -->setBeanName_aware
     * -->setBeanFactory_aware
     * -->ApplicationContext start: Aware 接口可以用于在初始化 bean 时获得 Spring 中的一些对象，如获取 Spring 上下文等。
     * -->BeanPostProcessor start beanName={springBean}: 预初始化 bean 初始化之前调用
     * -->@PostConstruct
     * -->afterPropertiesSet
     * -->@Bean_initMethod
     * -->BeanPostProcessor end beanName={springBean}: 预初始化 bean 初始化完成调用
     * -->bean准备就绪
     * -->@PreDestroy
     * -->destroy
     * -->@Bean_destroyMethod
     * -->
     * -->-->-->-->-->-->-->-->-->
     * @param args
     */

    public static void main(String[] args){
        ConfigurableApplicationContext ctx = new AnnotationConfigApplicationContext(SpringBean.class,MyBeanFactoryPostProcessor.class,MyBeanPostProcessor.class,MyInstantiationAwareBeanPostProcessorAdapter.class);
        System.out.println("容器初始化成功");

        SpringBean springBean = ctx.getBean("springBean",SpringBean.class);
        System.out.println(springBean);


        System.out.println("现在开始关闭容器！");
        ((AnnotationConfigApplicationContext) ctx).removeBeanDefinition("springBean");

    }


}
