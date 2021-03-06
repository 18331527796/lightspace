package com.threefriend.lightspace.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.threefriend.lightspace.mapper.UserMapper;
import com.threefriend.lightspace.vo.ResultVO;

/**
 * 用户业务逻辑
 * @author Administrator
 *
 */
public interface UserService {

	//新增用户
	public Object insertUser(Map<String, String> params);
	//用户列表
	public ResultVO findAll(Map<String, String> params,HttpSession session);
	//用户登录
	public Object login(String loginname, String password,HttpSession session);
	//用户权限
	public Object getUserRight(String token);
	//删除账户
	public ResultVO deleteUser(Integer id);
	//所属人模糊查询
	public ResultVO findByNameLike(String name);
	
	ResultVO addUserSchool(Map<String, String> params);
	
	ResultVO deleteUserSchool(Map<String, String> params);
	
}
