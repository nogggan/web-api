package com.gxf.webapi.exception;

import com.gxf.webapi.result.CodeMsg;

import lombok.Getter;

/**
 * @author 甘晓锋
 *
 * 2018年6月19日
 */
@Getter
public class GlobalException extends RuntimeException {

	private CodeMsg codeMsg;
	
	public GlobalException(CodeMsg codeMsg){
		this.codeMsg = codeMsg;
	}

}
