package com.threefriend.lightspace.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.threefriend.lightspace.service.Impl.OrderServiceImpl;
import com.threefriend.lightspace.vo.ResultVO;

@RestController
public class OrderController {

	@Autowired
	private OrderServiceImpl order_impl;
	
	@PostMapping("/orderList")
	public ResultVO orderList(@RequestParam Map<String , String> params) {
		return order_impl.orderList(params);
	}
	
	@PostMapping("/deleteOrder")
	public ResultVO deleteOrder(@RequestParam Map<String , String> params) {
		return order_impl.deleteOrder(params);
	}
	
	@PostMapping("/deliveryNumber")
	public ResultVO deliveryNumber(@RequestParam Map<String , String> params) {
		return order_impl.deliveryNumber(params);
	}
}
