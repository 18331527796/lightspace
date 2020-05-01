package com.threefriend.lightspace.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.threefriend.lightspace.mapper.ClassesMapper;

/**
 * 班级操作层
 * @author Administrator
 *
 */
public interface ClassesRepository extends JpaRepository<ClassesMapper, Integer>{

	List<ClassesMapper> findAllByOrderByFinish();
	
	List<ClassesMapper> findBySchoolIdOrderByFinish(Integer sId);
	
	List<ClassesMapper> findBySchoolIdAndClassName(Integer sId,String name);
	
	List<ClassesMapper> findByClassNameLikeOrderByFinish(String name);
	
	@Transactional
	void deleteBySchoolId(Integer schoolId);
	
	int countBySchoolIdAndExperiment(Integer schoolId,Integer experiment);
	
	int countBySchoolId(Integer schoolId);
	@Query("select id from ClassesMapper where schoolId = ?1 and experiment = ?2")
	List<Integer> AllclassIdBySchoolIdAndExperiment(Integer schoolId,Integer experiment);
}
