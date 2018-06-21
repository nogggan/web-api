package com.gxf.webapi.resolver;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;
import org.springframework.beans.BeanUtils;
import org.springframework.core.MethodParameter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import com.gxf.webapi.entities.User;
import com.gxf.webapi.exception.GlobalException;
import com.gxf.webapi.result.CodeMsg;
import com.gxf.webapi.util.RequestContext;

/**
 * @author 甘晓锋
 *
 *	全面接管ModelArrtibuteMethodProcessor对User对象的解析。
 * 2018年6月21日
 */
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterType() == User.class;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		User user = RequestContext.getUser();
		if(user == null){
			String name = parameter.getParameterName();
			user = mavContainer.containsAttribute(name)?(User) mavContainer.getModel().get(name):this.createAttribute(User.class);
			bindParameter(parameter, webRequest, user);
			validatorParameter(parameter,user);
		}
		return user;
	}
	
	private void bindParameter(MethodParameter parameter,NativeWebRequest webRequest,User user) throws Exception {
		Field[] fields = parameter.getParameterType().getDeclaredFields();
		for(Iterator<String> iterator = webRequest.getParameterNames();iterator.hasNext();){
			String paramName = iterator.next();
			for(Field field : fields){
				if(!paramName.equals(field.getName()))
					continue;
				field.setAccessible(true);
				String[] paramValues = webRequest.getParameterValues(paramName);
				field.set(user, paramValues[0]);
			}
		}
	}
	
	private void validatorParameter(MethodParameter parameter,User user){
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Validated parameterAnnotation = parameter.getParameterAnnotation(Validated.class);
		Set<ConstraintViolation<User>> violation = null;
		if(parameterAnnotation != null){
			violation = validator.validate(user,parameterAnnotation.value());
		}else{
			violation = validator.validate(user,Default.class);
		}
		if(violation.size() > 0){
			throw new GlobalException(CodeMsg.GENERIC_ERROR.formatParam(violation.iterator().next().getMessage()));
		}
	}
	
	private User createAttribute(Class<User> clazz){
		return BeanUtils.instantiateClass(User.class);
	}

}
