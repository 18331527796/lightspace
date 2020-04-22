package com.threefriend.lightspace.xcx.controller;

import java.text.ParseException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.threefriend.lightspace.service.Impl.xcx.MarkServiceImpl;
import com.threefriend.lightspace.vo.ResultVO;

/**
 *	每日签到控制器
 */
@RestController
@RequestMapping("/xcx")
public class MarkController {

	@Autowired
	private MarkServiceImpl mark_impl;
	
	
	@PostMapping("/signin")
	public ResultVO signin(@RequestParam Map<String, String> params) throws ParseException {
		return mark_impl.Signin(params);
	}
}
