package com.threefriend.lightspace.service;

import java.util.Map;

import javax.servlet.http.HttpSession;


import com.threefriend.lightspace.vo.ResultVO;

/**
 *	教师业务逻辑接口
 */
public interface TeacherService {
	//教师列表
	ResultVO teacherList(Map<String, String> params,HttpSession session);
	//新增教师
	ResultVO addTeacher(Map<String, String> params);
	//修改教师
	ResultVO findById(Map<String, String> params);
	//修改后保存
	ResultVO alertTeacher(Map<String, String> params);
	//删除教师
	ResultVO deleteTeacher(Map<String, String> params);
	//搜索教师
	ResultVO queryTeacher(Map<String, String> params);
}
