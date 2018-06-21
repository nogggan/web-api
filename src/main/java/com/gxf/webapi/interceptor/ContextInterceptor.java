package com.gxf.webapi.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import com.gxf.webapi.util.RequestContext;

/**
 * @author 甘晓锋
 *
 * 2018年6月21日
 */
public class ContextInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		RequestContext.setRequest(request);
		RequestContext.setResponse(response);
		return true;
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		RequestContext.removeRequest();
		RequestContext.removeResponse();
	}
	
}
