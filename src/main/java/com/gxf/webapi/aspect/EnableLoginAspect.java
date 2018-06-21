package com.gxf.webapi.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.gxf.webapi.entities.User;
import com.gxf.webapi.exception.GlobalException;
import com.gxf.webapi.result.CodeMsg;
import com.gxf.webapi.util.JsonUtils;
import com.gxf.webapi.util.RequestContext;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 甘晓锋
 *
 * 处理EnableLogin注解的切面
 * 2018年6月21日
 */
@Component
@Aspect
@Slf4j
public class EnableLoginAspect {

	@Pointcut(value="@annotation(EnableLogin)")
	public void pointcut(){}
	
	@Before("pointcut()")
	public void enableLogin(JoinPoint joinpoint){
		Class<?> clazz = joinpoint.getTarget().getClass();
		String methodName = joinpoint.getSignature().getName();
		Class<?>[] args = ((MethodSignature)joinpoint.getSignature()).getParameterTypes();
		try {
			Method method = clazz.getMethod(methodName, args);
			if(method.isAnnotationPresent(EnableLogin.class)){
				EnableLogin enableLogin = method.getAnnotation(EnableLogin.class);
				check(enableLogin);
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}
	
	private void check(EnableLogin enableLogin){
		boolean isCheck = enableLogin.value();
		boolean isRepeat = enableLogin.repeat();
		User user = RequestContext.getUser();
		log.debug("User:{}",JsonUtils.toString(user));
		if(isRepeat){
			if(user!=null){
				handleMessage(enableLogin);
			}
			return;
		}
		if(isCheck){
			if(user==null){
				handleMessage(enableLogin);
			}
		}
	}
	
	private void handleMessage(EnableLogin enableLogin){
		String message = enableLogin.message();
		throw new GlobalException(CodeMsg.GENERIC_ERROR.formatParam(message));
	}
	
}
