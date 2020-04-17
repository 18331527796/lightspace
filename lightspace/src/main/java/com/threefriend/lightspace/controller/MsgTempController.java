package com.threefriend.lightspace.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.threefriend.lightspace.service.Impl.MsgTempServiceImpl;
import com.threefriend.lightspace.vo.ResultVO;

@RestController
public class MsgTempController {

	@Autowired
	private MsgTempServiceImpl msg_temp_impl;
	
	@PostMapping("msgTempList")
	public ResultVO msgTempList(@RequestParam Map<String, String> params) {
		return msg_temp_impl.tempList(params);
	}
	
	@PostMapping("editMsgTemp")
	public ResultVO editMsgTemp(@RequestParam Map<String, String> params) {
		return msg_temp_impl.editTemp(params);
	}
	
	@PostMapping("saveMsgTemp")
	public ResultVO saveMsgTemp(@RequestParam Map<String, String> params) {
		return msg_temp_impl.saveTemp(params);
	}
	
	@PostMapping("selectedTemp")
	public ResultVO selectedTemp(@RequestParam Map<String, String> params) {
		return msg_temp_impl.selectedTemp(params);
	}
}
