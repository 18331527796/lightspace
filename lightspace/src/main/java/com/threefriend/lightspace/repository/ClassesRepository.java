package com.threefriend.lightspace.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.threefriend.lightspace.mapper.ClassesMapper;

/**
 * 班级操作层
 * @author Administrator
 *
 */
public interface ClassesRepository extends JpaRepository<ClassesMapper, Integer>{

	
	List<ClassesMapper> findBySchoolIdOrderByFinish(Integer sId);
	
	List<ClassesMapper> findBySchoolIdAndClassName(Integer sId,String name);
	
	Page<ClassesMapper> findByClassNameLikeOrderByFinish(String name,Pageable page);
	
	Page<ClassesMapper> findById(Integer id,Pageable page);
	
	Page<ClassesMapper> findBySchoolId(Integer id,Pageable page);
	
	@Transactional
	void deleteBySchoolId(Integer schoolId);
	
	int countBySchoolIdAndExperiment(Integer schoolId,Integer experiment);
	
	int countBySchoolId(Integer schoolId);
	@Query("select id from ClassesMapper where schoolId = ?1 and grade = ?2")
	List<Integer> findIdBySchoolIdAndGrade(Integer schoolId,Integer grade);
	@Query("select id from ClassesMapper where schoolId = ?1 and experiment = ?2")
	List<Integer> AllclassIdBySchoolIdAndExperiment(Integer schoolId,Integer experiment);
}
