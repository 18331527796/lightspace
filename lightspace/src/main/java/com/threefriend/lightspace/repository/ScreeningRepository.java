package com.threefriend.lightspace.repository;



import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.threefriend.lightspace.mapper.xcx.ScreeningMapper;
import com.threefriend.lightspace.mapper.xcx.ScreeningWearMapper;

/**
 * 筛查操作层
 * @author Administrator
 *
 */
public interface ScreeningRepository extends JpaRepository<ScreeningMapper, Integer>{

 	ScreeningMapper findTopByStudentIdOrderByGenTimeDesc(Integer studentId);
 	
 	List<ScreeningMapper> findByStudentIdOrderByGenTimeDesc(Integer studentId);
	
	List<ScreeningMapper> findByStudentIdAndGenTimeBetweenOrderById(Integer studentId,Date begin,Date end);
	
	@Query(value="SELECT count(DISTINCT student_id) from screening_mapper ",nativeQuery = true)
	int findcount();
	@Query(value="SELECT * FROM ( SELECT DISTINCT * FROM screening_mapper ORDER BY id DESC ) A GROUP BY student_id order by id desc limit ?1,?2",nativeQuery = true)
	List<ScreeningMapper> findAllByOrderByGenTimeDesc(int page,int size);
	@Query(value="SELECT * FROM ( SELECT DISTINCT * FROM screening_mapper ORDER BY id DESC ) A GROUP BY student_id order by id desc",nativeQuery = true)
	List<ScreeningMapper> findAllByOrderByGenTimeDesc();
	
	
	
	
	@Query(value="SELECT count(DISTINCT student_id) from screening_mapper where student_id = ?1",nativeQuery = true)
	int findcountByStudentId(Integer studentId);
	@Query(value="SELECT * FROM ( SELECT DISTINCT * FROM screening_mapper where student_id = ?1 ORDER BY id DESC ) A GROUP BY student_id order by id desc limit ?2,?3",nativeQuery = true)
	List<ScreeningMapper> findByStudentIdOrderByGenTimeDesc(Integer studentId,int page,int size);
	@Query(value="SELECT * FROM ( SELECT DISTINCT * FROM screening_mapper where student_id = ?1 ORDER BY id DESC ) A GROUP BY student_id order by id desc",nativeQuery = true)
	List<ScreeningMapper> findExcel(Integer studentId);
	
	
	
	
	@Query(value="SELECT count(DISTINCT student_id) from screening_mapper where school_id = ?1",nativeQuery = true)
	int findcountBySchoolId(Integer SchoolId);
	@Query(value="SELECT * FROM ( SELECT DISTINCT * FROM screening_mapper where school_id = ?1 ORDER BY id DESC ) A GROUP BY student_id order by id desc limit ?2,?3",nativeQuery = true)
	List<ScreeningMapper> findBySchoolIdOrderByGenTimeDesc(Integer schoolId,int page,int size);
	@Query(value="SELECT * FROM ( SELECT DISTINCT * FROM screening_mapper where school_id = ?1 ORDER BY id DESC ) A GROUP BY student_id order by id desc",nativeQuery = true)
	List<ScreeningMapper> findBySchoolIdOrderByGenTimeDesc(Integer schoolId);
 	
	
	
	
	@Query(value="SELECT count(DISTINCT student_id) from screening_mapper where class_id = ?1",nativeQuery = true)
	int findcountByClassId(Integer SchoolId);
	@Query(value="SELECT * FROM ( SELECT DISTINCT * FROM screening_mapper where class_id = ?1 ORDER BY id DESC ) A GROUP BY student_id order by id desc limit ?2,?3",nativeQuery = true)
	List<ScreeningMapper> findByClassIdOrderByGenTimeDesc(Integer classId,int page,int size);
	@Query(value="SELECT * FROM ( SELECT DISTINCT * FROM screening_mapper where class_id = ?1 ORDER BY id DESC ) A GROUP BY student_id order by id desc",nativeQuery = true)
	List<ScreeningMapper> findByClassIdOrderByGenTimeDesc(Integer classId);
 	
 	
 	
 	
 	Page<ScreeningMapper> findByStudentIdOrderByGenTimeDesc(Integer studentId,Pageable Pageable);
 	
}
