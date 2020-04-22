package com.threefriend.lightspace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.threefriend.lightspace.service.Impl.WechatMenuServiceImpl;
import com.threefriend.lightspace.vo.ResultVO;

@RestController
public class WechatMenuController {

	@Autowired
	private WechatMenuServiceImpl impl;
	
	@PostMapping("settingMenu")
	public ResultVO settingMenu() {
		return impl.sendPost();
	}
}
