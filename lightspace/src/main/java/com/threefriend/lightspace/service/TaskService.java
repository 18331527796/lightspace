package com.threefriend.lightspace.service;

import java.util.Map;

import com.threefriend.lightspace.vo.ResultVO;

/**
 * 任务业务
 */
public interface TaskService {
	
	//任务列表
	ResultVO taskList();
	//新增任务
	ResultVO addTask(Map<String, String> params);
	//按照id查找
	ResultVO findById(Map<String, String> params);
	//修改后保存
	ResultVO saveTask(Map<String, String> params);
	//删除任务
	ResultVO deleteTask(Map<String, String> params);
}
