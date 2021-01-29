package com.threefriend.dingding.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.threefriend.dingding.dto.TaskDTO;
import com.threefriend.dingding.dto.TaskRecordDTO;
import com.threefriend.dingding.mapper.TaskMapper;
import com.threefriend.dingding.service.impl.TaskServiceImpl;
import com.threefriend.dingding.util.ResultVOUtil;
import com.threefriend.dingding.vo.ResultVO;

@RestController
@RequestMapping("/task")
public class TaskController {

	@Autowired
	private TaskServiceImpl impl;
	
	@PostMapping("/addTask")
	public ResultVO addTask(@Valid TaskMapper vo) {
		return impl.addTask(vo);
	}
	
	@PostMapping("/editTask")
	public ResultVO editTask(@Valid TaskMapper vo) {
		return impl.editTask(vo);
	}
	
	@PostMapping("/saveTask")
	public ResultVO saveTask(@Valid TaskMapper vo) {
		return impl.saveTask(vo);
	}
	
	@PostMapping("/deleteTask")
	public ResultVO deleteTask(@Valid TaskMapper vo) {
		return impl.deleteTask(vo);
	}
	
	@PostMapping("/taskList")
	public ResultVO taskList(@Valid TaskDTO vo,@RequestParam(value="userId") String userId) throws Exception {
		return impl.taskList(vo,userId);
	}
	
	@PostMapping("/superviseTaskList")
	public ResultVO taskList(@Valid TaskDTO vo){
		return impl.taskList(vo);
	}
	
	@PostMapping("/clockIn")
	public ResultVO clockIn(@Valid TaskRecordDTO dto) {
		return ResultVOUtil.success(impl.clockIn(dto));
	}
	
}
