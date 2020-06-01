package com.threefriend.lightspace.xcx.service;

import java.util.Map;

import com.threefriend.lightspace.vo.ResultVO;

public interface ProductXcxService {

	ResultVO productPage(Map<String, String> params);
	
	ResultVO productDetils(Map<String, String> params);
	
	ResultVO findProduct(Map<String, String> params);
}
