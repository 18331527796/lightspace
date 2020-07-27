package com.threefriend.lightspace.xcx.service;

import java.text.ParseException;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.threefriend.lightspace.vo.ResultVO;

public interface TaskExamineXcxService {

	ResultVO addTaskPic(Map<String, String> params,MultipartFile[] file)throws Exception;
	
	ResultVO momentsList(Map<String, String> params);
	
	ResultVO myMomentsList(Map<String, String> params);
	
	ResultVO fabulous(Map<String, String> params);
	
	ResultVO flowers(Map<String, String> params)throws ParseException;
	
	ResultVO configPic();
	
	ResultVO deleteMyMoments(Map<String, String> params);
	
	ResultVO allFabulousMsg(Map<String, String> params);
}
