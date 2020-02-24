package com.threefriend.lightspace.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threefriend.lightspace.mapper.ClassesMapper;

/**
 * 班级操作层
 * @author Administrator
 *
 */
public interface ClassesRepository extends JpaRepository<ClassesMapper, Integer>{

	List<ClassesMapper> findBySchoolId(Integer sId);
	
	List<ClassesMapper> findBySchoolIdAndClassName(Integer sId,String name);
	
	List<ClassesMapper> findByClassNameLike(String name);
	
	ClassesMapper findByClassName(String name);
	
	@Transactional
	void deleteBySchoolId(Integer schoolId);
	
}
