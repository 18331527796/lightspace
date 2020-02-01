package com.threefriend.lightspace.service;

import java.util.List;
import java.util.Map;

import com.threefriend.lightspace.mapper.RoleMapper;
import com.threefriend.lightspace.vo.RoleRightVO;

public interface RoleService {
	
	//新增角色
	public List<RoleMapper> addRole(Map<String, String> params);
	//角色列表
	public List<RoleRightVO> roleList();
	//按照id查询角色
	public RoleMapper findById(Integer id);
	//修改后保存角色
	public List<RoleMapper> saveRole(Map<String, String> params);
	//删除角色
	public List<RoleMapper> deleteRole(Integer id);
	//角色分配权限
	public List<RoleMapper> roleRight(Map<String, String> params);

}
