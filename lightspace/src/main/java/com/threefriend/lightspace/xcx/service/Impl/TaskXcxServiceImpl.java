package com.threefriend.lightspace.xcx.service.Impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.threefriend.lightspace.enums.ResultEnum;
import com.threefriend.lightspace.mapper.xcx.IntegralMapper;
import com.threefriend.lightspace.mapper.xcx.ParentMapper;
import com.threefriend.lightspace.mapper.xcx.ParentStudentRelation;
import com.threefriend.lightspace.mapper.xcx.TaskMapper;
import com.threefriend.lightspace.mapper.xcx.TaskRecordMapper;
import com.threefriend.lightspace.repository.IntegralRepository;
import com.threefriend.lightspace.repository.ParentRepository;
import com.threefriend.lightspace.repository.ParentStudentRepository;
import com.threefriend.lightspace.repository.TaskRecordRepository;
import com.threefriend.lightspace.repository.TaskRepository;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.vo.TaskVO;
import com.threefriend.lightspace.xcx.service.TaskXcxService;

@Service
public class TaskXcxServiceImpl implements TaskXcxService{
	@Autowired
	private TaskRecordRepository taskrecord_dao;
	@Autowired
	private TaskRepository task_dao;
	@Autowired
	private ParentRepository parent_dao;
	@Autowired
	private IntegralRepository integral_dao;
	@Autowired
	private ParentStudentRepository p_s_dao;

	@Override
	public ResultVO xcxTaskList(Map<String, String> params) throws Exception {
		if(StringUtils.isEmpty(params.get("studentId")))return ResultVOUtil.error(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage());
		Map<String, Date> map = beginAndEnd();
		List<TaskVO> endList = new ArrayList<>();
		Integer studentId=Integer.valueOf(params.get("studentId")); 
		List<TaskRecordMapper> allRecords = taskrecord_dao.findByStudentIdAndGenTimeBetween(studentId, map.get("begin"), map.get("end"));
		List<TaskMapper> allTask = task_dao.findAll();
		for (TaskMapper task : allTask) {
			TaskVO vo = new TaskVO();
			BeanUtils.copyProperties(task, vo);
			vo.setSuccess(0);
			for (TaskRecordMapper record : allRecords) {
				if(record.getTaskId()==task.getId()) {
					vo.setSuccess(1);
				}
			}
			endList.add(vo);
		}
		return ResultVOUtil.success(endList);
	}

	@Override
	public ResultVO completeTask(Map<String, String> params) throws Exception {
		if(StringUtils.isEmpty(params.get("studentId")))return ResultVOUtil.error(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage());
		Map<String, Date> map = beginAndEnd();
		ParentMapper parent = parent_dao.findByOpenId(params.get("openId"));
		Integer parentId = parent.getId();
		Integer studentId=Integer.valueOf(params.get("studentId")); 
		Integer taskId = Integer.valueOf(params.get("taskId"));
		TaskRecordMapper record = taskrecord_dao.findByStudentIdAndTaskId(studentId, taskId);
		if(record!=null) {
			record.setGenTime(new Date());
			taskrecord_dao.save(record);
		}else {
			TaskRecordMapper po = new TaskRecordMapper();
			po.setParentId(parentId);
			po.setStudentId(studentId);
			po.setTaskId(taskId);
			po.setGenTime(new Date());
			taskrecord_dao.save(po);
		}
		List<TaskRecordMapper> allRecords = taskrecord_dao.findByStudentIdAndGenTimeBetween(studentId, map.get("begin"), map.get("end"));
		long count = task_dao.count();
		if(count==allRecords.size()) {
			IntegralMapper integral = new IntegralMapper();
			integral.setIntegral(2l);
			integral.setDetailed("完成每日任务");
			integral.setStudentId(studentId);
			integral.setState(1);
			integral.setGenTime(new Date());
			integral_dao.save(integral);
			return ResultVOUtil.error(ResultEnum.TASK_SUCCESS.getStatus(), ResultEnum.TASK_SUCCESS.getMessage());
		}
		return ResultVOUtil.success();
	}

	@Override
	public Map<String, Date> beginAndEnd() throws Exception {
		Map<String, Date> map = new HashMap<String, Date>();
		DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date begin = simpleDateFormat.parse(DateFormatUtils.format(new Date(), "yyyy-MM-dd 00:00:00"));
		Date end = simpleDateFormat.parse(DateFormatUtils.format(new Date(), "yyyy-MM-dd 23:59:59"));
		map.put("begin", begin);
		map.put("end", end);
		return map;
	}

}
