package com.threefriend.lightspace.repository;



import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.threefriend.lightspace.mapper.TaskExamineMapper;
import com.threefriend.lightspace.mapper.xcx.TaskRecordMapper;


public interface TaskExamineRepository extends JpaRepository<TaskExamineMapper, Integer>{

	Page<TaskExamineMapper> findByExamineStatus(Integer status,Pageable page);
	
	Page<TaskExamineMapper> findByExamineStatusNot(Integer status,Pageable page);
	
	Page<TaskExamineMapper> findByParentId(Integer parentId,Pageable page);
	
	TaskExamineMapper findByStudentIdAndGenTimeBetween(Integer studentId,Date begin,Date end);
}
