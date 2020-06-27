package com.threefriend.lightspace.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.threefriend.lightspace.vo.ResultVO;

/**
 * 答题业务逻辑接口
 * @author Administrator
 *
 */
public interface AnswerService {

	ResultVO addAnswer(MultipartFile file);
	
	ResultVO delete(Map<String, String> params);
	
	ResultVO list(Map<String, String> params);
	
	ResultVO editAnswerConfig(Map<String, String> params);

	ResultVO saveAnswerConfig(Map<String, String> params,MultipartFile file);
	
}
