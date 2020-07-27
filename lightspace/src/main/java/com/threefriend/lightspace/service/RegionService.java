package com.threefriend.lightspace.service;

import java.util.Map;

import com.threefriend.lightspace.vo.ResultVO;

public interface RegionService {
	
	ResultVO findRegion(Map<String, String> params);

}
