package com.threefriend.lightspace.service;

import java.util.Map;

import com.threefriend.lightspace.vo.ResultVO;

public interface ReportService {
	
	ResultVO pushReport(Map<String, String> params);
	
	String reaGrade(Integer grade);

}
