package com.threefriend.lightspace.service.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.threefriend.lightspace.Exception.LightException;
import com.threefriend.lightspace.enums.ResultEnum;
import com.threefriend.lightspace.mapper.RightMapper;
import com.threefriend.lightspace.mapper.RoleRightRelation;
import com.threefriend.lightspace.mapper.UserMapper;
import com.threefriend.lightspace.mapper.UserRoleRelation;
import com.threefriend.lightspace.repository.RightRepository;
import com.threefriend.lightspace.repository.RoleRightRepository;
import com.threefriend.lightspace.repository.UserRepository;
import com.threefriend.lightspace.repository.UserRoleRepository;
import com.threefriend.lightspace.service.UserService;
import com.threefriend.lightspace.util.RedisUtils;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.util.TokenUtils;
import com.threefriend.lightspace.vo.MenuListVo;

/**
 * 用户逻辑实现
 * 
 * @author Administrator
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository user_dao;
	@Autowired
	private UserRoleRepository user_role_dao;
	@Autowired
	private RoleRightRepository role_right_dao;
	@Autowired
	private RightRepository right_dao;
	@Resource
	private RedisUtils redisUtil;
	
	
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
	 * 登录方法
	 */
	@Override
	public Object login(String loginname, String password) {
		System.out.println(loginname + "+++++++" + password+"登录名+密码");
		// 比如对密码进行 md5 加密
		String md5 = DigestUtils.md5DigestAsHex(password.getBytes());
		List<UserMapper> findByLoginName = user_dao.findByloginNameAndPassword(loginname, md5);
		if(1 !=findByLoginName.size())return ResultVOUtil.error(ResultEnum.LOGIN_FAIL.getStatus(), ResultEnum.LOGIN_FAIL.getMessage());
		Map<String, String> token = new HashMap<String, String>();
		//正式用的时候放开
		String tokenstr = TokenUtils.getToken(findByLoginName.get(0).getId());
		System.out.println(tokenstr+"-----------用户控制器 生成token");
		//这里要用上面的tokenstr传递前端
		token.put("token", tokenstr);
		redisUtil.setValueTime(tokenstr, findByLoginName.get(0).getId().toString(),1800);//1800
		return ResultVOUtil.success(token);
	}

	/* 
	 * 菜单list
	 */
	@Override
	public Object getUserRight(String token) {
		// 根据token查找用户id
		Integer userId = /* Integer.valueOf(redisUtils.get(token)) */2;
		// 根据用户id查找对应的角色
		List<UserRoleRelation> roleId = user_role_dao.findByUserId(userId);
		// 根据角色查找相应的权限
		List<RoleRightRelation> findByRoleId = role_right_dao.findByRoleId(roleId.get(0).getId());
		List<Integer> ids = new ArrayList<>();
		for (RoleRightRelation roleRightRelation : findByRoleId) {
			ids.add(roleRightRelation.getRightId());
		}
		//组装树形
		List<RightMapper> rights = right_dao.findAllById(ids);
		List<MenuListVo> trees = new ArrayList<MenuListVo>();
		for (RightMapper role1 : rights) {
			MenuListVo menu = new MenuListVo();
			if (role1.getpId() == 0) {
				menu.setId(role1.getId());
				menu.setAuthName(role1.getAuthName());
				for (RightMapper it : rights) {
					if (it.getpId() == role1.getId()) {
						if (menu.getChildren() == null) {
							menu.setChildren(new ArrayList<RightMapper>());
						}
						menu.setId(role1.getId());
						menu.setAuthName(role1.getAuthName());
						menu.getChildren().add(it);
					}
				}
				trees.add(menu);
			}
		}
		return ResultVOUtil.success(trees);
	}

}
