package com.threefriend.lightspace.xcx.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.xcx.service.Impl.IntegralServiceImpl;

@RestController
@RequestMapping("/xcx")
public class IntegralController {

	@Autowired
	private IntegralServiceImpl integral_impl;
	
	@ResponseBody
	@RequestMapping("/integralList")
	public ResultVO integralListByParentId(@RequestParam Map<String, String> params) {
		return ResultVOUtil.success(integral_impl.IntegralListByParentId(params));
	}
	
}
