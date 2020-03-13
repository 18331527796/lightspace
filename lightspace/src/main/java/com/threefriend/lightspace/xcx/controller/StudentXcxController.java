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

@RestController
@RequestMapping("/xcx")
public class StudentXcxController {

	@Autowired
	private StudentXcxServiceImpl student_impl;
	
	@ResponseBody
	@PostMapping("/queryStudentBySidCid")
	public ResultVO  queryStudentBySidCid (@RequestParam Map<String, String> params) {
		return student_impl.queryBySidCid(params);
	}
}