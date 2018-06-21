package com.gxf.webapi.aspect;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 甘晓锋
 *
 * 在需要登录的API接口方法上面标注此注解。 重复验证的优先级比需要登录的优先级高,并且是短路验证。
 * 2018年6月21日
 */
@Target(value={ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnableLogin {

	//true 表示需要登录
	boolean value() default true;
	
	//未登录的错误显示信息
	String message() default "你需要先登录!";
	
	//false 表示不需要对接口API进行重复登录验证，true将验证，重复登录将将禁止访问API接口，并返回错误信息
	boolean repeat() default false;
	
}
