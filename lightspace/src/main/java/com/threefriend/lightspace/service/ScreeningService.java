package com.threefriend.lightspace.service;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;

import com.threefriend.lightspace.mapper.xcx.ScreeningMapper;
import com.threefriend.lightspace.vo.ResultVO;

public interface ScreeningService {
	
	ResultVO screeningList(Map<String, String> params);
	
	ResultVO deleteScreening(Map<String, String> params);
	
	ResultVO screeningWearList(Map<String, String> params);
	
	ResultVO deleteScreeningWear(Map<String, String> params);
	
	ResultVO screeningExcel(Map<String, String> params);
	
	ResultVO screeningWearExcel(Map<String, String> params);
	
}
