package com.threefriend.lightspace.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.threefriend.lightspace.service.Impl.RightServiceImpl;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ResultVO;

/**
 * 权限控制器
 */
@RestController
public class RightController {

	@Autowired
	private RightServiceImpl right_impl;
	
	/**
	 * 添加权限
	 * @param params
	 * @return
	 */
	@PostMapping("/addRight")
	
	public ResultVO addRight(@RequestParam Map<String, String> params) {
		return ResultVOUtil.success(right_impl.addRight(params));
	}
	
	/**
	 * 修改权限
	 * @param params
	 * @return
	 */
	@PostMapping("/editRight")
	
	public ResultVO editRight(@RequestParam Map<String, String> params) {
		return ResultVOUtil.success(right_impl.findById(Integer.valueOf(params.get("id"))));
	}
	
	
	/**
	 * 修改后保存权限
	 * @param params
	 * @return
	 */
	@PostMapping("/saveRight")
	
	public ResultVO saveRight(@RequestParam Map<String, String> params) {
		return ResultVOUtil.success(right_impl.saveRight(params));
	}
	
	/**
	 * 删除权限
	 * @param params
	 * @return
	 */
	@PostMapping("/deleteRight")
	
	public ResultVO deleteRight(@RequestParam Map<String, String> params) {
		return ResultVOUtil.success(right_impl.deleteRight(Integer.valueOf(params.get("id"))));
	}
	
	/**
	 * 权限列表
	 * @param params
	 * @return
	 */
	@PostMapping("/rightList")
	
	public ResultVO rightList(@RequestParam Map<String, String> params) {
		return ResultVOUtil.success(right_impl.rightList());
	}
}
