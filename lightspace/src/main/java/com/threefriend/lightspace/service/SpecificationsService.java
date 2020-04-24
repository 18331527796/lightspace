package com.threefriend.lightspace.service;

import java.util.Map;

import com.threefriend.lightspace.vo.ResultVO;

/**
 * 规格业务逻辑层
 * @author Administrator
 *
 */
public interface SpecificationsService {
	
	ResultVO specificationsList(Integer productId);
	
	ResultVO addSpecifications(Map<String, String> params);
	
	ResultVO editSpecifications(Map<String, String> params);
	
	ResultVO saveSpecifications(Map<String, String> params);
	
	ResultVO deleteSpecifications(Map<String, String> params);

}
