package com.threefriend.dingding.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threefriend.dingding.mapper.TaskMapper;

public interface TaskRepository extends JpaRepository<TaskMapper, Integer >{

	List<TaskMapper> findByIsShowAndLevelOrderById(Integer isShow,Integer level);
	
	List<TaskMapper> findByIsShowOrderById(Integer isShow);
}
