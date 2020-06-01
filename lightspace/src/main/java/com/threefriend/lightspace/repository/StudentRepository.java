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
	
	List<StudentMapper> findBySchoolId(Integer id);
	
	Page<StudentMapper> findBySchoolId(Integer id ,Pageable Pageable);
	
	Page<StudentMapper> findById(Integer id ,Pageable Pageable);
	
	List<StudentMapper> findByClassesId(Integer id);
	
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
	
	
	//学校 左眼 大于
		@Query(value="SELECT count(1)" + 
				" FROM student_mapper t1" + 
				" WHERE school_id = ?1 and vision_left_str > ?2",nativeQuery = true)
		int countTopBySchoolIdAndVisionLeftGreaterThanOrderByStudentId(Integer schoolId,Double min);
		//学校 左眼 区间
		@Query(value="SELECT count(1) FROM student_mapper t1" + 
				" WHERE school_id = ?1 and vision_left_str BETWEEN ?2 and ?3",nativeQuery = true)
		int countTopBySchoolIdAndVisionLeftBetweenOrderByStudentId(Integer schoolId,Double min,Double max);
		//学校 右眼 大于
		@Query(value="SELECT count(1)" + 
				" FROM student_mapper t1" + 
				" WHERE school_id = ?1 and vision_right_str > ?2",nativeQuery = true)
		int countTopBySchoolIdAndVisionRightGreaterThanOrderByStudentId(Integer schoolId,Double min);
		//学校 右眼区间
		@Query(value="SELECT count(1) FROM student_mapper t1" + 
				" WHERE school_id = ?1 and vision_right_str BETWEEN ?2 and ?3",nativeQuery = true)
		int countTopBySchoolIdAndVisionRightBetweenOrderByStudentId(Integer schoolId,Double min,Double max);
		//学校 双眼 区间
		@Query(value="SELECT count(1) FROM student_mapper t1" + 
				" WHERE school_id = ?1 and (vision_left_str + vision_right_str)/2 BETWEEN ?2 and ?3",nativeQuery = true)
		int schoolAvgVision(Integer school,Double min,Double max);
		//学校 双眼 大于
		@Query(value="SELECT count(1)" + 
				" FROM student_mapper t1" + 
				" WHERE school_id = ?1 and (vision_left_str + vision_right_str)/2 > ?2",nativeQuery = true)
		int schoolAvgVision(Integer school,Double min);
		
		@Query(value="SELECT count(1)" + 
				" FROM student_mapper t1" + 
				" WHERE school_id = ?1 and gender = ?2 and (vision_left_str + vision_right_str)/2 < ?3",nativeQuery = true)
		int schoolAvgVisionSmaller(Integer school,Integer gender ,Double min);
		
		
		@Query(value="SELECT count(1)" + 
				" FROM student_mapper t1" + 
				" WHERE classes_id IN ?1 and vision_left_str BETWEEN ?2 and ?3",nativeQuery = true)
		int countTopByClassesIdInAndVisionLeftBetweenOrderByStudentId(List<Integer> ClassId,Double min,Double max);
		
		@Query(value="SELECT count(1)" + 
				" FROM student_mapper t1" + 
				" WHERE classes_id IN ?1 and vision_left_str > ?2",nativeQuery = true)
		int countTopByClassesIdInAndVisionLeftGreaterThanOrderByStudentId(List<Integer> ClassId,Double min);
		
		@Query(value="SELECT count(1)" + 
				" FROM student_mapper t1" + 
				" WHERE classes_id IN ?1 and vision_right_str > ?2",nativeQuery = true)
		int countTopByClassesIdInAndVisionRightGreaterThanOrderByStudentId(List<Integer> ClassId,Double min);
		
		@Query(value="SELECT count(1)" + 
				" FROM student_mapper " + 
				" WHERE classes_id IN ?1 and vision_right_str BETWEEN ?2 and ?3",nativeQuery = true)
		int countTopByClassesIdInAndVisionRightBetweenOrderByStudentId(List<Integer> ClassId,Double min,Double max);
		@Query(value="SELECT count(1)" + 
				" FROM student_mapper " + 
				" WHERE classes_id IN ?1 and (vision_left_str + vision_right_str)/2 BETWEEN ?2 and ?3",nativeQuery = true)
		int classInAvgVision(List<Integer> ClassId,Double min,Double max);
		
		@Query(value="SELECT count(1)" + 
				" FROM student_mapper " + 
				" WHERE  classes_id IN ?1  and (vision_left_str + vision_right_str)/2 > ?2",nativeQuery = true)
		int classInAvgVision(List<Integer> ClassId,Double min);
		
		@Query(value="SELECT count(1)" + 
				" FROM student_mapper " + 
				" WHERE  classes_id IN ?1 and gender = ?2 and (vision_left_str + vision_right_str)/2 < ?3",nativeQuery = true)
		int classInAvgVisionSmaller(List<Integer> ClassId,Integer gender,Double min);
		
		
}
