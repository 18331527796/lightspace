package com.threefriend.schoolclient.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.threefriend.lightspace.mapper.StudentMapper;
import com.threefriend.lightspace.vo.ResultVO;

public interface SchoolRecordService {

	ResultVO screeningList(Map<String, String> params,HttpSession session);
	
	ResultVO screeningWearList(Map<String, String> params,HttpSession session);
	
	ResultVO screeningByStudentId(Map<String, String> params);
	
	ResultVO screeningWearByStudentId(Map<String, String> params);
	
	ResultVO deleteScreening(Map<String, String> params);
	
	ResultVO deleteScreeningWear(Map<String, String> params);
	//数据概况
	ResultVO recordSurvey(Map<String, String> params,HttpSession session);
	//分级
	ResultVO RecordVisionGrade(Map<String, String> params,HttpSession session);
	//查看报表
	ResultVO ViewGradeReport(Map<String, String> params,HttpSession session);
	//查看报表
	ResultVO ViewClassReport(Map<String, String> params,HttpSession session);

	ResultVO pushReport(Map<String, String> params,HttpSession session);
	//屈光度
	ResultVO diopterList(Map<String, String> params,HttpSession session);
	//屈光度
	ResultVO diopterByStudentId(Map<String, String> params);
	//屈光度
	ResultVO deleteDiopter(Map<String, String> params);

	ResultVO diopterExcelOut(Map<String, String> params,HttpSession session);

	Map<String, List<String>> diopterExcel(List<StudentMapper> student);
}
