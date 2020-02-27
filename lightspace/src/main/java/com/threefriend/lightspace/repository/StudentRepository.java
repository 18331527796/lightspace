package com.threefriend.lightspace.repository;

import java.util.List;

import javax.transaction.Transactional;

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
	
	List<StudentMapper> findBySchoolId(Integer id);
	
	List<StudentMapper> findByClassesId(Integer id);
	
	List<StudentMapper> findByClassesIdOrderBySittingHeight(Integer classesid);
	
	List<StudentMapper> findBySchoolIdAndClassesIdAndNameLike(Integer schoolid,Integer classesid,String name);
	
	StudentMapper findBySchoolIdAndClassesIdAndName(Integer schoolid,Integer classesid,String name);
	@Transactional
	void deleteByClassesId(List<Integer> ids);
	@Transactional
	void deleteByClassesId(Integer id);
	
	StudentMapper findByNameAndParentPhone(String name,String parentPhone);
	
	int countByClassesIdAndCorrect(Integer classId,Integer correct);
	
	int countByClassesIdIn(List<Integer> classId);
	int countByClassesId(Integer classId);
	
	int countBySchoolId(Integer school);
}
