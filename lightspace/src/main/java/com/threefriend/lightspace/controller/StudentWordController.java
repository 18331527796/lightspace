package com.threefriend.lightspace.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.threefriend.lightspace.service.Impl.StudentWordServiceImpl;
import com.threefriend.lightspace.vo.ResultVO;

/**
 * 学生分析报告控制器
 */
@RestController
public class StudentWordController {

	@Autowired
	private StudentWordServiceImpl student_word_impl;
	/**
	 * 导入学生检查报告
	 * @param file
	 * @param token
	 * @return
	 */
	
	@PostMapping(value="/studentWord")
	public ResultVO  studentWord(@RequestParam(value="file",required = false)MultipartFile[] file){
		return student_word_impl.readStudentWord(file);
	}
	
	/**
	 * word列表
	 * @return
	 */
	@PostMapping(value="/studentWordList")
	public ResultVO  wordList(@RequestParam Map<String, String> params){
		return student_word_impl.wordList(params);
	}
}
