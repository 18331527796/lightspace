package com.threefriend.lightspace.service.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import com.threefriend.lightspace.enums.ResultEnum;
import com.threefriend.lightspace.mapper.RightMapper;
import com.threefriend.lightspace.mapper.RoleRightRelation;
import com.threefriend.lightspace.mapper.UserMapper;
import com.threefriend.lightspace.mapper.UserRoleRelation;
import com.threefriend.lightspace.repository.RightRepository;
import com.threefriend.lightspace.repository.RoleRepository;
import com.threefriend.lightspace.repository.RoleRightRepository;
import com.threefriend.lightspace.repository.UserRepository;
import com.threefriend.lightspace.repository.UserRoleRepository;
import com.threefriend.lightspace.service.UserService;
import com.threefriend.lightspace.util.RedisUtils;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.util.TokenUtils;
import com.threefriend.lightspace.vo.MenuListVo;
import com.threefriend.lightspace.vo.ResultVO;

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
	@Autowired
	private RoleRepository role_dao;
	
	
	/*
	 * 新增用户
	 */
	@Override
	public ResultVO insertUser(Map<String, String> params) {
		List<UserMapper> list = user_dao.findByLoginName(params.get("loginName"));
		if(list.size()>=1)return ResultVOUtil.error(ResultEnum.LOGINNAME_REPEAT.getStatus(),ResultEnum.LOGINNAME_REPEAT.getMessage());
		UserMapper user = new UserMapper();
		user.setGenTime(new Date());
		user.setName(params.get("name"));
		user.setLoginName(params.get("loginName"));
		user.setRoleName(role_dao.findById(Integer.valueOf(params.get("roleId"))).get().getRoleName());
		user.setPassword(DigestUtils.md5DigestAsHex(params.get("password").getBytes()));
		if(!StringUtils.isEmpty(params.get("schoolId")))user.setSchoolId(Integer.valueOf(params.get("schoolId")));
		if(!StringUtils.isEmpty(params.get("classId")))user.setClassesId(Integer.valueOf(params.get("classId")));;
		user_dao.save(user);
		UserRoleRelation userRole= new UserRoleRelation();
		userRole.setUserId(user.getId());
		userRole.setRoleId(Integer.valueOf(params.get("roleId")));
		user_role_dao.save(userRole);
		return ResultVOUtil.success(user_dao.findAll());
	}

	/*
	 * 查询所有用户
	 */
	@Override
	public ResultVO findAll(Map<String, String> params) {
		int page = 0 ;
		if(!StringUtils.isEmpty(params.get("page")))page = Integer.valueOf(params.get("page")) - 1 ; 
		return ResultVOUtil.success(user_dao.findAll(PageRequest.of(page, 10)));
	}

	/* 
	 * 登录方法
	 */
	@Override
	public Object login(String loginname, String password,HttpSession session) {
		// 比如对密码进行 md5 加密
		String md5 = DigestUtils.md5DigestAsHex(password.getBytes());
		List<UserMapper> user = user_dao.findByloginNameAndPassword(loginname, md5);
		if(1 !=user.size())return ResultVOUtil.error(ResultEnum.LOGIN_FAIL.getStatus(), ResultEnum.LOGIN_FAIL.getMessage());
		Map<String, String> token = new HashMap<String, String>();
		//正式用的时候放开
		String tokenstr = TokenUtils.getToken(user.get(0).getId());
		//这里要用上面的tokenstr传递前端
		redisUtil.setValueTime(tokenstr, user.get(0).getId().toString(),1800);//1800
		Integer roleId = user_role_dao.findByUserId(user.get(0).getId()).get(0).getRoleId();
		String mark="0";
		if(roleId==2)mark=user.get(0).getSchoolId().toString();
		if(roleId==3)mark=user.get(0).getClassesId().toString();
		tokenstr=tokenstr+"-"+roleId+"-"+mark;
		token.put("token", tokenstr);
		//用户名放session里面
		session.setAttribute("userName", user.get(0).getName());
		return ResultVOUtil.success(token);
	}

	/* 
	 * 菜单list
	 */
	@Override
	public Object getUserRight(String tokenstr) {
		String[] token = tokenstr.split("-");
		// 根据token查找用户id
		Integer userId =Integer.valueOf(redisUtil.get(token[0]))/* 2*/;
		// 根据用户id查找对应的角色
		List<UserRoleRelation> roleId = user_role_dao.findByUserId(userId);
		// 根据角色查找相应的权限
		List<RoleRightRelation> findByRoleId = role_right_dao.findByRoleId(roleId.get(0).getRoleId());
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

	/* 
	 * 删除用户
	 */
	@Override
	public List<UserMapper> deleteUser(Integer id) {
		user_dao.deleteById(id);
		user_role_dao.deleteByUserId(id);
		return user_dao.findAll();
	}

	/* 
	 * 模糊查询
	 */
	@Override
	public ResultVO findByNameLike(String name) {
		List<UserMapper> list = user_dao.findByNameLike("%"+name+"%");
		if(list==null||list.size()==0)return ResultVOUtil.error(ResultEnum.USERSIZE_NULL.getStatus(), ResultEnum.USERSIZE_NULL.getMessage());
		return ResultVOUtil.success(list); 
	}

}
