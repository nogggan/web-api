package com.gxf.webapi.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 甘晓锋
 *
 * 2018年6月19日
 */
public abstract class AbstractFilter implements Filter {
	
	private FilterConfig config;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		config = filterConfig;
		init();
	}

	protected void init() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		doFilter(request,response,chain);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
	public abstract void doFilter(HttpServletRequest request,HttpServletResponse response,FilterChain chain)
			throws IOException, ServletException;
}
