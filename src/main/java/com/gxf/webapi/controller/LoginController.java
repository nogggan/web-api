package com.gxf.webapi.controller;

import javax.validation.Valid;

import org.junit.runners.Parameterized.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gxf.webapi.aspect.EnableLogin;
import com.gxf.webapi.aspect.RoutingDataSource;
import com.gxf.webapi.entities.User;
import com.gxf.webapi.request.UserRequest;
import com.gxf.webapi.result.CodeMsg;
import com.gxf.webapi.result.Result;
import com.gxf.webapi.service.UserService;
import com.gxf.webapi.util.StringUtils;
import com.gxf.webapi.validator.CreateView;
import com.gxf.webapi.validator.Mobile;

/**
 * @author 甘晓锋
 * 2018年6月19日
 */
@RestController
@RequestMapping("/user")
@Validated
public class LoginController {
	
	@Autowired
	private UserService userService;

	/**
	 * 登录接口
	 * @param User 必须传 mobile,password参数
	 * @return
	 */
	@GetMapping("/login")
	@RoutingDataSource(RoutingDataSource.SLAVE)
	@EnableLogin(repeat=true,message="重复登录")
	public Result<String> login(@Valid UserRequest user){
		String token = userService.login(user);
		return Result.SUCCESS(token);
	}
	
	/**
	 * 注册接口
	 * @param User 必须传mobile,name,password参数
	 * @return
	 */
	@GetMapping("/register")
	@EnableLogin(repeat=true,message="请先退出当前账号")
	public Result<String> register(@Validated(value={CreateView.class}) User user){	
		return userService.save(user)?Result.SUCCESS("添加成功"):Result.ERROR(CodeMsg.EXISTS_USER);
	}

	/**
	 * 删除接口
	 * @param mobile 必须传 mobile参数
	 * @return
	 */
	@DeleteMapping("/del/{mobile}")
	@EnableLogin
	public Result<String> delete(@Mobile @PathVariable("mobile")String mobile){	
		return userService.delete(mobile)?Result.SUCCESS("删除成功"):Result.ERROR(CodeMsg.OPERATOR_FAILD);
	}
	
	
	@GetMapping("/logout")
	@EnableLogin
	public Result<String> logout(@RequestParam(value="_token",
			required=false)String paramToken,@CookieValue(value="_token",required=false)String cookieToken){
		String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
		userService.logout(token);
		return Result.SUCCESS("已退出登录");
	}
	
}