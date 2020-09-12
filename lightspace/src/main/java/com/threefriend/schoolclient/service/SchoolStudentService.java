package com.threefriend.schoolclient.service;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;

import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.vo.StudentStatisticsVO;

public interface SchoolStudentService {

	ResultVO getAllStudent(Map<String, String> params,HttpSession session);
	
	ResultVO getStudentByClass(Map<String, String> params);
	
	ResultVO addStudent(Map<String, String> params);
	
	ResultVO editStudent(Map<String, String> params);
	
	ResultVO saveStudent(Map<String, String> params);
	
	ResultVO deleteStudent(Map<String, String> params);

	ResultVO readStudentExcel(MultipartFile file);
	
	ResultVO getAllWord(Map<String, String> params,HttpSession session);

	ResultVO  findAllByStudentId(Map<String, String> params);
	
}
