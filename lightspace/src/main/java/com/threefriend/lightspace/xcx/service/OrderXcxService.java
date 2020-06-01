package com.threefriend.lightspace.xcx.service;

import java.util.Map;

import com.threefriend.lightspace.vo.ResultVO;

/**
 * 小程序的订单
 * @author Administrator
 *
 */
public interface OrderXcxService {

	ResultVO addOrder(Map<String, String> params);
	
	ResultVO orderByStudent(Map<String, String> params);

	String getopenid(String openid, String out_trade_no, Long total_fee, String productName);
	
	ResultVO confirmReceipt(Map<String, String> params);

	Map<String, String> createOrder(String openid, Long money, int orderId ,String productName);
	
	ResultVO display(Map<String, String> params);
}
