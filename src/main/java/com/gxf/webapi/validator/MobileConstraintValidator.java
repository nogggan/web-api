package com.gxf.webapi.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.gxf.webapi.util.PatternUtils;

/**
 * @author 甘晓锋
 *
 * 2018年6月19日
 */
public class MobileConstraintValidator implements ConstraintValidator<Mobile, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return PatternUtils.isMobile(value);
	}

}
