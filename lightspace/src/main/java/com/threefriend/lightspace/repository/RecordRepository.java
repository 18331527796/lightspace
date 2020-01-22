package com.threefriend.lightspace.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threefriend.lightspace.mapper.RecordMapper;

/**
 * 基础数据操作层
 * @author Administrator
 *
 */
public interface RecordRepository extends JpaRepository<RecordMapper, Integer>{
	
	List<RecordMapper> findAllByStudentNameLike(String name);
	
	List<RecordMapper> findByClassesId(Integer classId);
	
	List<RecordMapper> findBySchoolId(Integer schoolId);
}
