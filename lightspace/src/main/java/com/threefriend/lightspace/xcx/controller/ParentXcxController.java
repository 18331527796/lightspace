package com.threefriend.lightspace.xcx.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.xcx.service.Impl.ParentXcxServiceImpl;

@RestController
@RequestMapping("/xcx")
public class ParentXcxController {

	@Autowired
	private ParentXcxServiceImpl parent_impl;
	
	@ResponseBody
	@RequestMapping("/loginXcx")
	public ResultVO loginXcx(@RequestParam Map<String, String> params) {
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
	
	/**
	 * 解除绑定孩子 
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/relieve")
	public ResultVO relieveStudent(@RequestParam Map<String, String> params) {
		return parent_impl.relieveStudent(params);
	}
	
	/**
	 * 查询孩子详细信息 
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/findStudent")
	public ResultVO findStudent(@RequestParam Map<String, String> params) {
		return parent_impl.findStudent(params);
	}
	
	/**
	 * 我的首页
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mine")
	public ResultVO mind(@RequestParam Map<String, String> params) {
		return parent_impl.mine(params);
	}
}
