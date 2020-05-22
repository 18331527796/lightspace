package com.threefriend.lightspace.xcx.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.xcx.service.Impl.OrderXcxServiceImpl;

/**
 * 小程序的订单控制器
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/xcx")
public class OrderXcxController {

	@Autowired
	private OrderXcxServiceImpl order_impl;
	
	/**
	 * 下单
	 * @param params
	 * @return
	 */
	@PostMapping("/addOrder")
	public ResultVO addOrder(@RequestParam Map<String, String> params) {
		return order_impl.addOrder(params);
	}
	
	/**
	 * 领取列表
	 * @param params
	 * @return
	 */
	@PostMapping("/orderByStudent")
	public ResultVO orderByStudent(@RequestParam Map<String, String> params) {
		return order_impl.orderByStudent(params);
	}
	
	/**
	 * 确认收货
	 * @param params
	 * @return
	 */
	@PostMapping("/confirmReceipt")
	public ResultVO confirmReceipt(@RequestParam Map<String, String> params) {
		return order_impl.confirmReceipt(params);
	}
	
	/**
	 * 取消显示
	 * @param params
	 * @return
	 */
	@PostMapping("/display")
	public ResultVO display(@RequestParam Map<String, String> params) {
		return order_impl.display(params);
	}
}
