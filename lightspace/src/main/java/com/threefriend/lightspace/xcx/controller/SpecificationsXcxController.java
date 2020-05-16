package com.threefriend.lightspace.xcx.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.xcx.service.Impl.SpecificationsXcxServiceImpl;

@RestController
@RequestMapping("/xcx")
public class SpecificationsXcxController {

	@Autowired
	private SpecificationsXcxServiceImpl specifications_dao;
	
	@PostMapping("/specificationsList")
	public ResultVO specificationsList(@RequestParam Map<String, String> params) {
		return specifications_dao.SpecificationsList(params);
	}
	
	
	
}
