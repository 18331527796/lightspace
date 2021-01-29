package com.price.service;

import java.util.Map;

import com.price.vo.ResultVO;


public interface PriceService {

	ResultVO getAllLabel();
	
	ResultVO getSeriesByLabel(Map<String, String> params);
	
	ResultVO loginPrice(Map<String, String> params);
	
	ResultVO changePassword(Map<String, String> params);
	
	ResultVO findGlassesBySeries(Map<String, String> params);
	
	ResultVO contrastGlasses(Map<String, String> params);
}
