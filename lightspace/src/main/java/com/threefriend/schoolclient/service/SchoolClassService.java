package com.threefriend.schoolclient.service;

import java.util.Map;

import javax.servlet.http.HttpSession;

import com.threefriend.lightspace.vo.ResultVO;

public interface SchoolClassService {
	
	
	ResultVO editClass(Map<String, String> params);
	
	ResultVO addClass(Map<String, String> params,HttpSession session);
	
	ResultVO saveClass(Map<String, String> params);
	
	ResultVO classList(HttpSession session);
	
	ResultVO deleteClass(Map<String, String> params);
	
	ResultVO classNumber();
	

}
