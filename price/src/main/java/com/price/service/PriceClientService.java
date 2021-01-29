package com.price.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.price.mapper.GlassesMapper;
import com.price.vo.ResultVO;


public interface PriceClientService {
	
	ResultVO labelList(Map<String, String> params);
	
	ResultVO addLabel(Map<String, String> params);
	
	ResultVO editLabel(Map<String, String> params);
	
	ResultVO saveLabel(Map<String, String> params);
	
	ResultVO deleteLabel(Map<String, String> params);

	ResultVO seriesList(Map<String, String> params);
	
	ResultVO addSeries(Map<String, String> params,MultipartFile details,MultipartFile picture);
	
	ResultVO editSeries(Map<String, String> params);
	
	ResultVO saveSeries(Map<String, String> params,MultipartFile details,MultipartFile picture);
	
	ResultVO findSeriesBylabel(Map<String, String> params);
	
	ResultVO deleteSeries(Map<String, String> params);
	
	ResultVO addGlasses(GlassesMapper vo,MultipartFile picture,MultipartFile customFile);
	
	ResultVO findGlassesBySeries(Map<String, String> params);
	
	ResultVO editGlasses(Map<String, String> params);
	
	ResultVO saveGlasses(GlassesMapper vo,MultipartFile picture,MultipartFile customFile);
	
	ResultVO deleteGlasses(Map<String, String> params);
	
}
