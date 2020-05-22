package com.threefriend.lightspace.service;

import java.util.Map;

import com.threefriend.lightspace.vo.ResultVO;

public interface OrderService {

	ResultVO orderList(Map<String, String> params);
	
	ResultVO deleteOrder(Map<String, String> params);
	
	ResultVO deliveryNumber(Map<String, String> params);
}
