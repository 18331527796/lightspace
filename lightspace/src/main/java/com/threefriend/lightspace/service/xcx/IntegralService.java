package com.threefriend.lightspace.service.xcx;

import java.util.Map;

import com.threefriend.lightspace.vo.ResultVO;

public interface IntegralService {
	//这个账号的积分列表
	ResultVO IntegralListByParentId(Map<String, String> params);

}
