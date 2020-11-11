package com.threefriend.priceclient.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.priceclient.service.impl.PriceServiceImpl;

@RestController
@RequestMapping("/priceclient")
public class PriceController {

	@Autowired
	private PriceServiceImpl impl;
	
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
	
	@PostMapping("/contrast")
	public ResultVO contrast(@RequestParam Map<String , String> params) {
		return impl.contrast(params);
	}
	
	@PostMapping("/findGlassesBySeries")
	public ResultVO findGlassesBySeries(@RequestParam Map<String , String> params) {
		return impl.findGlassesBySeries(params);
	}
	
	@PostMapping("/contrastGlasses")
	public ResultVO contrastGlasses(@RequestParam Map<String , String> params) {
		return impl.contrastGlasses(params);
	}
}
