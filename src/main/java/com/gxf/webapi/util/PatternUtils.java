package com.gxf.webapi.util;

import java.util.regex.Pattern;

/**
 * @author 甘晓锋
 *
 * 2018年6月19日
 */
public class PatternUtils {
	
	private static final Pattern MOBILE = Pattern.compile("^[1][3,5,7,8,9][0-9]{9}$");
	
	public static final boolean isMobile(String mobile){
		if(StringUtils.isEmpty(mobile))
			return false;
		return MOBILE.matcher(mobile).matches();
	}
}
