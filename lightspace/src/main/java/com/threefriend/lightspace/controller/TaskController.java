package com.threefriend.lightspace.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.threefriend.lightspace.service.Impl.TaskServiceImpl;
import com.threefriend.lightspace.vo.ResultVO;

/**
 * 打卡任务控制器
 * @author Administrator
 *
 */
@RestController
public class TaskController {

	@Autowired
	private TaskServiceImpl task_impl;
	
	/**
	 * 任务列表
	 * @param params
	 * @return
	 */
	
	@PostMapping("/taskList")
	public ResultVO taskList() {
		return task_impl.taskList();
	}
	
	/**
	 * 新增任务
	 * @param params
	 * @return
	 */
	
	@PostMapping("/addTask")
	public ResultVO addTask(@RequestParam Map<String, String> params) {
		return task_impl.addTask(params);
	}
	
	/**
	 * 修改任务
	 * @param params
	 * @return
	 */
	
	@PostMapping("/editTask")
	public ResultVO editTask(@RequestParam Map<String, String> params) {
		return task_impl.findById(params);
	}
	
	/**
	 * 修改后保存
	 * @param params
	 * @return
	 */
	
	@PostMapping("/saveTask")
	public ResultVO saveTask(@RequestParam Map<String, String> params) {
		return task_impl.saveTask(params);
	}
	
	/**
	 * 删除任务
	 * @param params
	 * @return
	 */
	
	@PostMapping("/deleteTask")
	public ResultVO deleteTask(@RequestParam Map<String, String> params) {
		return task_impl.deleteTask(params);
	}
}
