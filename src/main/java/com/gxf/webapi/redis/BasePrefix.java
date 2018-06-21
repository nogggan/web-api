package com.gxf.webapi.redis;

/**
 * @author 甘晓锋
 *
 * 2018年6月19日
 */
public class BasePrefix implements PrefixKey {
	
	private String selfPrefix;
	private int selfExpire;

	public BasePrefix(String selfPrefix, int selfExpire) {
		super();
		this.selfPrefix = selfPrefix;
		this.selfExpire = selfExpire;
	}
	
	public BasePrefix(String selfPrefix) {
		super();
		this.selfPrefix = selfPrefix;
		this.selfExpire = 0;
	}

	@Override
	public String prefix() {
		return this.getClass().getSimpleName()+":"+this.selfPrefix;
	}

	@Override
	public int expire() {
		return this.selfExpire;
	}

}
