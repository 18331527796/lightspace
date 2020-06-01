package com.threefriend.lightspace.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.threefriend.lightspace.vo.ResultVO;

/**
 * word业务逻辑
 */
public interface StudentWordService {

	//解析word文件储存
	ResultVO readStudentWord(MultipartFile[] file);
	
	ResultVO wordList(Map<String, String> params);
}
