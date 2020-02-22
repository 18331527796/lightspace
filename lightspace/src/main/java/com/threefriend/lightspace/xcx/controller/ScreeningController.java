package com.threefriend.lightspace.xcx.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.xcx.service.ScreeningService;

/**
 * 筛查控制层
 *
 */
@RestController
@RequestMapping("/xcx")
public class ScreeningController {

	@Autowired
	private ScreeningService screening_impl;
	
	@ResponseBody
	@RequestMapping("/cascade")
	public ResultVO selectStudent() {
		return screening_impl.selectStudent();
	}
	
	@ResponseBody
	@RequestMapping("/binding")
	public ResultVO insertStudent(@RequestParam Map<String, String> params) {
		return screening_impl.insertStudent(params);
	}
	
	@ResponseBody
	@RequestMapping("/addScreening")
	public ResultVO addScreening(@RequestParam Map<String, String> params) {
		return screening_impl.addScreening(params);
	}
	
	@ResponseBody
	@RequestMapping("/optotype")
	public ResultVO optotype() {
		return screening_impl.selectOptotype();
	}
}
