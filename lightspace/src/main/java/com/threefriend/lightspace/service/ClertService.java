package com.threefriend.lightspace.service;

import java.util.Map;

import javax.servlet.http.HttpSession;

import com.threefriend.lightspace.vo.ResultVO;

/**
 *	店员业务逻辑接口
 */
public interface ClertService {
	//店员列表
	ResultVO clertList(Map<String, String> params,HttpSession session);
	//新增店员
	ResultVO addClert(Map<String, String> params);
	//修改店员
	ResultVO findById(Map<String, String> params);
	//修改后保存
	ResultVO alertClert(Map<String, String> params);
	//删除店员
	ResultVO deleteClert(Map<String, String> params);
}
