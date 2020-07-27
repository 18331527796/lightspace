package com.threefriend.lightspace.xcx.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.xcx.service.Impl.RegionServiceImpl;

@RestController
public class RegionController {

	@Autowired
	private RegionServiceImpl region_impl;
	
	@PostMapping("/findRegion")
	public ResultVO findRegion(@RequestParam Map<String, String> params) {
		return region_impl.findRegion(params);
	}
}
