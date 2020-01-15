package com.threefriend.lightspace.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.threefriend.lightspace.service.Impl.ClassesServiceImpl;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ResultVO;

/**
 * 班级控制器
 * @author Administrator
 *
 */
@RestController
public class ClassesController {
	
	@Autowired
	private ClassesServiceImpl classes_impl;
	
	
	/**
	 * 新增班级
	 * @param params
	 * @return
	 */
	@PostMapping("/addClasses")
	@ResponseBody
	public ResultVO addClasses(@RequestParam Map<String, String> params) {
		return ResultVOUtil.success(classes_impl.addClasses(params));
	}
	
	/**
	 * 班级列表
	 * @param params
	 * @return
	 */
	@PostMapping("/classesList")
	@ResponseBody
	public ResultVO classesList(@RequestParam Map<String, String> params) {
		return ResultVOUtil.success(classes_impl.findAllClasses());
	}
	
	/**
	 * 修改班级信息
	 * @param params
	 * @return
	 */
	@PostMapping("/editClasses")
	@ResponseBody
	public ResultVO editClasses(@RequestParam Map<String, String> params) {
		return ResultVOUtil.success(classes_impl.findById(Integer.valueOf(params.get("id"))));
	}
	
	/**
	 * 修改班级信息后保存
	 * @param params
	 * @return
	 */
	@PostMapping("/saveClasses")
	@ResponseBody
	public ResultVO saveClasses(@RequestParam Map<String, String> params) {
		return ResultVOUtil.success(classes_impl.alterClasses(params));
	}
	
	/**
	 * 班级删除
	 * @param params
	 * @return
	 */
	@PostMapping("/deleteClasses")
	@ResponseBody
	public ResultVO deleteClasses(@RequestParam Map<String, String> params) {
		return ResultVOUtil.success(classes_impl.deleteClasses(Integer.valueOf(params.get("id"))));
	}

}
