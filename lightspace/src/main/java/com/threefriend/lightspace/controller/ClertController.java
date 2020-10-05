package com.threefriend.lightspace.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.threefriend.lightspace.service.Impl.ClertServiceImpl;
import com.threefriend.lightspace.vo.ResultVO;

@RestController
public class ClertController {

	@Autowired
	private ClertServiceImpl impl;
	
	@PostMapping("/clertList")
	public ResultVO clertList(@RequestParam Map<String, String> params,HttpSession session) {
		return impl.clertList(params,session);
	}
	
	@PostMapping("/addClert")
	public ResultVO addClert(@RequestParam Map<String, String> params) {
		return impl.addClert(params);
	}
	
	@PostMapping("/editClert")
	public ResultVO editClert(@RequestParam Map<String, String> params) {
		return impl.findById(params);
	}
	
	@PostMapping("/saveClert")
	public ResultVO alertClert(@RequestParam Map<String, String> params) {
		return impl.alertClert(params);
	}
	
	@PostMapping("/deleteClert")
	public ResultVO deleteClert(@RequestParam Map<String, String> params) {
		return impl.deleteClert(params);
	}
}
