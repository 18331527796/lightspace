package com.threefriend.lightspace.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

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
	public ResultVO classesList(@RequestParam Map<String, String> params,HttpSession session) {
		return classes_impl.findAllClasses(params,session);
	}
	
	/**
	 * 修改班级信息
	 * @param params
	 * @return
	 */
	@PostMapping("/editClasses")
	
	public ResultVO editClasses(@RequestParam Map<String, String> params) {
		return classes_impl.findById(Integer.valueOf(params.get("id")));
	}
	
	/**
	 * 修改班级信息后保存
	 * @param params
	 * @return
	 */
	@PostMapping("/saveClasses")
	public ResultVO saveClasses(@RequestParam Map<String, String> params) {
		return classes_impl.alterClasses(params);
	}
	
	/**
	 * 班级删除
	 * @param params
	 * @return
	 */
	@PostMapping("/deleteClasses")
	public ResultVO deleteClasses(@RequestParam Map<String, String> params) {
		return classes_impl.deleteClasses(Integer.valueOf(params.get("id")),params.get("token"));
	}
	
	
	/**
	 * 按照学校找班级
	 * @param params
	 * @return
	 */
	@PostMapping("/queryClassesBySchool")
	public ResultVO queryClassesBySchool(@RequestParam Map<String, String> params) {
		return classes_impl.findBySchoolId(Integer.valueOf(params.get("schoolId")));
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
	public ResultVO cascade1(@RequestParam Map<String, String> params,HttpSession session) {
		return ResultVOUtil.success(classes_impl.cascade1(session));
	}
	
	/**
	 * 一键升年级
	 * @param params
	 * @return
	 */
	@PostMapping("/elevateClass")
	public ResultVO elevateClass(@RequestParam Map<String, String> params,HttpSession session) {
		return classes_impl.elevateClass(params,session);
	}
	
	/**
	 * 班级概况的查询
	 * @param params
	 * @return
	 */
	@PostMapping("/queryClassInStatistics")
	public ResultVO queryClassInStatistics(@RequestParam Map<String, String> params) {
		return classes_impl.queryClassInStatistics(params);
	}
	
	

}
