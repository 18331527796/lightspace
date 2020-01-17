package com.threefriend.lightspace.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threefriend.lightspace.mapper.ClassesMapper;

/**
 * 班级操作层
 * @author Administrator
 *
 */
public interface ClassesRepository extends JpaRepository<ClassesMapper, Integer>{

	List<ClassesMapper> findBySchoolId(Integer sId);
	
	List<ClassesMapper> findByClassNameLike(String name);
	
	void deleteBySchoolId(Integer schoolId);
	
}
