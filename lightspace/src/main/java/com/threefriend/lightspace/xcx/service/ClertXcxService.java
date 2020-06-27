package com.threefriend.lightspace.xcx.service;

import java.util.Map;


import com.threefriend.lightspace.vo.ResultVO;

public interface ClertXcxService {

	
	//登录
	ResultVO clertLogin(Map<String, String> params);
	//验证登录状态
	ResultVO chkState(Map<String, String> params);
	//扫码验货
	ResultVO clertSanningCode(Map<String, String> params);
	//完成的列表
	ResultVO orderList(Map<String, String> params);
}
