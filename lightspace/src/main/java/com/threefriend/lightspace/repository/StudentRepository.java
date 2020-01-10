package com.threefriend.lightspace.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threefriend.lightspace.mapper.StudentMapper;

/**
 * 班级操作层
 * @author Administrator
 *
 */
public interface StudentRepository extends JpaRepository<StudentMapper, Integer>{

}
