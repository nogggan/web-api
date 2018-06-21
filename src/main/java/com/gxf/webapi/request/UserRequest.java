package com.gxf.webapi.request;



import com.gxf.webapi.validator.Mobile;
import com.gxf.webapi.validator.NotBlank;

import lombok.Data;

/**
 * @author 甘晓锋
 *
 * 2018年6月19日
 */
@Data
public class UserRequest {

	@Mobile
	private String mobile;
	
	@NotBlank(message="密码不能为空！")
	private String password;
	
}
