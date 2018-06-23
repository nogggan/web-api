package com.gxf.webapi.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.gxf.webapi.util.DataSourceContext;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class RoutingDataSourceAspect {
	
	@Pointcut(value="@annotation(RoutingDataSource)")
	public void pointcut(){}

	@Before("pointcut()")
	public void dynamicDataSource(JoinPoint joinPoint){
		Class<?> clazz = joinPoint.getTarget().getClass();
		String methodName = joinPoint.getSignature().getName();
		Class<?>[] args = ((MethodSignature)joinPoint.getSignature()).getParameterTypes();
		String lookupKey = RoutingDataSource.MASTER;
		try{
			Method method = clazz.getMethod(methodName, args);
			if(method.isAnnotationPresent(RoutingDataSource.class)){
				RoutingDataSource routingDataSource = method.getAnnotation(RoutingDataSource.class);
				lookupKey = routingDataSource.value();
			}
		}catch (Exception e) {
			log.debug("LookupKey : "+lookupKey+"--MethodName : "+methodName ,e);
		}
		DataSourceContext.set(lookupKey);
	}
	
	@After("pointcut()")
	public void after(){
		DataSourceContext.remove();
	}
	
}
