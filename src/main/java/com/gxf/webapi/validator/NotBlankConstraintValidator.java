package com.gxf.webapi.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.gxf.webapi.util.StringUtils;

/**
 * @author 甘晓锋
 *
 * 2018年6月19日
 */
public class NotBlankConstraintValidator implements ConstraintValidator<NotBlank, String> {
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		boolean isValid = !StringUtils.isEmpty(value);
		return isValid;
	}

}
