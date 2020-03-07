package com.threefriend.lightspace.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threefriend.lightspace.mapper.TaskMapper;
import com.threefriend.lightspace.mapper.TaskRecordMapper;

/**
 * 任务记录层
 * @author Administrator
 *
 */
public interface TaskRecordRepository extends JpaRepository<TaskRecordMapper, Integer>{

	List<TaskRecordMapper> findByParentIdAndGenTimeBetween(Integer parentId,Date begin,Date end);
	
	TaskRecordMapper findByParentIdAndTaskId(Integer parentId,Integer taskId);
}
