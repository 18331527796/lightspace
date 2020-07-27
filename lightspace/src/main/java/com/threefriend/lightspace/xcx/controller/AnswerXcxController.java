package com.threefriend.lightspace.xcx.controller;

import java.text.ParseException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.threefriend.lightspace.aspect.Mylog;
import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.xcx.service.Impl.AnswerXcxServiceImpl;


@RestController
@RequestMapping("/xcx")
public class AnswerXcxController {

	@Autowired
	private AnswerXcxServiceImpl impl;
	
	@Mylog(value=("爱眼答题"))
	@PostMapping("/answerList")
	public ResultVO answerList() {
		return impl.list();
	}
	
	@PostMapping("/answerSubmit")
	public ResultVO answerSubmit(@RequestParam Map<String, String> params) throws ParseException {
		return impl.submit(params);
	}
	
	@PostMapping("/answerConfig")
	public ResultVO answerConfig() {
		return impl.answerConfig();
	}
}
