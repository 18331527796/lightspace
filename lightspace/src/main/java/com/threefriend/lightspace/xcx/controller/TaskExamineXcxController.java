package com.threefriend.lightspace.xcx.controller;

import java.text.ParseException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.threefriend.lightspace.aspect.Mylog;
import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.xcx.service.Impl.TaskExamineXcxServiceImpl;

@RestController
@RequestMapping("/xcx")
public class TaskExamineXcxController {

	@Autowired
	private TaskExamineXcxServiceImpl task_examine_impl;
	
	@Mylog(value=("发布秀一秀"))
	@PostMapping("/addTaskPic")
	public ResultVO addTaskPic(@RequestParam Map<String, String> params,
							   @RequestParam(value="file",required=false) MultipartFile[] file) throws Exception {
		return task_examine_impl.addTaskPic(params, file);
	}
	
	@Mylog(value=("秀一秀首页"))
	@PostMapping("/momentsList")
	public ResultVO momentsList(@RequestParam Map<String, String> params) {
		return task_examine_impl.momentsList(params);
	}
	
	@Mylog(value=("我的秀一秀"))
	@PostMapping("/myMomentsList")
	public ResultVO myMomentsList(@RequestParam Map<String, String> params) {
		return task_examine_impl.myMomentsList(params);
	}

	@PostMapping("/deleteMyMoments")
	public synchronized ResultVO deleteMyMoments(@RequestParam Map<String, String> params) {
		return task_examine_impl.deleteMyMoments(params);
	}
	
	@Mylog(value=("点赞"))
	@PostMapping("/fabulous")
	public synchronized ResultVO fabulous(@RequestParam Map<String, String> params) {
		return task_examine_impl.fabulous(params);
	}
	
	@Mylog(value=("送花"))
	@PostMapping("/flowers")
	public synchronized ResultVO flowers(@RequestParam Map<String, String> params) throws ParseException {
		return task_examine_impl.flowers(params);
	}
	
	@PostMapping("/configPic")
	public ResultVO configPic(){
		return task_examine_impl.configPic();
	}
	
	@PostMapping("/allFabulousMsg")
	public synchronized ResultVO allFabulousMsg(@RequestParam Map<String, String> params) {
		return task_examine_impl.allFabulousMsg(params);
	}
}
