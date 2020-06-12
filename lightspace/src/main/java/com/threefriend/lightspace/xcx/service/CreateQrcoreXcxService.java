package com.threefriend.lightspace.xcx.service;

import java.util.Map;

import com.threefriend.lightspace.vo.ResultVO;

/**
 * 主要是公众号的跳转页面的功能
 * @author Administrator
 *
 */
public interface CreateQrcoreXcxService {

	ResultVO allSchool();
	
	ResultVO classByGradeAndSchool(Map<String, String> params);
	
	ResultVO studentByClassId(Map<String, String> params);
	
	ResultVO chkStudent(Map<String, String> params);
}
