package com.threefriend.lightspace.service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.threefriend.lightspace.mapper.UserMapper;
import com.threefriend.lightspace.repository.UserRepository;
import com.threefriend.lightspace.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository user_dao;

	@Override
	public void insertUser(UserMapper user) {
		user_dao.save(user);
	}

	@Override
	public List<UserMapper> findAll() {
		
		return user_dao.findAll();
	}

	@Override
	public Optional<UserMapper> findOne(Integer id) {
		// TODO Auto-generated method stub
		Optional<UserMapper> findById = user_dao.findById(id);
		return findById;
	}

	@Override
	public List<UserMapper> findByLoginName(String loginname) {
		List<UserMapper> findByLoginName = user_dao.findByloginName(loginname);
		return findByLoginName;
	}



}
