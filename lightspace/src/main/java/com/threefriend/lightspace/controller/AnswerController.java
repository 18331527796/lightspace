package com.threefriend.lightspace.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.threefriend.lightspace.service.Impl.AnswerServiceImpl;
import com.threefriend.lightspace.vo.ResultVO;

/**
 * 答题控制器
 * @author Administrator
 *
 */
@RestController
public class AnswerController {

	@Autowired
	private AnswerServiceImpl answer_impl;
	
	@PostMapping("answerExcel")
	public ResultVO answerExcel(@RequestParam(value="file",required = false)MultipartFile file) {
		return answer_impl.addAnswer(file);
	}
	
	@PostMapping("deleteAnswer")
	public ResultVO deleteAnswer(@RequestParam Map<String, String> params) {
		return answer_impl.delete(params);
	}
	
	@PostMapping("answerList")
	public ResultVO answerList(@RequestParam Map<String, String> params) {
		return answer_impl.list(params);
	}
	
}
