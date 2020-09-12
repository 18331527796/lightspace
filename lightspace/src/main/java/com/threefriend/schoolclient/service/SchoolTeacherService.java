package com.threefriend.schoolclient.service;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.threefriend.lightspace.mapper.StudentMapper;
import com.threefriend.lightspace.vo.ResultVO;

/**
 *	教师业务逻辑接口
 */
public interface SchoolTeacherService {
	//教师列表
	ResultVO teacherList(Map<String, String> params,HttpSession session);
	//新增教师
	ResultVO addTeacher(Map<String, String> params,HttpSession session);
	//修改教师
	ResultVO findById(Map<String, String> params);
	//修改后保存
	ResultVO alertTeacher(Map<String, String> params);
	//删除教师
	ResultVO deleteTeacher(Map<String, String> params);
	//搜索教师
	ResultVO queryTeacher(Map<String, String> params,HttpSession session);
	
	//今天的24小时
	Map<String, Date> beginAndEnd() throws Exception;
	//未检测的人
	ResultVO undetected(Map<String, String> params,HttpSession session);
	//未打卡的人
	ResultVO untask(Map<String, String> params,HttpSession session)throws Exception;
	//视力下降的人
	ResultVO decline(Map<String, String> params,HttpSession session);
	//提醒未检测
	ResultVO remindUndetected (Integer[] params)throws IOException;
	//提醒视力下降
	ResultVO remindDecline (Integer[] params)throws IOException;
	//提醒未打卡
	ResultVO remindUntask (Integer[] params)throws Exception;
	//发送msg
	void sendmsg(StudentMapper student ,String time, String type, String task)throws IOException;
	//拿公众号的token
	String getAccessToken();
}
