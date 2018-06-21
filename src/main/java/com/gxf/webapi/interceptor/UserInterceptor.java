package com.gxf.webapi.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.gxf.webapi.entities.User;
import com.gxf.webapi.service.UserService;
import com.gxf.webapi.util.JsonUtils;
import com.gxf.webapi.util.RequestContext;
import com.gxf.webapi.util.StringUtils;
import com.gxf.webapi.util.TokenUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 甘晓锋
 *
 * 2018年6月21日
 */
@Component
@Slf4j
public class UserInterceptor implements HandlerInterceptor{
	
	@Autowired
	private UserService userService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String paramToken = request.getParameter(TokenUtils.TOKEN);
		String cookieToken = getByCookie(request);
		String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
		User user = userService.getByToken(token);
		RequestContext.setUser(user);
		log.debug("User:{}",JsonUtils.toString(user));
		return true;
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		RequestContext.removeUser();
	}

	private String getByCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		String tokenCookie = null;
		if(cookies!=null){
			for(Cookie cookie : cookies){
				if(TokenUtils.TOKEN.equals(cookie.getName())){
					tokenCookie = cookie.getValue();
				}
			}
		}
		return tokenCookie;
	}

}
