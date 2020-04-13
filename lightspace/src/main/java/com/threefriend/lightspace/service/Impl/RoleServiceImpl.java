package com.threefriend.lightspace.service.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.threefriend.lightspace.enums.ResultEnum;
import com.threefriend.lightspace.mapper.RightMapper;
import com.threefriend.lightspace.mapper.RoleMapper;
import com.threefriend.lightspace.mapper.RoleRightRelation;
import com.threefriend.lightspace.repository.RightRepository;
import com.threefriend.lightspace.repository.RoleRepository;
import com.threefriend.lightspace.repository.RoleRightRepository;
import com.threefriend.lightspace.service.RoleService;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.MenuListVo;
import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.vo.RoleRightVO;

/**
 *	角色业务逻辑实现
 */
@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository role_dao;
	@Autowired
	private RoleRightRepository role_right_dao;
	@Autowired
	private RightRepository right_dao;

	@Override
	public List<RoleMapper> addRole(Map<String, String> params) {
		RoleMapper role = new RoleMapper();
		role.setRoleName(params.get("roleName"));
		role.setGenTime(new Date());
		if (!StringUtils.isEmpty(params.get("description")))
			role.setDescription(params.get("description"));
		role_dao.save(role);
		return role_dao.findAll();
	}

	@Override
	public List<RoleRightVO> roleList() {
		List<RoleMapper> allrole = role_dao.findAll();
		List<RoleRightVO> meybe = new ArrayList<>();
		for (RoleMapper roleId : allrole) {
			RoleRightVO vo = new RoleRightVO();
			vo.setRoleId(roleId.getId());
			vo.setRoleName(roleId.getRoleName());
			vo.setDescription(roleId.getDescription());
			// 根据角色查找相应的权限
			List<RoleRightRelation> findByRoleId = role_right_dao.findByRoleId(roleId.getId());
			List<Integer> ids = new ArrayList<>();
			for (RoleRightRelation roleRightRelation : findByRoleId) {
				ids.add(roleRightRelation.getRightId());
			}
			// 组装树形
			List<RightMapper> rights = right_dao.findAllById(ids);
			List<MenuListVo> trees = new ArrayList<MenuListVo>();
			for (RightMapper right : rights) {
				MenuListVo menu = new MenuListVo();
				if (right.getpId() == 0) {
					menu.setId(right.getId());
					menu.setAuthName(right.getAuthName());
					for (RightMapper it : rights) {
						if (it.getpId() == right.getId()) {
							if (menu.getChildren() == null) {
								menu.setChildren(new ArrayList<RightMapper>());
							}
							menu.setId(right.getId());
							menu.setAuthName(right.getAuthName());
							menu.getChildren().add(it);
						}
					}
					trees.add(menu);
				}
			}
			vo.setChildren(trees);
			meybe.add(vo);
		}
		return meybe;
	}

	@Override
	public RoleMapper findById(Integer id) {
		return role_dao.findById(id).get();
	}

	@Override
	public List<RoleMapper> saveRole(Map<String, String> params) {
		RoleMapper role = role_dao.findById(Integer.valueOf(params.get("id"))).get();
		if (!StringUtils.isEmpty(params.get("roleName")))
			role.setRoleName(params.get("roleName"));
		if (!StringUtils.isEmpty(params.get("description")))
			role.setDescription(params.get("description"));
		role_dao.save(role);
		return role_dao.findAll();
	}

	@Override
	public List<RoleMapper> deleteRole(Integer id) {
		role_dao.deleteById(id);
		return role_dao.findAll();
	}

	/*
	 * 角色分配权限
	 */
	@Override
	public List<RoleMapper> roleRight(Map<String, String> params) {
		String[] split = params.get("rightId").split("-");
		Integer roleId = Integer.valueOf(params.get("roleId"));
		role_right_dao.deleteByRoleId(roleId);
		for (String string : split) {
			RoleRightRelation roleRight = new RoleRightRelation();
			roleRight.setRightId(Integer.valueOf(string));
			roleRight.setRoleId(roleId);
			role_right_dao.save(roleRight);
		}
		return role_dao.findAll();
	}

	@Override
	public ResultVO findByRoleNameLike(String name) {
		List<RoleMapper> list = role_dao.findByRoleNameLike("%"+name+"%");
		if(list==null||list.size()==0)return ResultVOUtil.error(ResultEnum.ROLESIZE_NULL.getStatus(), ResultEnum.ROLESIZE_NULL.getMessage());
		return ResultVOUtil.success(list);
	}

}
