package com.gxf.webapi.redis;

public class UserPrefixKey extends BasePrefix {

	public UserPrefixKey(String selfPrefix) {
		super(selfPrefix);
	}
	
	
	
	public UserPrefixKey(String selfPrefix, int selfExpire) {
		super(selfPrefix, selfExpire);
	}



	public static final UserPrefixKey mobile = new UserPrefixKey("mobile");
	
	public static final UserPrefixKey token = new UserPrefixKey("token",1800);

}
