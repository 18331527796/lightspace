package com.threefriend.lightspace.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threefriend.lightspace.xcx.mapper.TaskMapper;

/**
 * 任务层
 * @author Administrator
 *
 */
public interface TaskRepository extends JpaRepository<TaskMapper, Integer>{

}
