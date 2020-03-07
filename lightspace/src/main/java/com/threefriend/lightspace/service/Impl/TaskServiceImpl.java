package com.threefriend.lightspace.service.Impl;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.threefriend.lightspace.mapper.TaskMapper;
import com.threefriend.lightspace.repository.TaskRepository;
import com.threefriend.lightspace.service.TaskService;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ResultVO;

@Service
public class TaskServiceImpl implements TaskService{

	@Autowired
	private TaskRepository task_dao;

	/* 
	 * 任务列表
	 */
	@Override
	public ResultVO taskList() {
		return ResultVOUtil.success(task_dao.findAll());
	}
	/* 
	 * 新增任务
	 */
	@Override
	public ResultVO addTask(Map<String, String> params) {
		TaskMapper task = new TaskMapper();
		task.setTitle(params.get("title"));
		task.setContent(params.get("content"));
		task.setIntegral(Long.valueOf(params.get("integral")));
		task.setGenTime(new Date());
		task_dao.save(task);
		return ResultVOUtil.success(task_dao.findAll());
	}
	/* 
	 * 按照id查找
	 */
	@Override
	public ResultVO findById(Map<String, String> params) {
		return 	ResultVOUtil.success(task_dao.findById(Integer.valueOf(params.get("id"))).get());
	}
	/* 
	 * 修改后保存
	 */
	@Override
	public ResultVO saveTask(Map<String, String> params) {
		Integer id = Integer.valueOf(params.get("id"));
		TaskMapper task = task_dao.findById(id).get();
		if(!StringUtils.isEmpty(params.get("title")))task.setTitle(params.get("title"));
		if(!StringUtils.isEmpty(params.get("content")))task.setContent(params.get("content"));
		if(!StringUtils.isEmpty(params.get("integral")))task.setIntegral(Long.valueOf(params.get("integral")));
		task_dao.save(task);
		return ResultVOUtil.success(task_dao.findAll());
	}
	/* 
	 * 删除任务
	 */
	@Override
	public ResultVO deleteTask(Map<String, String> params) {
		Integer id = Integer.valueOf(params.get("id"));
		task_dao.deleteById(id);
		return ResultVOUtil.success(task_dao.findAll());
	}
	
	
}
