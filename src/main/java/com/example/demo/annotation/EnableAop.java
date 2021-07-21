package com.example.demo.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
@Documented
public @interface EnableAop {
	/**
	 * 是否启用异常处理，默认使用InternalResponse返回。
	 */
	boolean enableExProcess() default true;

	/**
	 * 是否开启日志打印(建议一些查询就不需要了，占用磁盘)
	 */
	boolean enableLog() default true;
}
