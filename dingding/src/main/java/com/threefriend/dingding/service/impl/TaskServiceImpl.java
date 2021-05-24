package com.threefriend.dingding.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.threefriend.constants.DingDingAccessToken;
import com.threefriend.dingding.dto.TaskDTO;
import com.threefriend.dingding.dto.TaskRecordDTO;
import com.threefriend.dingding.mapper.RemarksMapper;
import com.threefriend.dingding.mapper.TaskMapper;
import com.threefriend.dingding.mapper.TaskRecordMapper;
import com.threefriend.dingding.repository.RemarksRepository;
import com.threefriend.dingding.repository.TaskRecordRepository;
import com.threefriend.dingding.repository.TaskRepository;
import com.threefriend.dingding.service.TaskService;
import com.threefriend.dingding.util.DingDingUtils;
import com.threefriend.dingding.util.MyBeanUtils;
import com.threefriend.dingding.util.ResultVOUtil;
import com.threefriend.dingding.vo.ResultVO;

@Service
public class TaskServiceImpl implements TaskService{
	
	@Autowired
	private TaskRepository task_dao;
	@Autowired
	private TaskRecordRepository record_dao;
	@Autowired
	private RemarksRepository remark_dao;

	
	public Map<String, Date> beginAndEnd() throws Exception {
		Map<String, Date> map = new HashMap<String, Date>();
		DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date begin = simpleDateFormat.parse(DateFormatUtils.format(new Date(), "yyyy-MM-dd 00:00:00"));
		Date end = simpleDateFormat.parse(DateFormatUtils.format(new Date(), "yyyy-MM-dd 23:59:59"));
		map.put("begin", begin);
		map.put("end", end);
		return map;
	}
	
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
	public ResultVO taskList(TaskDTO vo){
		List<TaskMapper> allList = task_dao.findByIsShowAndLevelOrderById(1,Integer.valueOf(vo.getLevel()));
		return ResultVOUtil.success(allList);
	}

	@Override
	public ResultVO taskList(TaskDTO vo,String userId) throws Exception {
		Map<String, Object> endMap = new HashMap<>();
		List<TaskMapper> allList = null;
		List<TaskDTO> endList = new ArrayList<>(); 
		Integer isSuccess = 2;
		Map<String, Date> beginAndEnd = beginAndEnd();
		String token = DingDingAccessToken.getToken();
		String leader = DingDingUtils.isLeader(token, userId);
		if(vo.getLevel()==100) {
			allList = task_dao.findByIsShowOrderById(1);
		}else if(leader.equals("true")){
			allList = task_dao.findByIsShowAndLevelOrderById(1,1);
		}else {
			allList = task_dao.findByIsShowAndLevelOrderById(1,2);
		}
		for (TaskMapper po : allList) {
			TaskRecordMapper record = record_dao.findByUserIdAndTaskIdAndTimeBetween(userId, po.getId(), beginAndEnd.get("begin"),beginAndEnd.get("end"));
			if(record==null) {
				isSuccess = 2;
			}else {
				isSuccess = 1;
			}
			TaskDTO dto = new TaskDTO();
			MyBeanUtils.copyProperties(po, dto);
			dto.setIsSuccess(isSuccess);
			endList.add(dto);
		}
		RemarksMapper remark = remark_dao.findByUserIdAndTimeBetween(userId, beginAndEnd.get("begin"),beginAndEnd.get("end"));
		endMap.put("taskList", endList);
		if(remark==null) {
			endMap.put("remark", null);
		}else {
			endMap.put("remark", remark);
		}
		return ResultVOUtil.success(endMap);
	}
	
	@Override
	public ResultVO clockIn(TaskRecordDTO dto) {
		TaskRecordMapper po = new TaskRecordMapper();
		MyBeanUtils.copyProperties(dto, po);
		po.setTime(new Date());
		record_dao.save(po);
		return ResultVOUtil.success();
	}

}
