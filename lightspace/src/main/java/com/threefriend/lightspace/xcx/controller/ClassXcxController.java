package com.threefriend.lightspace.xcx.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.threefriend.lightspace.service.Impl.xcx.ClassXcxServiceImpl;
import com.threefriend.lightspace.vo.ResultVO;

/**
 * 班级控制器
 */
@RestController
@RequestMapping("/xcx")
public class ClassXcxController {

	@Autowired
	private ClassXcxServiceImpl class_impl; 
	
	
	@PostMapping("/queryClassBySchool")
	public ResultVO queryClassesBySchool(@RequestParam Map<String, String> params) {
		return class_impl.findBySchoolId(params);
	}
	
	
	
}
