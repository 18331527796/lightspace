package com.threefriend.lightspace.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.threefriend.lightspace.mapper.StudentMapper;

/**
 * 学生操作层
 * @author Administrator
 *
 */
public interface StudentRepository extends JpaRepository<StudentMapper, Integer>{

	List<StudentMapper> findBySchoolIdAndClassesId(Integer schoolid,Integer classesid);
	
	List<StudentMapper> findByNameLike(String name);
	
	List<StudentMapper> findByNameAndBirthday(String name,String birthday);
	
	List<StudentMapper> findBySchoolId(Integer id);
	
	List<StudentMapper> findBySchoolIdIn(List<Integer> ids);
	
	Page<StudentMapper> findByRegionId(Integer regionId ,Pageable Pageable);
	
	List<Integer> findIdByRegionId(Integer id);
	
	List<StudentMapper> findByRegionId(Integer id);
	
	List<Integer> findIdBySchoolId(Integer id);
	
	Page<StudentMapper> findBySchoolId(Integer id ,Pageable Pageable);
	
	Page<StudentMapper> findBySchoolIdAndNameLike(Integer id ,String name ,Pageable Pageable);
	
	Page<StudentMapper> findById(Integer id ,Pageable Pageable);
	
	List<StudentMapper> findByClassesId(Integer id);
	
	List<StudentMapper> findByClassesIdOrderByDiopterLeftDesc(Integer id);
	
	List<StudentMapper> findBySchoolIdOrderByDiopterLeftDesc(Integer id);
	
	StudentMapper findByClassesIdAndName(Integer id,String name);
	
	Page<StudentMapper> findByClassesId(Integer id ,Pageable Pageable);
	
	@Query(value="SELECT * FROM student_mapper  WHERE classes_id in ?1 ",nativeQuery = true)
	List<StudentMapper> findByClassesId(List<Integer> ids);
	
	List<StudentMapper> findByClassesIdOrderBySittingHeight(Integer classesid);
	
	List<StudentMapper> findBySchoolIdAndClassesIdAndNameLike(Integer schoolid,Integer classesid,String name);
	
	StudentMapper findBySchoolIdAndClassesIdAndName(Integer schoolid,Integer classesid,String name);
	@Transactional
	void deleteByClassesId(List<Integer> ids);
	@Transactional
	void deleteByClassesId(Integer id);
	
	StudentMapper findBySchoolNameAndClassesNameAndName(String schoolName,String className,String name);
	
	int countByClassesIdAndCorrect(Integer classId,Integer correct);
	@Query(value="SELECT count(1) FROM student_mapper  WHERE classes_id in ?1 ",nativeQuery = true)
	int countByClassesId(List<Integer> classId);
	
	int countByClassesId(Integer classId);
	
	int countBySchoolId(Integer school);
	
	int countBySchoolIdIn(List<Integer> schoolIds);
	
		
}
