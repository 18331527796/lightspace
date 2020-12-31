package com.threefriend.dingding.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.threefriend.dingding.mapper.TaskMapper;
import com.threefriend.dingding.repository.TaskRepository;
import com.threefriend.dingding.service.TaskService;
import com.threefriend.dingding.util.MyBeanUtils;
import com.threefriend.dingding.util.ResultVOUtil;
import com.threefriend.dingding.vo.ResultVO;

@Service
public class TaskServiceImpl implements TaskService{
	
	@Autowired
	private TaskRepository task_dao;

	@Override
	public ResultVO addTask(TaskMapper vo) {
		TaskMapper po = new TaskMapper();
		MyBeanUtils.copyProperties(vo, po);
		task_dao.save(po);
		return ResultVOUtil.success();
	}

	@Override
	public ResultVO deleteTask(TaskMapper vo) {
		TaskMapper po = task_dao.findById(vo.getId()).get();
		po.setIsShow(2);
		task_dao.save(po);
		return ResultVOUtil.success();
	}

	@Override
	public ResultVO editTask(TaskMapper vo) {
		TaskMapper po = task_dao.findById(vo.getId()).get();
		return ResultVOUtil.success(po);
	}

	@Override
	public ResultVO saveTask(TaskMapper vo) {
		TaskMapper po = task_dao.findById(vo.getId()).get();
		MyBeanUtils.copyProperties(vo, po);
		task_dao.save(po);
		return ResultVOUtil.success();
	}

	@Override
	public ResultVO taskList(TaskMapper vo) {
		List<TaskMapper> allList = null;
		if(StringUtils.isEmpty(vo.getLevel())) {
			allList = task_dao.findByIsShowOrderById(1);
		}else {
			allList = task_dao.findByIsShowAndLevelOrderById(1,vo.getLevel());
		}
		return ResultVOUtil.success(allList);
	}

}
