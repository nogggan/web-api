package com.gxf.webapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;

//不需要SpringBoot自动配置Redis
@SpringBootApplication(exclude={RedisAutoConfiguration.class})
@MapperScan(basePackages="com.gxf.webapi.dao")
public class WebApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebApiApplication.class, args);
	}
}
