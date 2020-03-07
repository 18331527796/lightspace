package com.threefriend.lightspace.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.threefriend.lightspace.service.Impl.ParentServiceImpl;
import com.threefriend.lightspace.vo.ResultVO;

@RestController
public class ParentController {

	@Autowired
	private ParentServiceImpl parent_Impl;
	
	/**
	 * 小程序用户列表
	 * @param params
	 * @return
	 */
	@ResponseBody
	@PostMapping("/parentList")
	public ResultVO parentList() {
		return parent_Impl.parentList();
	}
}
