package com.threefriend.lightspace.service;

import java.util.List;
import java.util.Optional;

import com.threefriend.lightspace.mapper.UserMapper;

public interface UserService {

	public void insertUser(UserMapper user);
	
	public List<UserMapper> findAll();
	
	public Optional<UserMapper> findOne(Integer id);
	
	public List<UserMapper> findByLoginName(String loginname);
}
