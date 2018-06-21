package com.gxf.webapi.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 甘晓锋
 *
 * 2018年6月19日
 */
public class AjaxFilter extends AbstractFilter {

	@Override
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		chain.doFilter(request, response);
	}

	

}
