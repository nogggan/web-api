package com.gxf.webapi.exception;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.gxf.webapi.result.CodeMsg;
import com.gxf.webapi.result.Result;

/**
 * @author 甘晓锋
 *
 * 2018年6月19日
 */
@RestControllerAdvice
public class GlobleExceptionHandler {
	
	
	@ExceptionHandler(value=Exception.class)
	public Result<String> handleException(Exception e){
		if(e instanceof BindException){
			BindException bindException = (BindException) e;
			List<ObjectError> objectErrors = bindException.getAllErrors();
			ObjectError objectError = objectErrors.get(0);
			String message = objectError.getDefaultMessage();
			return Result.ERROR(CodeMsg.PARAM_VALID_ERROR.formatParam(message));
		}else if(e instanceof HttpRequestMethodNotSupportedException){
			HttpRequestMethodNotSupportedException exception = (HttpRequestMethodNotSupportedException) e;
			String message = exception.getMessage();
			return Result.ERROR(CodeMsg.NOT_SUPPORT_METHOD(message));
		}else if(e instanceof ConstraintViolationException){
			ConstraintViolationException exception = (ConstraintViolationException) e;
			String message = exception.getMessage();
			message = message.substring(message.indexOf(":")+1);
			return Result.ERROR(CodeMsg.PARAM_VALID_ERROR.formatParam(message));
		}else if(e instanceof GlobalException){
			GlobalException globalException = (GlobalException) e;
			return Result.ERROR(globalException.getCodeMsg());
		}
		e.printStackTrace();
		return Result.ERROR(CodeMsg.SERVER_ERROR);
	}

}
