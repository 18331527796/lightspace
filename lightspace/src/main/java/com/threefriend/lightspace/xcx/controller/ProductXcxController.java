package com.threefriend.lightspace.xcx.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.xcx.service.Impl.ProductXcxServiceImpl;

@RestController
@RequestMapping("/xcx")
public class ProductXcxController {

	@Autowired
	private ProductXcxServiceImpl product_impl;
	
	@PostMapping("/productList")
	public ResultVO productList(@RequestParam Map<String, String> params) {
		return product_impl.productPage(params);
	}
	
	@PostMapping("/productDetils")
	public ResultVO productDetils(@RequestParam Map<String, String> params) {
		return product_impl.productDetils(params);
	}
}
