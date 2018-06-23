package com.gxf.webapi.util;

public class DataSourceContext {

	private static final ThreadLocal<String> DATASOURCE = new ThreadLocal<>();
	
	public static final void set(String lookupKey){
		DATASOURCE.set(lookupKey);
	}
	
	public static final String get(){
		return DATASOURCE.get();
	}
	
	public static final void remove(){
		DATASOURCE.remove();
	}
	
}
