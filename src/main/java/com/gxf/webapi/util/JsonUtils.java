package com.gxf.webapi.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author 甘晓锋
 *
 * 2018年6月19日
 */
public class JsonUtils {
	
	public static final String toString(Object object){
		if(StringUtils.isEmpty(object))
			return null;
		return JSON.toJSONString(object);
	}
	
	public static final <T> T toObject(String json,Class<T> clazz){
		if(StringUtils.isEmpty(json)){
			return null;
		}
		return JSONObject.toJavaObject(JSONObject.parseObject(json), clazz);
	}

}
