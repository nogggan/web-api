package com.gxf.webapi.result;

import lombok.Getter;

/**
 * @author 甘晓锋
 *
 * 2018年6月19日
 */
@Getter
public class Result<T> {
	
	private int code;
	
	private String msg;
	
	private T data;
	
	private Result(){}
	
	private Result(T data){
		this.code = 0;
		this.msg = "处理成功";
		this.data = data;
	}
	
	private Result(CodeMsg codeMsg){
		this.code = codeMsg.getCode();
		this.msg = codeMsg.getMsg();
	}
	
	public static final <X> Result<X> SUCCESS(X data){
		return new Result<>(data);
	}
	
	public static final <Y> Result<Y> ERROR(CodeMsg codeMsg){
		return new Result<>(codeMsg);
	}

}
