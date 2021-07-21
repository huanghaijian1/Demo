package com.example.demo.spring;

import com.example.demo.spring.beanLifeCycle.SpringBean;
import com.example.demo.spring.circularReference.CircularReference;
import com.example.demo.spring.circularReference.CircularReference2;
import com.example.demo.spring.beanLifeCycle.common.MyBeanFactoryPostProcessor;
import com.example.demo.spring.beanLifeCycle.common.MyBeanPostProcessor;
import com.example.demo.spring.beanLifeCycle.common.MyInstantiationAwareBeanPostProcessorAdapter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
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
     * springBean生命周期
     *
     * Spring 只帮我们管理单例模式 Bean 的完整生命周期，对于 prototype 的 bean ，Spring 在创建好交给使用者之后则不会再管理后续的生命周期。
     * 执行顺序：(部分)
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
     */

    @Test
    public void test1(){
        ConfigurableApplicationContext ctx = new AnnotationConfigApplicationContext(SpringBean.class, MyBeanFactoryPostProcessor.class, MyBeanPostProcessor.class, MyInstantiationAwareBeanPostProcessorAdapter.class);
        System.out.println("容器初始化成功");

        SpringBean springBean = ctx.getBean("springBean",SpringBean.class);
        System.out.println(springBean);


        System.out.println("现在开始关闭容器！");
        ((AnnotationConfigApplicationContext) ctx).removeBeanDefinition("springBean");

    }


//    private CircularReference2 circularReference2;
//    private CircularReference circularReference;
//    @Autowired
//    public void setCircularReference(CircularReference circularReference) {
//        this.circularReference = circularReference;
//    }
//    @Autowired
//    public void setCircularReference2(CircularReference2 circularReference2) {
//        this.circularReference2 = circularReference2;
//    }

    private final CircularReference2 circularReference2;
    private final CircularReference circularReference;

    public MySpringTest(CircularReference circularReference,CircularReference2 circularReference2){
        this.circularReference = circularReference;
        this.circularReference2 = circularReference2;
    }

    /**
     * spring循环依赖
     *
     */
    @Test
    @RequestMapping("/testCircularReference")
    public void test2(){
//        ConfigurableApplicationContext ctx = new AnnotationConfigApplicationContext(CircularReference.class,CircularReference2.class);
//        System.out.println("容器初始化成功");

        circularReference2.test();
        circularReference.test();

    }


}
