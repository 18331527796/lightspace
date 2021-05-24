package com.threefriend.dingding.service;

import java.util.Map;

import com.threefriend.dingding.dto.TaskRecordDTO;
import com.threefriend.dingding.mapper.RemarksMapper;
import com.threefriend.dingding.vo.ResultVO;

public interface DingDingService {

	ResultVO login(String code);
	
	ResultVO getDeptList();
	
	ResultVO getUserTaskList(Map<String, String> param);
	
	ResultVO getUserRecord(Map<String, String> param);
	
	ResultVO pushRemark(Map<String, String> param);
	
}
