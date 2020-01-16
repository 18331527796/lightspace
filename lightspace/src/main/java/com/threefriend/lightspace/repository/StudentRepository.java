package com.threefriend.lightspace.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threefriend.lightspace.mapper.StudentMapper;

/**
 * 班级操作层
 * @author Administrator
 *
 */
public interface StudentRepository extends JpaRepository<StudentMapper, Integer>{

	List<StudentMapper> findBySchoolIdAndClassesId(Integer schoolid,Integer classesid);
	
	List<StudentMapper> findByNameLike(String name);
}
