package com.threefriend.lightspace.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.threefriend.lightspace.mapper.SchoolMapper;
import com.threefriend.lightspace.service.Impl.SchoolServiceImpl;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ResultVO;

@RestController
public class SchoolController {
	@Autowired
	private SchoolServiceImpl school_Impl;
	
	
	@PostMapping("/addSchool")
	@ResponseBody
	public ResultVO insertSchool(@RequestParam Map<String, String> params) {
		school_Impl.addSchool(params);
		return ResultVOUtil.success();
	}
	
	@PostMapping("/schoolList")
	@ResponseBody
	public ResultVO findAllSchool() {
		List<SchoolMapper> findAllSchool = school_Impl.findAllSchool();
		return ResultVOUtil.success(findAllSchool);
	}

}
