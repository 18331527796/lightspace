package com.threefriend.lightspace.service;

import com.threefriend.lightspace.vo.ResultVO;

public interface OrderService {

	ResultVO orderList();
	
	ResultVO findOne();
	
	ResultVO deleteOrder();
}
