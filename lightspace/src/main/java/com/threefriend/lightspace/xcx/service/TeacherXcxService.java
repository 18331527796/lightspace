package com.threefriend.lightspace.xcx.service;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestParam;

import com.threefriend.lightspace.mapper.StudentMapper;
import com.threefriend.lightspace.vo.ResultVO;

public interface TeacherXcxService {

	
	//登录
	ResultVO teacherLogin(Map<String, String> params);
	//验证登录状态
	ResultVO chkState(Map<String, String> params);
	//教师主页
	ResultVO teacherPage(Map<String, String> params)throws Exception ;
	//今天的24小时
	Map<String, Date> beginAndEnd() throws Exception;
	//未检测的人
	ResultVO undetected(Map<String, String> params);
	//未打卡的人
	ResultVO untask(Map<String, String> params)throws Exception;
	//视力下降的人
	ResultVO decline(Map<String, String> params);
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
	//不同程度视力的孩子列表
	ResultVO studentByType(Map<String, String> params);
}
