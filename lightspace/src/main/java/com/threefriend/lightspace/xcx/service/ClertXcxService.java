package com.threefriend.lightspace.xcx.service;

import java.util.Map;

import com.threefriend.lightspace.mapper.StudentWordMapper;
import com.threefriend.lightspace.vo.ResultVO;

/**
 * 店员的业务逻辑
 * @author Administrator
 *
 */
public interface ClertXcxService {

	
	//登录
	ResultVO clertLogin(Map<String, String> params);
	//验证登录状态
	ResultVO chkState(Map<String, String> params);
	//扫码验货
	ResultVO clertSanningCode(Map<String, String> params);
	//完成的列表
	ResultVO orderList(Map<String, String> params);
	//扫码查看孩子的档案信息
	ResultVO childrenScreening(Map<String, String> params);
	//扫码查看待体验订单
	ResultVO orderShow(Map<String, String> params);
	//录入眼健康的档案
	ResultVO insertStudentWord(StudentWordMapper word);
}
