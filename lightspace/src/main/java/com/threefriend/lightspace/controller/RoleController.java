package com.threefriend.lightspace.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.threefriend.lightspace.service.Impl.RoleServiceImpl;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ResultVO;

/**
 * 角色控制器
 * @author Administrator
 *
 */
@RestController
public class RoleController {

	@Autowired
	private RoleServiceImpl role_impl;
	
	/**
	 * 添加角色
	 * @param params
	 * @return
	 */
	@PostMapping("/addRole")
	
	public ResultVO addRole(@RequestParam Map<String, String> params) {
		return ResultVOUtil.success(role_impl.addRole(params));
	}
	
	/**
	 * 修改角色
	 * @param params
	 * @return
	 */
	@PostMapping("/editRole")
	
	public ResultVO editRole(@RequestParam Map<String, String> params) {
		return ResultVOUtil.success(role_impl.findById(Integer.valueOf(params.get("roleId"))));
	}
	
	/**
	 * 修改后保存角色
	 * @param params
	 * @return
	 */
	@PostMapping("/saveRole")
	
	public ResultVO saveRole(@RequestParam Map<String, String> params) {
		return ResultVOUtil.success(role_impl.saveRole(params));
	}
	
	
	/**
	 * 删除角色
	 * @param params
	 * @return
	 */
	@PostMapping("/deleteRole")
	
	public ResultVO deleteRole(@RequestParam Map<String, String> params) {
		return ResultVOUtil.success(role_impl.deleteRole(Integer.valueOf(params.get("roleId"))));
	}
	
	/**
	 * 角色列表
	 * @param params
	 * @return
	 */
	@PostMapping("/roleList")
	
	public ResultVO roleList() {
		return ResultVOUtil.success(role_impl.roleList());
	}
	
	/**
	 * 角色列表
	 * @param params
	 * @return
	 */
	@PostMapping("/roleRight")
	
	public ResultVO roleRight(@RequestParam Map<String, String> params) {
		return ResultVOUtil.success(role_impl.roleRight(params));
	}
	
	/**
	 * 模糊查询
	 * @param params
	 * @return
	 */
	@PostMapping("/queryRole")
	
	public ResultVO queryRole(@RequestParam Map<String, String> params) {
		return role_impl.findByRoleNameLike(params.get("name"));
	}
}
