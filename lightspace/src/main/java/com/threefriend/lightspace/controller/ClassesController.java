package com.threefriend.lightspace.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
	
	public ResultVO addClasses(@RequestParam Map<String, String> params) {
		return classes_impl.addClasses(params);
	}
	
	/**
	 * 班级列表
	 * @param params
	 * @return
	 */
	@PostMapping("/classesList")
	
	public ResultVO classesList(@RequestParam Map<String, String> params) {
		return ResultVOUtil.success(classes_impl.findAllClasses(params));
	}
	
	/**
	 * 修改班级信息
	 * @param params
	 * @return
	 */
	@PostMapping("/editClasses")
	
	public ResultVO editClasses(@RequestParam Map<String, String> params) {
		return ResultVOUtil.success(classes_impl.findById(Integer.valueOf(params.get("id"))));
	}
	
	/**
	 * 修改班级信息后保存
	 * @param params
	 * @return
	 */
	@PostMapping("/saveClasses")
	
	public ResultVO saveClasses(@RequestParam Map<String, String> params) {
		return ResultVOUtil.success(classes_impl.alterClasses(params));
	}
	
	/**
	 * 班级删除
	 * @param params
	 * @return
	 */
	@PostMapping("/deleteClasses")
	
	public ResultVO deleteClasses(@RequestParam Map<String, String> params) {
		return ResultVOUtil.success(classes_impl.deleteClasses(Integer.valueOf(params.get("id")),params.get("token")));
	}
	
	/**
	 * 模糊查询
	 * @param params
	 * @return
	 */
	@PostMapping("/queryClasses")
	
	public ResultVO queryClasses(@RequestParam Map<String, String> params) {
		return classes_impl.findByNameLike(params.get("name"));
	}
	
	/**
	 * 按照学校找班级
	 * @param params
	 * @return
	 */
	@PostMapping("/queryClassesBySchool")
	
	public ResultVO queryClassesBySchool(@RequestParam Map<String, String> params) {
		return ResultVOUtil.success(classes_impl.findBySchoolId(Integer.valueOf(params.get("schoolId"))));
	}
	
	/**
	 * 级联（学校到学生）
	 * @param params
	 * @return
	 */
	@PostMapping("/cascade")
	
	public ResultVO cascade(@RequestParam Map<String, String> params) {
		return ResultVOUtil.success(classes_impl.cascade());
	}
	
	/**
	 * 级联 （学校到班级）
	 * @param params
	 * @return
	 */
	@PostMapping("/cascade1")
	
	public ResultVO cascade1(@RequestParam Map<String, String> params) {
		return ResultVOUtil.success(classes_impl.cascade1());
	}
	
	/**
	 * 设置座位保存时间（废弃）
	 * 
	 *//*
	@PostMapping("/setSaveTime")
	
	public ResultVO setSaveTime(@RequestParam Map<String, String> params) {
		classes_impl.setSaveTime(Integer.valueOf(params.get("classId")), Integer.valueOf(params.get("time")));
		return ResultVOUtil.success();
	}*/
	

}
