package com.threefriend.lightspace.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.threefriend.lightspace.service.Impl.WechatMenuServiceImpl;
import com.threefriend.lightspace.vo.ResultVO;

@RestController
public class WechatMenuController {

	@Autowired
	private WechatMenuServiceImpl impl;
	
	@PostMapping("settingWechatMenu")
	public ResultVO settingMenu() {
		return impl.sendPost();
	}
	
	@PostMapping("menuWechatList")
	public ResultVO menuList() {
		return impl.menuList();
	}
	
	@PostMapping("addWechatMenu")
	public ResultVO addMenu(@RequestParam Map<String, String> params) {
		return impl.addMenu(params);
	}
	
	@PostMapping("editWechatMenu")
	public ResultVO editMenu(@RequestParam Map<String, String> params) {
		return impl.editMenu(params);
	}
	
	@PostMapping("saveWechatMenu")
	public ResultVO saveMenu(@RequestParam Map<String, String> params) {
		return impl.saveMenu(params);
	}
	
	@PostMapping("deleteWechatMenu")
	public ResultVO deleteMenu(@RequestParam Map<String, String> params) {
		return impl.deleteMenu(params);
	}
	
}
