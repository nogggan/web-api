package com.gxf.webapi.aspect;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value={ElementType.METHOD})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface RoutingDataSource {
	
	static String MASTER = "masterDB";
	static String SLAVE = "slaveDB";
	
	String value() default MASTER;

}
