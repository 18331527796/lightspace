package com.threefriend.lightspace.xcx.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.threefriend.lightspace.aspect.Mylog;
import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.xcx.service.Impl.TaskXcxServiceImpl;

/**
 *	打卡任务控制器
 */
@RestController
@RequestMapping("/xcx")
public class TaskXcxController {

	@Autowired
	private TaskXcxServiceImpl task_impl;
	
	@Mylog(value=("打卡列表"))
	@PostMapping("/xcxTaskList")
	public ResultVO xcxTaskList(@RequestParam Map<String, String> params) throws Exception {
		return task_impl.xcxTaskList(params);
	}
	
	
	@PostMapping("/completeTask")
	public ResultVO completeTask(@RequestParam Map<String, String> params) throws Exception  {
		return task_impl.completeTask(params);
	}
	
}
