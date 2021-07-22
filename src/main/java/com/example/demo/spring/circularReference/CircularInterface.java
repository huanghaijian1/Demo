package com.example.demo.spring.circularReference;

import org.springframework.beans.factory.BeanCurrentlyInCreationException;
import org.springframework.util.StringUtils;

public interface CircularInterface {

    public void test();
//    protected Object doCreateBean( ... ){
//...
//        boolean earlySingletonExposure = (mbd.isSingleton() && this.allowCircularReferences && isSingletonCurrentlyInCreation(beanName));
//        if (earlySingletonExposure) {
//            addSingletonFactory(beanName, () -> getEarlyBeanReference(beanName, mbd, bean));
//        }
//...

// populateBean这一句特别的关键，它需要给A的属性赋值，所以此处会去实例化B~~
// 而B我们从上可以看到它就是个普通的Bean（并不需要创建代理对象），实例化完成之后，继续给他的属性A赋值，而此时它会去拿到A的早期引用
// 也就在此处在给B的属性a赋值的时候，会执行到上面放进去的Bean A流程中的getEarlyBeanReference()方法  从而拿到A的早期引用~~
// 执行A的getEarlyBeanReference()方法的时候，会执行自动代理创建器，但是由于A没有标注事务，所以最终不会创建代理，so B合格属性引用会是A的**原始对象**
// 需要注意的是：@Async的代理对象不是在getEarlyBeanReference()中创建的，是在postProcessAfterInitialization创建的代理
// 从这我们也可以看出@Async的代理它默认并不支持你去循环引用，因为它并没有把代理对象的早期引用提供出来~~~（注意这点和自动代理创建器的区别~）

// 结论：此处给A的依赖属性字段B赋值为了B的实例(因为B不需要创建代理，所以就是原始对象)
// 而此处实例B里面依赖的A注入的仍旧为Bean A的普通实例对象（注意  是原始对象非代理对象）  注：此时exposedObject也依旧为原始对象
//        populateBean(beanName, mbd, instanceWrapper);

// 标注有@Async的Bean的代理对象在此处会被生成~~~ 参照类：AsyncAnnotationBeanPostProcessor
// 所以此句执行完成后  exposedObject就会是个代理对象而非原始对象了
//        exposedObject = initializeBean(beanName, exposedObject, mbd);

//...
// 这里是报错的重点~~~
//        if (earlySingletonExposure) {
//            // 上面说了A被B循环依赖进去了，所以此时A是被放进了二级缓存的，所以此处earlySingletonReference 是A的原始对象的引用
//            // （这也就解释了为何我说：如果A没有被循环依赖，是不会报错不会有问题的   因为若没有循环依赖earlySingletonReference =null后面就直接return了）
//            Object earlySingletonReference = getSingleton(beanName, false);
//            if (earlySingletonReference != null) {
//                // 上面分析了exposedObject 是被@Aysnc代理过的对象， 而bean是原始对象 所以此处不相等  走else逻辑
//                if (exposedObject == bean) {
//                    exposedObject = earlySingletonReference;
//                }
//                // allowRawInjectionDespiteWrapping 标注是否允许此Bean的原始类型被注入到其它Bean里面，即使自己最终会被包装（代理）
//                // 默认是false表示不允许，如果改为true表示允许，就不会报错啦。这是我们后面讲的决方案的其中一个方案~~~
//                // 另外dependentBeanMap记录着每个Bean它所依赖的Bean的Map~~~~
//                else if (!this.allowRawInjectionDespiteWrapping && hasDependentBean(beanName)) {
//                    // 我们的Bean A依赖于B，so此处值为["b"]
//                    String[] dependentBeans = getDependentBeans(beanName);
//                    Set<String> actualDependentBeans = new LinkedHashSet<>(dependentBeans.length);
//
//                    // 对所有的依赖进行一一检查~    比如此处B就会有问题
//                    // “b”它经过removeSingletonIfCreatedForTypeCheckOnly最终返返回false  因为alreadyCreated里面已经有它了表示B已经完全创建完成了~~~
//                    // 而b都完成了，所以属性a也赋值完成儿聊 但是B里面引用的a和主流程我这个A竟然不相等，那肯定就有问题(说明不是最终的)~~~
//                    // so最终会被加入到actualDependentBeans里面去，表示A真正的依赖~~~
//                    for (String dependentBean : dependentBeans) {
//                        if (!removeSingletonIfCreatedForTypeCheckOnly(dependentBean)) {
//                            actualDependentBeans.add(dependentBean);
//                        }
//                    }
//
//                    // 若存在这种真正的依赖，那就报错了~~~  则个异常就是上面看到的异常信息
//                    if (!actualDependentBeans.isEmpty()) {
//                        throw new BeanCurrentlyInCreationException(beanName,
//                                "Bean with name '" + beanName + "' has been injected into other beans [" +
//                                        StringUtils.collectionToCommaDelimitedString(actualDependentBeans) +
//                                        "] in its raw version as part of a circular reference, but has eventually been " +
//                                        "wrapped. This means that said other beans do not use the final version of the " +
//                                        "bean. This is often the result of over-eager type matching - consider using " +
//                                        "'getBeanNamesOfType' with the 'allowEagerInit' flag turned off, for example.");
//                    }
//                }
//            }
//        }
//...
//    }
}
