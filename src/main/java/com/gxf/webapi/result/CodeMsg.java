package com.gxf.webapi.result;

import lombok.Getter;

/**
 * @author 甘晓锋
 *
 * 2018年6月19日
 */
@Getter
public class CodeMsg {

	private int code;
	
	private String msg;
	
	private CodeMsg(){}
	
	private CodeMsg(int code,String msg){
		this.code = code;
		this.msg = msg;
	}
	
	public static final CodeMsg MOBILE_FORMAT_ERROR = new CodeMsg(201, "手机格式错误");
	
	public static final CodeMsg PARAM_VALID_ERROR = new CodeMsg(202,"参数校检失败:%s");
	
	public static final CodeMsg USER_NOT_EXISTS = new CodeMsg(203,"用户名错误或密码错误");
	
	public static final CodeMsg OPERATOR_FAILD = new CodeMsg(204,"操作失败，请重新操作");
	
	public static final CodeMsg EXISTS_USER = new CodeMsg(205,"已存在用户");
	
	public static final CodeMsg SERVER_ERROR = new CodeMsg(100,"服务器开小差啦");
	
	public static final CodeMsg GENERIC_ERROR = new CodeMsg(101,"%s");
	
	public static final CodeMsg NOT_SUPPORT_METHOD(String msg){
		return new CodeMsg(404, msg);
	}
	
	public final CodeMsg formatParam(String msg){
		String format = String.format(this.msg,msg);
		return new CodeMsg(this.code,format);
	}

}
