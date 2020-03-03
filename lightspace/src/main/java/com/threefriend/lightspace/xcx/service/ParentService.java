package com.threefriend.lightspace.xcx.service;

import java.util.Map;

import com.threefriend.lightspace.vo.ResultVO;

public interface ParentService {

	ResultVO loginXcx(Map<String, String> params);
	
	ResultVO childrenList(Map<String, String> params);
}
