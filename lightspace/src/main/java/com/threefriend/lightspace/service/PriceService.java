package com.threefriend.lightspace.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.threefriend.lightspace.vo.ResultVO;

public interface PriceService {
	
	ResultVO labelList(Map<String, String> params);
	
	ResultVO addLabel(Map<String, String> params);
	
	ResultVO editLabel(Map<String, String> params);
	
	ResultVO saveLabel(Map<String, String> params);
	
	ResultVO deleteLabel(Map<String, String> params);

	ResultVO seriesList(Map<String, String> params);
	
	ResultVO addSeries(Map<String, String> params,MultipartFile details);
	
	ResultVO editSeries(Map<String, String> params);
	
	ResultVO saveSeries(Map<String, String> params,MultipartFile details);
	
	ResultVO findSeriesBylabel(Map<String, String> params);
	
	ResultVO deleteSeries(Map<String, String> params);
	
	ResultVO seriesProductList(Map<String, String> params);
	
	ResultVO addSeriesProduct(Map<String, String> params,MultipartFile details);
	
	ResultVO deleteSeriesProduct(Map<String, String> params);
	
}
