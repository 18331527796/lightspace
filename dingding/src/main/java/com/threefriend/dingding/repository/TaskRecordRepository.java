package com.threefriend.dingding.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threefriend.dingding.mapper.TaskRecordMapper;

public interface TaskRecordRepository extends JpaRepository<TaskRecordMapper, Long>{

	TaskRecordMapper findByUserIdAndTaskIdAndTimeBetween(String userId , Integer taskId, Date start,Date end);
	
	List<TaskRecordMapper> findByUserIdAndTimeBetween(String userId, Date start,Date end);
	
	int countByUserIdAndTimeBetween(String userId, Date start,Date end);
}
