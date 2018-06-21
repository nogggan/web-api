package com.gxf.webapi.redis;
/**
 * @author 甘晓锋
 *
 * 2018年6月19日
 */
public interface PrefixKey {
	
	String prefix();
	
	int expire();

}
