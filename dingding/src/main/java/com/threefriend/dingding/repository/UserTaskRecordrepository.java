package com.threefriend.dingding.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threefriend.dingding.mapper.UserTaskRecordMapper;

public interface UserTaskRecordrepository extends JpaRepository<UserTaskRecordMapper, Integer>{

	List<UserTaskRecordMapper> findByDeptIdAndTimeBetween(String dept_id,Date begin , Date end);
}
