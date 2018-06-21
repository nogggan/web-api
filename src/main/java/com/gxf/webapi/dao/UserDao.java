package com.gxf.webapi.dao;

import com.gxf.webapi.entities.User;

/**
 * @author 甘晓锋
 *
 * 2018年6月19日
 */
public interface UserDao {

	User getByMobileAndPassword(User user);
	
	int save(User user);
	
	int delete(String mobile);
	
	User getByMobile(String mobile);
	
}
