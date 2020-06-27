package com.threefriend.lightspace.service;


import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.threefriend.lightspace.vo.ResultVO;

public interface TaskExamineService {

	ResultVO taskExamineList(Map<String, String> params);
	
	ResultVO deleteTaskExamine(Map<String, String> params);
	
	ResultVO examineTask(Map<String, String> params);
	
	ResultVO addMomentsConfig(Map<String, String> params,MultipartFile file);
	
	ResultVO momentsConfigList(Map<String, String> params);
	
	ResultVO deleteMomentsConfig(Map<String, String> params);
	
	ResultVO changeMomentsConfigDisplay(Map<String, String> params);
	
	
}
