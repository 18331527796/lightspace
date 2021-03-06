package com.threefriend.lightspace.xcx.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.xcx.service.Impl.StudentXcxServiceImpl;

/**
 *	学生控制器
 */
@RestController
@RequestMapping("/xcx")
public class StudentXcxController {

	@Autowired
	private StudentXcxServiceImpl student_impl;
	
	
	@PostMapping("/queryStudentBySidCid")
	public ResultVO  queryStudentBySidCid (@RequestParam Map<String, String> params) {
		return student_impl.queryBySidCid(params);
	}
	
	@PostMapping("/queryStudentWord")
	public ResultVO  queryStudentWord (@RequestParam Map<String, String> params) {
		return student_impl.queryStudentWord(params);
	}
	
	@PostMapping("/queryStudentWordById")
	public ResultVO  queryStudentWordById (@RequestParam Map<String, String> params) {
		return student_impl.queryStudentWordById(params);
	}
	
	@PostMapping("/perfectStudent")
	public ResultVO  perfectStudent (@RequestParam Map<String, String> params) {
		return student_impl.perfectStudent(params);
	}
	
	@PostMapping("/chkDominantEye")
	public ResultVO  chkDominantEye (@RequestParam Map<String, String> params) {
		return student_impl.chkDominantEye(params);
	}
	
	@PostMapping("/saveDominantEye")
	public ResultVO  saveDominantEye (@RequestParam Map<String, String> params) {
		return student_impl.saveDominantEye(params);
	}
}
