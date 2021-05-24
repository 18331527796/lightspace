package com.threefriend.dingding.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.threefriend.dingding.dto.TaskRecordDTO;
import com.threefriend.dingding.service.DingDingService;
import com.threefriend.dingding.util.HttpUtils;
import com.threefriend.dingding.util.ResultVOUtil;
import com.threefriend.dingding.vo.ResultVO;

@RestController
public class DingDingController {
	@Autowired
	private DingDingService dingding;

	@SuppressWarnings("rawtypes")
	@RequestMapping("/test")
	public ResultVO test() {
		Map<String , String > end = new HashMap<String, String>();
		end.put("String", "Hello Word JavaEE");
		return ResultVOUtil.success(end);
	}
	
	
	@PostMapping("/login")
	public ResultVO login(@RequestParam(value="code") String code) {
		return dingding.login(code);
	}
	
	@PostMapping("/getDeptList")
	public ResultVO getDeptList() {
		return dingding.getDeptList();
	}
	
	@PostMapping("/getUserTaskList")
	public ResultVO getUserTaskList(@RequestParam Map<String, String> param) {
		return dingding.getUserTaskList(param);
	}
	
	@PostMapping("/getUserRecord")
	public ResultVO getUserRecord(@RequestParam Map<String, String> param) {
		return dingding.getUserRecord(param);
	}
	
	@PostMapping("/pushRemark")
	public ResultVO pushRemark(@RequestParam Map<String, String> param) {
		return dingding.pushRemark(param);
	}
	
	  
}
