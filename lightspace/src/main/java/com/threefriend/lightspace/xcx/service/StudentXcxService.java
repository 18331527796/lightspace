package com.threefriend.lightspace.xcx.service;

import java.util.Map;

import com.threefriend.lightspace.vo.ResultVO;

/**
 *	学生业务逻辑接口
 */
public interface StudentXcxService {

	
	//按照学校班级查询
	ResultVO queryBySidCid(Map<String, String> params);
	
	ResultVO queryStudentWord(Map<String, String> params);
	
	ResultVO queryStudentWordById(Map<String, String> params);
}
