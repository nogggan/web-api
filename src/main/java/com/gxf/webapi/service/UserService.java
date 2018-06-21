package com.gxf.webapi.service;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gxf.webapi.dao.UserDao;
import com.gxf.webapi.entities.User;
import com.gxf.webapi.exception.GlobalException;
import com.gxf.webapi.redis.RedisService;
import com.gxf.webapi.redis.UserPrefixKey;
import com.gxf.webapi.request.UserRequest;
import com.gxf.webapi.result.CodeMsg;
import com.gxf.webapi.util.RequestContext;
import com.gxf.webapi.util.StringUtils;
import com.gxf.webapi.util.TokenUtils;

/**
 * @author 甘晓锋
 *
 * 2018年6月19日
 */
@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RedisService redisService;
	
	private User getByMobileAndPass(UserRequest userRequest){
		User dbUser = redisService.get(UserPrefixKey.mobile, userRequest.getMobile(), User.class);
		if(dbUser == null)
			dbUser = userDao.getByMobile(userRequest.getMobile());
		if(dbUser != null){
			if(dbUser.getPassword().equals(userRequest.getPassword())){
				redisService.set(UserPrefixKey.mobile, userRequest.getMobile(), dbUser);
			}else{
				dbUser = null;
			}
		}
		return dbUser;
	}
	
	public String login(UserRequest userRequest){
		User user = getByMobileAndPass(userRequest);
		if(user==null) throw new GlobalException(CodeMsg.USER_NOT_EXISTS);
		//token标识，但token生成策略有待更正 //TODO
		String token = TokenUtils.generateToken();
		addCookie(token,user);
		return token;
	}
	
	public void logout(String token){
		redisService.del(UserPrefixKey.token, token);
		removeCookie();
	}
	
	public User getByToken(String token){
		if(StringUtils.isEmpty(token))
			return null;
		return redisService.get(UserPrefixKey.token, token, User.class);
	}
	
	private void addCookie(String token,User user) {
		HttpServletResponse response = RequestContext.getResponse();
		Cookie cookie = new Cookie(TokenUtils.TOKEN,token);
		cookie.setPath("/");
		response.addCookie(cookie);
		redisService.set(UserPrefixKey.token, token, user);
	}
	
	private void removeCookie(){
		HttpServletResponse response = RequestContext.getResponse();
		Cookie cookie = new Cookie(TokenUtils.TOKEN,"");
		cookie.setMaxAge(0);
		response.addCookie(cookie);
	}

	public boolean save(User user){
		if(userDao.getByMobile(user.getMobile())!=null)
			return false;
		user.setCreateTime(new Date());
		int i = userDao.save(user);
		if(i>0)
			redisService.set(UserPrefixKey.mobile, user.getMobile(), user);
		return  i > 0;
	}
	
	public boolean delete(String mobile){
		int i = userDao.delete(mobile);
		if(i>0)
			redisService.del(UserPrefixKey.mobile, mobile);
		return i > 0;
	}

}
