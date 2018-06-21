package com.gxf.webapi.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.gxf.webapi.util.JsonUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 甘晓锋
 *
 * 2018年6月19日
 */
@Slf4j
public class HttpInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Long time = System.currentTimeMillis();
		request.setAttribute("currentTimeMillis", time);
		String ipAddress = request.getRemoteAddr();
		log.info("PreHandle<<< : args{} | Ip:{}",JsonUtils.toString(request.getParameterMap()),ipAddress);
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		log.info("PostHandle--- : args{}",JsonUtils.toString(request.getParameterMap()));
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		Long beforeTime = (Long) request.getAttribute("currentTimeMillis");
		Long nowTime = System.currentTimeMillis();
		log.info("AfterCompletion>>> : args{} | time:{}",JsonUtils.toString(request.getParameterMap()),nowTime-beforeTime);
	}
	
}
