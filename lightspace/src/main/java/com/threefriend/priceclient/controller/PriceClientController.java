package com.threefriend.priceclient.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.priceclient.service.impl.PriceClientServiceImpl;

@RestController
@RequestMapping("/priceclient")
public class PriceClientController {

	@Autowired
	private PriceClientServiceImpl impl;
	
	@PostMapping("/getAllLabel")
	public ResultVO getAllLabel() {
		return impl.getAllLabel();
	}
	
	@PostMapping("/getSeriesByLabel")
	public ResultVO getSeriesByLabel(@RequestParam Map<String , String> params) {
		return impl.getSeriesByLabel(params);
	}
	
	@PostMapping("/getProduceBySeries")
	public ResultVO getProduceBySeries(@RequestParam Map<String , String> params) {
		return impl.getProduceBySeries(params);
	}
	
	@PostMapping("/loginPrice")
	public ResultVO loginPrice(@RequestParam Map<String , String> params) {
		return impl.loginPrice(params);
	}
	
	@PostMapping("/changePassword")
	public ResultVO changePassword(@RequestParam Map<String , String> params) {
		return impl.changePassword(params);
	}
}
