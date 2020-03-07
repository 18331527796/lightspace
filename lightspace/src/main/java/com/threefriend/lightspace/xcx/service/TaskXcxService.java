package com.threefriend.lightspace.xcx.service;

import java.util.Map;

import com.threefriend.lightspace.vo.ResultVO;

public interface TaskXcxService {

	ResultVO xcxTaskList(Map<String, String> params) throws Exception;
	
	ResultVO completeTask(Map<String, String> params);
}
