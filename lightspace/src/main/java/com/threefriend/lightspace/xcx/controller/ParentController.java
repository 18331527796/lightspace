package com.threefriend.lightspace.xcx.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.xcx.service.Impl.ParentServiceImpl;

@RestController
@RequestMapping("/xcx")
public class ParentController {

	@Autowired
	private ParentServiceImpl parent_impl;
	
	@ResponseBody
	@RequestMapping("/loginchk")
	public ResultVO loginchk(@RequestParam Map<String, String> params) {
		return parent_impl.loginXcx(params);
	}
	
	@ResponseBody
	@RequestMapping("/childrenList")
	public ResultVO childrenList(@RequestParam Map<String, String> params) {
		return parent_impl.childrenList(params);
	}
	
	/**
	 * 绑定孩子 
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/binding")
	public ResultVO insertStudent(@RequestParam Map<String, String> params) {
		return parent_impl.insertStudent(params);
	}
}
