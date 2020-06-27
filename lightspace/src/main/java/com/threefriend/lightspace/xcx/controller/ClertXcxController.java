package com.threefriend.lightspace.xcx.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.xcx.service.Impl.ClertXcxServiceImpl;

/**
 * 店员的控制器
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/xcx")
public class ClertXcxController {

	@Autowired
	private ClertXcxServiceImpl impl;
	
	/**
	 * 店员登录
	 * @param params
	 * @return
	 */
	@PostMapping("/clertLogin")
	public ResultVO clertLogin(@RequestParam Map<String, String> params) {
		return impl.clertLogin(params);
	}
	
	/**
	 * 店员验证
	 * @param params
	 * @return
	 */
	@PostMapping("/chkClert")
	public ResultVO chkState(@RequestParam Map<String, String> params) {
		return impl.chkState(params);
	}
	
	/**
	 * 店员扫一扫体验的单子
	 * @param params
	 * @return
	 */
	@PostMapping("/clertSanningCode")
	public ResultVO clertSanningCode(@RequestParam Map<String, String> params) {
		return impl.clertSanningCode(params);
	}
	
	/**
	 * 店员扫过的单子列表
	 * @param params
	 * @return
	 */
	@PostMapping("/sanningCodeList")
	public ResultVO orderList(@RequestParam Map<String, String> params) {
		return impl.orderList(params);
	}
	
}
