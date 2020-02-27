package com.threefriend.lightspace.repository;


import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.w3c.dom.ls.LSInput;

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
	@Transactional
	void deleteBySchoolId(Integer sId);
	@Transactional
	void deleteByClassesId(Integer cId);
	@Transactional
	void deleteByStudentId(Integer Id);
	
	RecordMapper findTopByStudentIdOrderByGenTime(Integer id);
	//按照学生id查询所有的数据
	List<RecordMapper> findAllByStudentIdAndGenTimeBetween(Integer id,Date befor , Date after);
	//学校 左眼 大于
	@Query(value="SELECT count(1)" + 
			" FROM record_mapper t1" + 
			" LEFT JOIN record_mapper t2 ON t1.student_id = t2.student_id AND t1.gen_time < t2.gen_time " + 
			" WHERE t2.id IS NULL AND t1.school_id = ?1 and t1.vision_left > ?2",nativeQuery = true)
	int countTopBySchoolIdAndVisionLeftGreaterThanOrderByStudentId(Integer schoolId,Double min);
	//学校 左眼 区间
	@Query(value="SELECT count(1) FROM record_mapper t1" + 
			" LEFT JOIN record_mapper t2 ON t1.student_id = t2.student_id AND t1.gen_time < t2.gen_time" + 
			" WHERE t2.id IS NULL AND t1.school_id = ?1 and t1.vision_left BETWEEN ?2 and ?3",nativeQuery = true)
	int countTopBySchoolIdAndVisionLeftBetweenOrderByStudentId(Integer schoolId,Double min,Double max);
	//学校 右眼 大于
	@Query(value="SELECT count(1)" + 
			" FROM record_mapper t1" + 
			" LEFT JOIN record_mapper t2 ON t1.student_id = t2.student_id AND t1.gen_time < t2.gen_time " + 
			" WHERE t2.id IS NULL AND t1.school_id = ?1 and t1.vision_right > ?2",nativeQuery = true)
	int countTopBySchoolIdAndVisionRightGreaterThanOrderByStudentId(Integer schoolId,Double min);
	//学校 右眼区间
	@Query(value="SELECT count(1) FROM record_mapper t1" + 
			" LEFT JOIN record_mapper t2 ON t1.student_id = t2.student_id AND t1.gen_time < t2.gen_time" + 
			" WHERE t2.id IS NULL AND t1.school_id = ?1 and t1.vision_right BETWEEN ?2 and ?3",nativeQuery = true)
	int countTopBySchoolIdAndVisionRightBetweenOrderByStudentId(Integer schoolId,Double min,Double max);
	//学校 双眼 区间
	@Query(value="SELECT count(1) FROM record_mapper t1" + 
			" LEFT JOIN record_mapper t2 ON t1.student_id = t2.student_id AND t1.gen_time < t2.gen_time" + 
			" WHERE t2.id IS NULL AND t1.school_id = ?1 and (t1.vision_left + t1.vision_right)/2 BETWEEN ?2 and ?3",nativeQuery = true)
	int schoolAvgVision(Integer school,Double min,Double max);
	//学校 双眼 大于
	@Query(value="SELECT count(1)" + 
			" FROM record_mapper t1" + 
			" LEFT JOIN record_mapper t2 ON t1.student_id = t2.student_id AND t1.gen_time < t2.gen_time " + 
			" WHERE t2.id IS NULL AND t1.school_id = ?1 and (t1.vision_left + t1.vision_right)/2 > ?2",nativeQuery = true)
	int schoolAvgVision(Integer school,Double min);
	
	@Query(value="SELECT count(1)" + 
			" FROM record_mapper t1" + 
			" LEFT JOIN record_mapper t2 ON t1.student_id = t2.student_id AND t1.gen_time < t2.gen_time " + 
			" WHERE t2.id IS NULL AND t1.classes_id IN ?1 and t1.vision_left BETWEEN ?2 and ?3",nativeQuery = true)
	int countTopByClassesIdInAndVisionLeftBetweenOrderByStudentId(List<Integer> ClassId,Double min,Double max);
	
	@Query(value="SELECT count(1)" + 
			" FROM record_mapper t1" + 
			" LEFT JOIN record_mapper t2 ON t1.student_id = t2.student_id AND t1.gen_time < t2.gen_time " + 
			" WHERE t2.id IS NULL AND t1.classes_id IN ?1 and t1.vision_left > ?2",nativeQuery = true)
	int countTopByClassesIdInAndVisionLeftGreaterThanOrderByStudentId(List<Integer> ClassId,Double min);
	
	@Query(value="SELECT count(1)" + 
			" FROM record_mapper t1" + 
			" LEFT JOIN record_mapper t2 ON t1.student_id = t2.student_id AND t1.gen_time < t2.gen_time " + 
			" WHERE t2.id IS NULL AND t1.classes_id IN ?1 and t1.vision_right > ?2",nativeQuery = true)
	int countTopByClassesIdInAndVisionRightGreaterThanOrderByStudentId(List<Integer> ClassId,Double min);
	
	@Query(value="SELECT count(1)" + 
			" FROM record_mapper t1" + 
			" LEFT JOIN record_mapper t2 ON t1.student_id = t2.student_id AND t1.gen_time < t2.gen_time " + 
			" WHERE t2.id IS NULL AND t1.classes_id IN ?1 and t1.vision_right BETWEEN ?2 and ?3",nativeQuery = true)
	int countTopByClassesIdInAndVisionRightBetweenOrderByStudentId(List<Integer> ClassId,Double min,Double max);
	@Query(value="SELECT count(1)" + 
			" FROM record_mapper t1" + 
			" LEFT JOIN record_mapper t2 ON t1.student_id = t2.student_id AND t1.gen_time < t2.gen_time " + 
			" WHERE t2.id IS NULL AND t1.classes_id IN ?1 and (t1.vision_left + t1.vision_right)/2 BETWEEN ?2 and ?3",nativeQuery = true)
	int classInAvgVision(List<Integer> ClassId,Double min,Double max);
	
	@Query(value="SELECT count(1)" + 
			" FROM record_mapper t1" + 
			" LEFT JOIN record_mapper t2 ON t1.student_id = t2.student_id AND t1.gen_time < t2.gen_time " + 
			" WHERE t2.id IS NULL AND t1.classes_id IN ?1 and (t1.vision_left + t1.vision_right)/2 > ?2",nativeQuery = true)
	int classInAvgVision(List<Integer> ClassId,Double min);
}
