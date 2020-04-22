package com.threefriend.lightspace.xcx.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.threefriend.lightspace.service.Impl.xcx.ParentXcxServiceImpl;
import com.threefriend.lightspace.vo.ResultVO;

/**
 *	家长控制器
 */
@RestController
@RequestMapping("/xcx")
public class ParentXcxController {

	@Autowired
	private ParentXcxServiceImpl parent_impl;
	
	
	@PostMapping("/loginXcx")
	public ResultVO loginXcx(@RequestParam Map<String, String> params) throws Exception {
		return parent_impl.loginXcx(params);
	}
	
	
	@PostMapping("/childrenList")
	public ResultVO childrenList(@RequestParam Map<String, String> params) {
		return parent_impl.childrenList(params);
	}
	
	/**
	 * 绑定孩子 
	 * @param params
	 * @return
	 */
	
	@PostMapping("/binding")
	public ResultVO insertStudent(@RequestParam Map<String, String> params) {
		return parent_impl.insertStudent(params);
	}
	
	/**
	 * 解除绑定孩子 
	 * @param params
	 * @return
	 */
	
	@PostMapping("/relieve")
	public ResultVO relieveStudent(@RequestParam Map<String, String> params) {
		return parent_impl.relieveStudent(params);
	}
	
	/**
	 * 查询孩子详细信息 
	 * @param params
	 * @return
	 */
	
	@PostMapping("/findStudent")
	public ResultVO findStudent(@RequestParam Map<String, String> params) {
		return parent_impl.findStudent(params);
	}
	
	/**
	 * 我的首页
	 * @param params
	 * @return
	 */
	
	@PostMapping("/mine")
	public ResultVO mind(@RequestParam Map<String, String> params) {
		return parent_impl.mine(params);
	}
	
	@PostMapping("/bindingPhone")
	public ResultVO bindingPhone(@RequestParam Map<String, String> params) throws Exception {
		return parent_impl.bindingPhone(params);
	}
}
