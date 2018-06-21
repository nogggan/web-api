package com.gxf.webapi.entities;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gxf.webapi.serializer.DateSerializer;
import com.gxf.webapi.validator.CreateView;
import com.gxf.webapi.validator.DeleteView;
import com.gxf.webapi.validator.Mobile;
import com.gxf.webapi.validator.NotBlank;
import com.gxf.webapi.validator.SelectView;

import lombok.Data;

@Data
public class User {
	
	private Integer id;
	
	@NotBlank(message="用户名不能为空!",groups={CreateView.class})
	private String name;
	
	@NotBlank(message="密码不能为空!",groups={SelectView.class,CreateView.class})
	private String password;
	
	@Mobile(groups={SelectView.class,CreateView.class,DeleteView.class})
	private String mobile;
	
	@JsonSerialize(using=DateSerializer.class)
	private Date createTime;
	

}
