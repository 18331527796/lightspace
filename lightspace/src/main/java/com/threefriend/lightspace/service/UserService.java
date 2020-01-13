package com.threefriend.lightspace.service;

import java.util.List;
import java.util.Optional;

import com.threefriend.lightspace.mapper.UserMapper;

public interface UserService {

	//新增用户
	public void insertUser(UserMapper user);
	//用户列表
	public List<UserMapper> findAll();
	//用户登录
	public Object login(String loginname, String password);
	//用户权限
	public Object getUserRight(String token);
	
	
}
