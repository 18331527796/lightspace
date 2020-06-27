package com.threefriend.lightspace.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threefriend.lightspace.mapper.TaskExamineConfigMapper;


public interface TaskExamineConfigRepository extends JpaRepository<TaskExamineConfigMapper, Integer>{

	TaskExamineConfigMapper findByStatus(Integer status);
}
