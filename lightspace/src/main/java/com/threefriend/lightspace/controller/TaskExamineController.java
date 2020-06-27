package com.threefriend.lightspace.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.threefriend.lightspace.service.Impl.TaskExamineServiceImpl;
import com.threefriend.lightspace.vo.ResultVO;

@RestController
public class TaskExamineController {

	@Autowired
	private TaskExamineServiceImpl task_examine_impl;
	
	@PostMapping("/taskExamineList")
	public ResultVO taskExamineList(@RequestParam Map<String, String> params) {
		return task_examine_impl.taskExamineList(params);
	}
	
	@PostMapping("/deleteTaskExamine")
	public ResultVO deleteTaskExamine(@RequestParam Map<String, String> params) {
		return task_examine_impl.deleteTaskExamine(params);
	}
	
	@PostMapping("/examineTask")
	public ResultVO examineTask(@RequestParam Map<String, String> params) {
		return task_examine_impl.examineTask(params);
	}
	
	@PostMapping("/momentsConfigList")
	public ResultVO momentsConfigList(@RequestParam Map<String, String> params) {
		return task_examine_impl.momentsConfigList(params);
	}
	
	@PostMapping("/addMomentsConfig")
	public ResultVO addMomentsConfig(@RequestParam Map<String, String> params,
									 @RequestParam(value="file",required = false)MultipartFile file) {
		return task_examine_impl.addMomentsConfig(params,file);
	}
	
	@PostMapping("/deleteMomentsConfig")
	public ResultVO deleteMomentsConfig(@RequestParam Map<String, String> params) {
		return task_examine_impl.deleteMomentsConfig(params);
	}
	
	@PostMapping("/changeMomentsConfigDisplay")
	public ResultVO changeMomentsConfigDisplay(@RequestParam Map<String, String> params) {
		return task_examine_impl.changeMomentsConfigDisplay(params);
	}
}
