package com.threefriend.lightspace.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.threefriend.lightspace.service.Impl.ParentServiceImpl;
import com.threefriend.lightspace.service.Impl.TeacherServiceImpl;
import com.threefriend.lightspace.vo.ResultVO;

@RestController
public class TeacherController {

	@Autowired
	private TeacherServiceImpl teacher_impl;
	
	/**
	 * 教师列表
	 * @param params
	 * @return
	 */
	@ResponseBody
	@PostMapping("/teacherList")
	public ResultVO teacherList() {
		return teacher_impl.teacherList();
	}
	
	/**
	 * 新增教师
	 * @param params
	 * @return
	 */
	@ResponseBody
	@PostMapping("/addTeacher")
	public ResultVO addTeacher(@RequestParam Map<String, String> params) {
		return teacher_impl.addTeacher(params);
	}
	
	/**
	 * 修改
	 * @param params
	 * @return
	 */
	@ResponseBody
	@PostMapping("/editTeacher")
	public ResultVO editTeacher(@RequestParam Map<String, String> params) {
		return teacher_impl.findById(params);
	}
	
	/**
	 * 修改后保存
	 * @param params
	 * @return
	 */
	@ResponseBody
	@PostMapping("/saveTeacher")
	public ResultVO alertTeacher(@RequestParam Map<String, String> params) {
		return teacher_impl.alertTeacher(params);
	}
	
	/**
	 * 删除教师
	 * @param params
	 * @return
	 */
	@ResponseBody
	@PostMapping("/deleteTeacher")
	public ResultVO deleteTeacher(@RequestParam Map<String, String> params) {
		return teacher_impl.deleteTeacher(params);
	}
}
