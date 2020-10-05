package com.threefriend.schoolclient.service;

import java.util.Map;

import javax.servlet.http.HttpSession;

import com.threefriend.lightspace.vo.ResultVO;

public interface SchoolUserService {

	ResultVO login(Map<String, String> params,HttpSession session);
	
	ResultVO regionLogin(Map<String, String> params,HttpSession session);
	
	ResultVO survey(HttpSession session);
	
	ResultVO badPercentage(HttpSession session);
	
	ResultVO visionGrade(HttpSession session);
	
	ResultVO brokenLine(HttpSession session);
	
	ResultVO top5(Map<String, String> params,HttpSession session);
	
	ResultVO getAllClass(HttpSession session);
	
	ResultVO getAllSemester(HttpSession session);
	
	void initialize(Integer schoolId);
	
	ResultVO undetectedList(HttpSession session);
	
	ResultVO badList(HttpSession session);
	
	ResultVO loginOut(HttpSession session);
	
	ResultVO getUserSchools(HttpSession session);
	
	ResultVO getRegionSchools(HttpSession session);
	
	ResultVO changeSession(Map<String, String> params,HttpSession session);
	
}
