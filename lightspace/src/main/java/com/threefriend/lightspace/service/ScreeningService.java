package com.threefriend.lightspace.service;

import java.util.Map;

import com.threefriend.lightspace.vo.ResultVO;

public interface ScreeningService {
	
	ResultVO screeningList(Map<String, String> params);
	
	ResultVO deleteScreening(Map<String, String> params);
	
	ResultVO screeningWearList(Map<String, String> params);
	
	ResultVO deleteScreeningWear(Map<String, String> params);
}
