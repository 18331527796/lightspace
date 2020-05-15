package com.threefriend.lightspace.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threefriend.lightspace.mapper.xcx.TaskMapper;
import com.threefriend.lightspace.mapper.xcx.TaskRecordMapper;

/**
 * 任务记录层
 * @author Administrator
 *
 */
public interface TaskRecordRepository extends JpaRepository<TaskRecordMapper, Integer>{

	List<TaskRecordMapper> findByStudentIdAndGenTimeBetween(Integer studentId,Date begin,Date end);
	
	TaskRecordMapper findByStudentIdAndTaskId(Integer studentId,Integer taskId);
	
	int countByStudentIdAndGenTimeBetween(Integer studentId,Date begin,Date end);
	
	List<TaskRecordMapper> findByParentIdAndGenTimeBetween(Integer studentId,Date begin,Date end);
	
	TaskRecordMapper findByParentIdAndTaskId(Integer studentId,Integer taskId);
}
