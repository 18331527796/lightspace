package com.threefriend.lightspace.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.threefriend.lightspace.service.Impl.UserServiceImpl;



/**
 * 用户控制器
 * @author Administrator
 *
 */
@RestController
public class UserController {
	
	@Autowired
	private UserServiceImpl user_impl;
	
	/**
	 * 登录的方法
	 * @param params
	 * @return
	 */
	@PostMapping("/login")
	@ResponseBody
	public Object login(@RequestParam Map<String, String> params) {
		return user_impl.login(params.get("loginname"),params.get("password"));
	}
	
	
	/**
	 * 菜单列表
	 * @param params
	 * @return
	 */
	@PostMapping("/menuList")
	@ResponseBody
	public Object menuList(@RequestParam Map<String, String> params) {
		return user_impl.getUserRight(params.get("token"));
	}
	

}
