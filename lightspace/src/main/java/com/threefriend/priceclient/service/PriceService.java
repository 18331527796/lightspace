package com.threefriend.priceclient.service;

import java.util.Map;

import com.threefriend.lightspace.vo.ResultVO;

public interface PriceService {

	ResultVO getAllLabel();
	
	ResultVO getSeriesByLabel(Map<String, String> params);
	
	ResultVO getProduceBySeries(Map<String, String> params);
	
	ResultVO loginPrice(Map<String, String> params);
	
	ResultVO changePassword(Map<String, String> params);
	
	ResultVO contrast(Map<String, String> params);
	
	ResultVO findGlassesBySeries(Map<String, String> params);
	
	ResultVO contrastGlasses(Map<String, String> params);
}
