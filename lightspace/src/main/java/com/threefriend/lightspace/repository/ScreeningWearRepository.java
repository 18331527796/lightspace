package com.threefriend.lightspace.repository;



import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.threefriend.lightspace.xcx.mapper.ScreeningWearMapper;

/**
 * 筛查（戴镜）操作层
 * @author Administrator
 *
 */
public interface ScreeningWearRepository extends JpaRepository<ScreeningWearMapper, Integer>{

	ScreeningWearMapper findTopByStudentIdOrderByGenTime(Integer studentId);
 	
	ScreeningWearMapper findTopByStudentIdOrderByGenTimeDesc(Integer studentId);
	
 	List<ScreeningWearMapper> findByStudentIdAndGenTimeBetweenOrderById(Integer studentId,Date begin,Date end);
	
 	List<ScreeningWearMapper> findByStudentIdOrderByGenTimeDesc(Integer studentId);
 	
 	
 	
 	
 	@Query(value="SELECT count(DISTINCT student_id) from screening_wear_mapper ",nativeQuery = true)
	int findcount();
	@Query(value="SELECT *,max(id) from screening_wear_mapper GROUP BY student_id ORDER BY id DESC limit ?1,?2",nativeQuery = true)
	List<ScreeningWearMapper> findAllByOrderByGenTimeDesc(int page,int size);
	@Query(value="SELECT *,max(id) from screening_wear_mapper GROUP BY student_id ORDER BY id DESC",nativeQuery = true)
	List<ScreeningWearMapper> findAllByOrderByGenTimeDesc();
	
	@Query(value="SELECT count(DISTINCT student_id) from screening_wear_mapper where student_id = ?1",nativeQuery = true)
	int findcountByStudentId(Integer studentId);
	@Query(value="SELECT *,max(id) from screening_wear_mapper where student_id = ?1 GROUP BY student_id ORDER BY id DESC limit ?2,?3",nativeQuery = true)
	List<ScreeningWearMapper> findByStudentIdOrderByGenTimeDesc(Integer studentId,int page,int size);
	@Query(value="SELECT *,max(id) from screening_wear_mapper where student_id = ?1 GROUP BY student_id ORDER BY id DESC",nativeQuery = true)
	List<ScreeningWearMapper> findExcel(Integer studentId);
	
	@Query(value="SELECT count(DISTINCT student_id) from screening_wear_mapper where school_id = ?1",nativeQuery = true)
	int findcountBySchoolId(Integer SchoolId);
	@Query(value="SELECT *,max(id) from screening_wear_mapper where school_id = ?1 GROUP BY student_id ORDER BY id DESC limit ?2,?3",nativeQuery = true)
	List<ScreeningWearMapper> findBySchoolIdOrderByGenTimeDesc(Integer schoolId,int page,int size);
	@Query(value="SELECT *,max(id) from screening_wear_mapper where school_id = ?1 GROUP BY student_id ORDER BY id DESC",nativeQuery = true)
	List<ScreeningWearMapper> findBySchoolIdOrderByGenTimeDesc(Integer schoolId);
 	
	@Query(value="SELECT count(DISTINCT student_id) from screening_wear_mapper where class_id = ?1",nativeQuery = true)
	int findcountByClassId(Integer SchoolId);
	@Query(value="SELECT *,max(id) from screening_wear_mapper where class_id = ?1 GROUP BY student_id ORDER BY id DESC limit ?2,?3",nativeQuery = true)
	List<ScreeningWearMapper> findByClassIdOrderByGenTimeDesc(Integer classId,int page,int size);
	@Query(value="SELECT *,max(id) from screening_wear_mapper where class_id = ?1 GROUP BY student_id ORDER BY id DESC",nativeQuery = true)
	List<ScreeningWearMapper> findByClassIdOrderByGenTimeDesc(Integer classId);

	Page<ScreeningWearMapper> findByStudentIdOrderByGenTimeDesc(Integer studentId,Pageable page);

}
