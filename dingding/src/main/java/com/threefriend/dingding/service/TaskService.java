package com.threefriend.dingding.service;

import com.threefriend.dingding.dto.TaskDTO;
import com.threefriend.dingding.dto.TaskRecordDTO;
import com.threefriend.dingding.mapper.TaskMapper;
import com.threefriend.dingding.vo.ResultVO;

public interface TaskService {

	ResultVO addTask(TaskMapper vo);
	
	ResultVO deleteTask(TaskMapper vo);
	
	ResultVO editTask(TaskMapper vo);
	
	ResultVO saveTask(TaskMapper vo);
	
	ResultVO taskList(TaskDTO vo);
	
	ResultVO taskList(TaskDTO vo,String userId)throws Exception ;

	ResultVO clockIn(TaskRecordDTO dto);
	
}
