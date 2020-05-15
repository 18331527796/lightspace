package com.threefriend.lightspace.xcx.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.xcx.service.Impl.TeacherXcxServiceImpl;

@RestController
@RequestMapping("/xcx")
public class TeacherXcxController {

	@Autowired
	private TeacherXcxServiceImpl teacher_impl;
	
	@PostMapping("/teacherLogin")
	public ResultVO teacherLogin(@RequestParam Map<String, String> params) {
		return teacher_impl.teacherLogin(params);
	}
	
	@PostMapping("/chkState")
	public ResultVO chkState(@RequestParam Map<String, String> params) {
		return teacher_impl.chkState(params);
	}
	
	@PostMapping("/teacherPage")
	public ResultVO teacherPage(@RequestParam Map<String, String> params) throws Exception {
		return teacher_impl.teacherPage(params);
	}
	
	@PostMapping("/undetected")
	public ResultVO undetected(@RequestParam Map<String, String> params) throws Exception {
		return teacher_impl.undetected(params);
	}
	
	@PostMapping("/untask")
	public ResultVO untask(@RequestParam Map<String, String> params) throws Exception {
		return teacher_impl.untask(params);
	}
	
	@PostMapping("/decline")
	public ResultVO decline(@RequestParam Map<String, String> params) throws Exception {
		return teacher_impl.decline(params);
	}
	
	@PostMapping("/remindUndetected")
	public ResultVO remindUndetected(@RequestParam("id") Integer[] params) throws Exception {
		return teacher_impl.remindUndetected(params);
	}
	
	@PostMapping("/remindDecline")
	public ResultVO remindDecline(@RequestParam("id") Integer[] params) throws Exception {
		return teacher_impl.remindDecline(params);
	}
	
	@PostMapping("/remindUntask")
	public ResultVO remindUntask(@RequestParam("id") Integer[] params) throws Exception {
		return teacher_impl.remindUntask(params);
	}
	
}
