package com.gxf.webapi.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gxf.webapi.entities.User;

public class RequestContext {

	private static final ThreadLocal<HttpServletRequest> REQUEST = new ThreadLocal<>();
	
	private static final ThreadLocal<HttpServletResponse> RESPONSE = new ThreadLocal<>();
	
	private static final ThreadLocal<User> USER = new ThreadLocal<>();
	
	public static final void setRequest(HttpServletRequest request){
		REQUEST.set(request);
	}
	
	public static final HttpServletRequest getRequest(){
		return REQUEST.get();
	}
	
	public static final void removeRequest(){
		REQUEST.remove();
	}
	
	public static final void setResponse(HttpServletResponse response){
		RESPONSE.set(response);
	}
	
	public static final HttpServletResponse getResponse(){
		return RESPONSE.get();
	}
	
	public static final void removeResponse(){
		RESPONSE.remove();
	}
	
	public static final void setUser(User user){
		USER.set(user);
	}
	
	public static final User getUser(){
		return USER.get();
	}
	
	public static final void removeUser(){
		USER.remove();
	}
	
}
