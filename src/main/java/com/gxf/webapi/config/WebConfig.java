package com.gxf.webapi.config;

import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.gxf.webapi.filter.AjaxFilter;
import com.gxf.webapi.interceptor.ContextInterceptor;
import com.gxf.webapi.interceptor.HttpInterceptor;
import com.gxf.webapi.interceptor.UserInterceptor;
import com.gxf.webapi.resolver.UserArgumentResolver;

/**
 * @author 甘晓锋
 *
 * 2018年6月19日
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	@Autowired
	private UserInterceptor userInterceptor;

	@Bean
	public FilterRegistrationBean<Filter> ajaxFilter(){
		FilterRegistrationBean<Filter> filter = new FilterRegistrationBean<>();
		filter.setFilter(new AjaxFilter());
		filter.setUrlPatterns(Arrays.asList("/*"));
		return filter;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new HttpInterceptor()).addPathPatterns("/**").order(1);
		registry.addInterceptor(new ContextInterceptor()).addPathPatterns("/**").order(2);
		registry.addInterceptor(userInterceptor).addPathPatterns("/**").order(3);
	}
	
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new UserArgumentResolver());
	}
	
	@Bean
	public MethodValidationPostProcessor methodValidationPostProcessor(){
		return new MethodValidationPostProcessor();
	}
	
}
