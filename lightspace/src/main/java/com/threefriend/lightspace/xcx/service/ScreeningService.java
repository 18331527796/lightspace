package com.threefriend.lightspace.xcx.service;

import java.util.Map;

import com.threefriend.lightspace.vo.ResultVO;

/**
 *  筛查业务逻辑接口
 *
 */
public interface ScreeningService {
	
	public ResultVO selectStudent();
	
	public ResultVO insertStudent(Map<String, String> params);
	
	public ResultVO addScreening(Map<String, String> params);
	
	public ResultVO selectOptotype();

}
