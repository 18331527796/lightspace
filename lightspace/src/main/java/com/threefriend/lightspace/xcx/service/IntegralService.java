package com.threefriend.lightspace.xcx.service;

import java.util.Map;

import com.threefriend.lightspace.vo.ResultVO;

public interface IntegralService {
	//这个账号的积分列表
	ResultVO IntegralListByParentId(Map<String, String> params);
	//爱眼币排行榜
	ResultVO rankingNO10(Map<String, String> params);
}
