package com.threefriend.lightspace.xcx.service;

import java.text.ParseException;
import java.util.Map;

import com.threefriend.lightspace.vo.ResultVO;

public interface AnswerXcxService {

	ResultVO list();
	
	ResultVO submit(Map<String, String> params)throws ParseException;
	
	ResultVO answerConfig();
}
