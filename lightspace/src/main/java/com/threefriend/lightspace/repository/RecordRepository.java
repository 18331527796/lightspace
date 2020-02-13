package com.threefriend.lightspace.repository;


import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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
	//学校 左眼 区间
	int countBySchoolIdAndVisionLeftBetweenOrderByStudentId(Integer schoolId,Double min,Double max);
	//学校 左眼 大于
	int countBySchoolIdAndVisionLeftGreaterThanOrderByStudentId(Integer schoolId,Double min);
	//学校 右眼 大于
	int countBySchoolIdAndVisionRightGreaterThanOrderByStudentId(Integer schoolId,Double min);
	//学校 右眼区间
	int countBySchoolIdAndVisionRightBetweenOrderByStudentId(Integer schoolId,Double min,Double max);
	//学校 双眼 区间
	@Query("select count(1) from RecordMapper where schoolId = ?1 and (visionLeft + visionRight)/2 BETWEEN ?2 and ?3 ORDER BY studentId")
	int schoolAvgVision(Integer school,Double min,Double max);
	//学校 双眼 大于
	@Query("select count(1) from RecordMapper where schoolId = ?1 and (visionLeft + visionRight)/2 > ?2 ORDER BY studentId")
	int schoolAvgVision(Integer school,Double min);
	
	int countByClassesIdAndVisionLeftBetweenOrderByStudentId(Integer schoolId,Double min,Double max);
	
	int countByClassesIdAndVisionLeftGreaterThanOrderByStudentId(Integer ClassId,Double min);
	
	int countByClassesIdAndVisionRightGreaterThanOrderByStudentId(Integer ClassId,Double min);
	
	int countByClassesIdAndVisionRightBetweenOrderByStudentId(Integer schoolId,Double min,Double max);
	
	@Query("select count(1) from RecordMapper where classesId = ?1 and (visionLeft + visionRight)/2 BETWEEN ?2 and ?3 ORDER BY studentId")
	int classAvgVision(Integer school,Double min,Double max);
	
	@Query("select count(1) from RecordMapper where classesId = ?1 and (visionLeft + visionRight)/2 > ?2 ORDER BY studentId")
	int classAvgVision(Integer school,Double min);
}
