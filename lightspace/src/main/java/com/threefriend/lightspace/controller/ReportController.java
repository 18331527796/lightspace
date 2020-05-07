package com.threefriend.lightspace.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.threefriend.lightspace.service.Impl.ReportServiceImpl;
import com.threefriend.lightspace.vo.ResultVO;

@RestController
public class ReportController {
	@Autowired
	private ReportServiceImpl report_impl;
	
	@PostMapping("downReport")
	public ResultVO report(@RequestParam Map<String, String> params) {
		return report_impl.pushReport(params);
	}

}
