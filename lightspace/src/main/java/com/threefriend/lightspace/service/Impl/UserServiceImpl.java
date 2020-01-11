package com.threefriend.lightspace.service.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.threefriend.lightspace.mapper.UserMapper;
import com.threefriend.lightspace.repository.UserRepository;
import com.threefriend.lightspace.service.UserService;
import com.threefriend.lightspace.util.ResultUtil;

/**
 * 用户逻辑实现
 * @author Administrator
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository user_dao;

	/* 
	 * 新增用户
	 */
	@Override
	public void insertUser(UserMapper user) {
		user_dao.save(user);
	}

	/* 
	 * 查询所有用户
	 */
	@Override
	public List<UserMapper> findAll() {
		return user_dao.findAll();
	}

	/* 
	 * 按照id查找用户
	 */
	@Override
	public Optional<UserMapper> findOne(Integer id) {
		Optional<UserMapper> findById = user_dao.findById(id);
		return findById;
	}

	/* 
	 * 
	 */
	@Override
	public Object findByLoginNameAndPassword(String loginname, String password) {
		System.out.println(loginname+"+++++++"+password);
		//比如对密码进行 md5 加密
		String md5 = DigestUtils.md5DigestAsHex(password.getBytes());
		List<UserMapper> findByLoginName = user_dao.findByloginNameAndPassword(loginname,password);
		if (1 == findByLoginName.size()) {
			Map<String, String> token = new HashMap<String, String>();
			token.put("token", "666666666");
			return ResultUtil.successResult(200, "登陆成功", token);
		}
		return ResultUtil.successResult(401, "用户名或密码不正确", null);
	}

}
