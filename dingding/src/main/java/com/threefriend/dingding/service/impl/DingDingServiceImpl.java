package com.threefriend.dingding.service.impl;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.threefriend.constants.DingDingAccessToken;
import com.threefriend.dingding.dto.DeptDTO;
import com.threefriend.dingding.dto.UserDTO;
import com.threefriend.dingding.dto.UserTaskRecordDTO;
import com.threefriend.dingding.enums.ResultEnum;
import com.threefriend.dingding.mapper.TaskMapper;
import com.threefriend.dingding.mapper.TaskRecordMapper;
import com.threefriend.dingding.mapper.UserTaskRecordMapper;
import com.threefriend.dingding.repository.TaskRecordRepository;
import com.threefriend.dingding.repository.TaskRepository;
import com.threefriend.dingding.repository.UserTaskRecordrepository;
import com.threefriend.dingding.service.DingDingService;
import com.threefriend.dingding.util.DingDingUtils;
import com.threefriend.dingding.util.ResultVOUtil;
import com.threefriend.dingding.vo.ResultVO;

@Service
public class DingDingServiceImpl implements DingDingService{
	
	@Autowired
	private UserTaskRecordrepository u_t_dao;
	@Autowired
	private TaskRecordRepository record_dao;
	@Autowired
	private TaskRepository task_dao;

	@Override
	public ResultVO login(String code) {
		System.out.println(code);
		String token = DingDingAccessToken.getToken();
		JSONObject userInfo = DingDingUtils.getUserInfo(token,code);
    	System.out.println(userInfo.toJSONString());
    	UserDTO user = JSONObject.toJavaObject(userInfo, UserDTO.class);
		return ResultVOUtil.success(user);
	}

	@Override
	public ResultVO getDeptList() {
		List<DeptDTO> end = new ArrayList<DeptDTO>();
		String token = DingDingAccessToken.getToken();
    	JSONArray deptInfo = DingDingUtils.getDeptInfo(token);
    	for (int i=0; i<deptInfo.size(); i++) {
  		  JSONObject jsonObject = (JSONObject) deptInfo.get(i);
  		  DeptDTO dept = new DeptDTO(jsonObject.get("dept_id").toString(), jsonObject.get("name").toString());
  		  end.add(dept);
  		}
		return ResultVOUtil.success(end);
	}

	@Override
	public ResultVO getUserTaskList(Map<String, String> param) {
		Calendar calendar = Calendar.getInstance();
		try {
			System.out.println(param.get("date"));
			calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(param.get("date")));
		} catch (ParseException e) {
			System.err.println("时间解析出错  以当前时间返回信息");
			calendar.setTime(new Date());
		}
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date begin = calendar.getTime();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 30);
        Date end = calendar.getTime();
        
		List<UserTaskRecordMapper> record = u_t_dao.findByDeptIdAndTimeBetween(param.get("deptId"),begin,end);
		
		return ResultVOUtil.success(record);
	}

	@Override
	public ResultVO getUserRecord(Map<String, String> param) {
		List<UserTaskRecordDTO> endList = new ArrayList<>();
		Calendar calendar = Calendar.getInstance();
		try {
			System.out.println(param.get("date"));
			calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(param.get("date")));
		} catch (ParseException e) {
			System.err.println("时间解析出错  以当前时间返回信息");
			calendar.setTime(new Date());
		}
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date begin = calendar.getTime();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 30);
        Date end = calendar.getTime();
        
		List<TaskRecordMapper> record = record_dao.findByUserIdAndTimeBetween(param.get("userId"),begin,end);
		if(record.size()<1)return ResultVOUtil.error(ResultEnum.MARK_ERROR.getStatus(), ResultEnum.MARK_ERROR.getMessage());
		//Map<Integer, String> chakTask = new HashMap<>();
		//for (TaskRecordMapper taskRecordMapper : record) {
		//	chakTask.put(taskRecordMapper.getTaskId(), "1");
		//}
		
		//Integer level = task_dao.findById(record.get(0).getTaskId()).get().getLevel();
		//List<TaskMapper> tasks = task_dao.findByIsShowAndLevelOrderById(1, level);
		//for (TaskMapper taskMapper : tasks) {
		//	UserTaskRecordDTO dto = new UserTaskRecordDTO();
		//	dto.setContent(taskMapper.getContent());
		//	if(chakTask.containsKey(taskMapper.getId())) {
		//		dto.setIsSuccess("完成");
		//	}else {
		//		dto.setIsSuccess("未完成");
		//	}
		//	endList.add(dto);
		//}
		for (TaskRecordMapper po : record) {
			UserTaskRecordDTO dto = new UserTaskRecordDTO();
			dto.setContent(task_dao.findById(po.getTaskId()).get().getContent());
			dto.setIsSuccess("完成");
			endList.add(dto);
		}
		return ResultVOUtil.success(endList);
	}

	

	
}
