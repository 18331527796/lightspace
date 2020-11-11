package com.threefriend.lightspace.repository;



import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.threefriend.lightspace.mapper.xcx.ScreeningWearMapper;

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
	@Query(value="SELECT * FROM ( SELECT DISTINCT * FROM screening_wear_mapper ORDER BY id DESC ) A GROUP BY student_id order by id desc limit ?1,?2",nativeQuery = true)
	List<ScreeningWearMapper> findAllByOrderByGenTimeDesc(int page,int size);
	@Query(value="SELECT * FROM ( SELECT DISTINCT * FROM screening_wear_mapper ORDER BY id DESC ) A GROUP BY student_id order by id desc",nativeQuery = true)
	List<ScreeningWearMapper> findAllByOrderByGenTimeDesc();
	
	@Query(value="SELECT count(DISTINCT student_id) from screening_wear_mapper where student_id = ?1",nativeQuery = true)
	int findcountByStudentId(Integer studentId);
	
	@Query(value="SELECT count(DISTINCT student_id) from screening_wear_mapper where student_name like ?1 and school_id = ?2",nativeQuery = true)
	int findcountByName(String name,Integer schoolId);
	
	@Query(value="SELECT count(DISTINCT student_id) from screening_wear_mapper where student_name like ?1 and school_id = ?2 and gen_time BETWEEN ?3 and ?4",nativeQuery = true)
	int findcountByName(String name,Integer schoolId,Date beginTime,Date endTime);
	
	@Query(value="SELECT * FROM ( SELECT DISTINCT * FROM screening_wear_mapper where student_id = ?1 ORDER BY id DESC ) A GROUP BY student_id order by id desc limit ?2,?3",nativeQuery = true)
	List<ScreeningWearMapper> findByStudentIdOrderByGenTimeDesc(Integer studentId,int page,int size);
	
	@Query(value="SELECT * FROM ( SELECT DISTINCT * FROM screening_wear_mapper where student_name like ?1 and school_id = ?2 ORDER BY id DESC ) A GROUP BY student_id order by id desc limit ?3,?4",nativeQuery = true)
	List<ScreeningWearMapper> findByNameOrderByGenTimeDesc(String name,Integer schoolId,int page,int size);
	
	@Query(value="SELECT * FROM ( SELECT DISTINCT * FROM screening_wear_mapper where student_name like ?1 and school_id = ?2 and gen_time BETWEEN ?3 and ?4 ORDER BY id DESC ) A GROUP BY student_id order by id desc limit ?5,?6",nativeQuery = true)
	List<ScreeningWearMapper> findByNameOrderByGenTimeDesc(String name,Integer schoolId,Date beginTime,Date endTime ,int page,int size);
	
	@Query(value="SELECT * FROM ( SELECT DISTINCT * FROM screening_wear_mapper where student_id = ?1 ORDER BY id DESC ) A GROUP BY student_id order by id desc",nativeQuery = true)
	List<ScreeningWearMapper> findExcel(Integer studentId);
	
	@Query(value="SELECT count(DISTINCT student_id) from screening_wear_mapper where school_id = ?1",nativeQuery = true)
	int findcountBySchoolId(Integer SchoolId);
	
	@Query(value="SELECT count(DISTINCT student_id) from screening_wear_mapper where school_id = ?1 and gen_time BETWEEN ?2 and ?3",nativeQuery = true)
	int findcountBySchoolId(Integer SchoolId,Date beginTime,Date endTime);
	
	@Query(value="SELECT count(DISTINCT student_id) from screening_wear_mapper where school_id in ?1",nativeQuery = true)
	int findcountBySchoolId(List<Integer> SchoolId);
	
	@Query(value="SELECT count(DISTINCT student_id) from screening_wear_mapper where school_id in ?1 and gen_time BETWEEN ?2 and ?3",nativeQuery = true)
	int findcountBySchoolId(List<Integer> SchoolId,Date beginTime,Date endTime);
	
	
	@Query(value="SELECT count(DISTINCT student_id) from screening_wear_mapper where class_id in ?1",nativeQuery = true)
	int findcountByClassId(List<Integer> classId);
	
	@Query(value="SELECT * FROM ( SELECT DISTINCT * FROM screening_wear_mapper where school_id = ?1 ORDER BY id DESC ) A GROUP BY student_id order by id desc limit ?2,?3",nativeQuery = true)
	List<ScreeningWearMapper> findBySchoolIdOrderByGenTimeDesc(Integer schoolId,int page,int size);
	
	@Query(value="SELECT * FROM ( SELECT DISTINCT * FROM screening_wear_mapper where school_id = ?1 and gen_time BETWEEN ?2 and ?3 ORDER BY id DESC ) A GROUP BY student_id order by id desc limit ?4,?5",nativeQuery = true)
	List<ScreeningWearMapper> findBySchoolIdOrderByGenTimeDesc(Integer schoolId,Date beginTime,Date endTime,int page,int size);
	
	@Query(value="SELECT * FROM ( SELECT DISTINCT * FROM screening_wear_mapper where school_id in ?1 ORDER BY id DESC ) A GROUP BY student_id order by id desc limit ?2,?3",nativeQuery = true)
	List<ScreeningWearMapper> findBySchoolIdOrderByGenTimeDesc(List<Integer> schoolId,int page,int size);
	
	@Query(value="SELECT * FROM ( SELECT DISTINCT * FROM screening_wear_mapper where school_id in ?1 and gen_time BETWEEN ?2 and ?3 ORDER BY id DESC ) A GROUP BY student_id order by id desc limit ?4,?5",nativeQuery = true)
	List<ScreeningWearMapper> findBySchoolIdOrderByGenTimeDesc(List<Integer> schoolId,Date beginTime,Date endTime,int page,int size);
	
	@Query(value="SELECT * FROM ( SELECT DISTINCT * FROM screening_wear_mapper where school_id = ?1 ORDER BY id DESC ) A GROUP BY student_id order by id desc",nativeQuery = true)
	List<ScreeningWearMapper> findBySchoolIdOrderByGenTimeDesc(Integer schoolId);
 	
	@Query(value="SELECT count(DISTINCT student_id) from screening_wear_mapper where class_id = ?1",nativeQuery = true)
	int findcountByClassId(Integer SchoolId);
	
	@Query(value="SELECT count(DISTINCT student_id) from screening_wear_mapper where class_id = ?1 and gen_time BETWEEN ?2 and ?3",nativeQuery = true)
	int findcountByClassId(Integer SchoolId,Date beginTime,Date endTime);
	
	@Query(value="SELECT * FROM ( SELECT DISTINCT * FROM screening_wear_mapper where class_id = ?1 ORDER BY id DESC ) A GROUP BY student_id order by id desc limit ?2,?3",nativeQuery = true)
	List<ScreeningWearMapper> findByClassIdOrderByGenTimeDesc(Integer classId,int page,int size);
	
	@Query(value="SELECT * FROM ( SELECT DISTINCT * FROM screening_wear_mapper where class_id = ?1 and gen_time BETWEEN ?2 and ?3 ORDER BY id DESC ) A GROUP BY student_id order by id desc limit ?4,?5",nativeQuery = true)
	List<ScreeningWearMapper> findByClassIdOrderByGenTimeDesc(Integer classId,Date beginTime,Date endTime ,int page,int size);
	
	@Query(value="SELECT * FROM ( SELECT DISTINCT * FROM screening_wear_mapper where class_id = ?1 ORDER BY id DESC ) A GROUP BY student_id order by id desc",nativeQuery = true)
	List<ScreeningWearMapper> findByClassIdOrderByGenTimeDesc(Integer classId);

	Page<ScreeningWearMapper> findByStudentIdOrderByGenTimeDesc(Integer studentId,Pageable page);

	@Query(value="SELECT count(DISTINCT student_id) from screening_wear_mapper where school_id = ?1 and (vision_left_str + vision_right_str)/2 < ?2",nativeQuery = true)
	int findcountBySchoolId(Integer SchoolId,Double AVG);
	
	@Query(value="SELECT count(DISTINCT student_id) from screening_wear_mapper where class_id in ?1 and (vision_left_str + vision_right_str)/2 < ?2",nativeQuery = true)
	int findcountByClassId(List<Integer> classId,Double AVG);
}
