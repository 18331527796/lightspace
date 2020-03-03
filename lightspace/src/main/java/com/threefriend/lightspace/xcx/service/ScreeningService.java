package com.threefriend.lightspace.xcx.service;

import java.util.Map;

import com.threefriend.lightspace.vo.ResultVO;

/**
 *  筛查业务逻辑接口
 *
 */
public interface ScreeningService {
	
	ResultVO selectStudent();
	
	ResultVO insertStudent(Map<String, String> params);
	
	ResultVO addScreening(Map<String, String> params);
	
	ResultVO selectOptotype();
	
	ResultVO findById(Map<String, String> params);

}
