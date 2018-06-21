package com.gxf.webapi.util;

import java.util.UUID;

public interface TokenUtils {

	public static final String TOKEN = "_token";
	
	public static String generateToken(){
		String token = UUID.randomUUID().toString().replace("-", "");
		return token;
	}
	
}
