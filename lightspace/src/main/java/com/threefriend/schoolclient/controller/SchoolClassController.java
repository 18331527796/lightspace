package com.threefriend.schoolclient.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.schoolclient.service.SchoolClassService;

@RestController
@RequestMapping("/school")
public class SchoolClassController {

	@Autowired
	private SchoolClassService class_impl;
	
	
	@PostMapping("/classList")
	public ResultVO classList(HttpSession session) {
		return class_impl.classList( session);
	}
	
	@PostMapping("/editClass")
	public ResultVO editClass(@RequestParam Map<String, String> params ) {
		return class_impl.editClass(params);
	}
	
	@PostMapping("/deleteClass")
	public ResultVO deleteClass(@RequestParam Map<String, String> params ) {
		return class_impl.deleteClass(params);
	}
	
	@PostMapping("/addClass")
	public ResultVO addClass(@RequestParam Map<String, String> params , HttpSession session) {
		return class_impl.addClass(params, session);
	}
	
	@PostMapping("/classNumber")
	public ResultVO classNumber() {
		return class_impl.classNumber();
	}
	
	
}
