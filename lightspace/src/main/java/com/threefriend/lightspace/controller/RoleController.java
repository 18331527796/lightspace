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
	@ResponseBody
	public ResultVO addRole(@RequestParam Map<String, String> params) {
		return ResultVOUtil.success(role_impl.addRole(params));
	}
	
	/**
	 * 修改角色
	 * @param params
	 * @return
	 */
	@PostMapping("/editRole")
	@ResponseBody
	public ResultVO editRole(@RequestParam Map<String, String> params) {
		return ResultVOUtil.success(role_impl.findById(Integer.valueOf(params.get("id"))));
	}
	
	/**
	 * 修改后保存角色
	 * @param params
	 * @return
	 */
	@PostMapping("/saveRole")
	@ResponseBody
	public ResultVO saveRole(@RequestParam Map<String, String> params) {
		return ResultVOUtil.success(role_impl.saveRole(params));
	}
	
	
	/**
	 * 删除角色
	 * @param params
	 * @return
	 */
	@PostMapping("/deleteRole")
	@ResponseBody
	public ResultVO deleteRole(@RequestParam Map<String, String> params) {
		return ResultVOUtil.success(role_impl.deleteRole(Integer.valueOf(params.get("id"))));
	}
	
	/**
	 * 角色列表
	 * @param params
	 * @return
	 */
	@PostMapping("/roleList")
	@ResponseBody
	public ResultVO roleList() {
		return ResultVOUtil.success(role_impl.roleList());
	}
	
	/**
	 * 角色列表
	 * @param params
	 * @return
	 */
	@PostMapping("/roleRight")
	@ResponseBody
	public ResultVO roleRight(@RequestParam Map<String, String> params) {
		return ResultVOUtil.success(role_impl.roleRight(params));
	}
}
