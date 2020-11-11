package com.threefriend.lightspace.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.threefriend.lightspace.mapper.DiopterMapper;
import com.threefriend.lightspace.mapper.xcx.ScreeningMapper;
import com.threefriend.lightspace.mapper.xcx.ScreeningWearMapper;

public interface DiopterRepository extends JpaRepository<DiopterMapper, Integer>{
	
	List<DiopterMapper> findByStudentIdOrderByIdDesc(Integer studentId);
	
	DiopterMapper findTopByStudentIdOrderByIdDesc(Integer studentId);

	@Query(value="SELECT * FROM ( SELECT DISTINCT * FROM diopter_mapper where student_id = ?1 ORDER BY id DESC ) A GROUP BY student_id order by id desc limit ?2,?3",nativeQuery = true)
	List<DiopterMapper> findByStudentIdOrderById(Integer studentId,int page,int size);
	
	@Query(value="SELECT count(DISTINCT student_id) from diopter_mapper where student_id = ?1",nativeQuery = true)
	int findcountByStudentId(Integer studentId);
	
	@Query(value="SELECT count(DISTINCT student_id) from diopter_mapper where class_id = ?1",nativeQuery = true)
	int findcountByClassId(Integer SchoolId);
	@Query(value="SELECT * FROM ( SELECT DISTINCT * FROM diopter_mapper where class_id = ?1 ORDER BY id DESC ) A GROUP BY student_id order by id desc limit ?2,?3",nativeQuery = true)
	List<DiopterMapper> findByClassIdOrderById(Integer classId,int page,int size);
	
	
	@Query(value="SELECT * FROM ( SELECT DISTINCT * FROM diopter_mapper where school_id = ?1 ORDER BY id DESC ) A GROUP BY student_id order by id desc limit ?2,?3",nativeQuery = true)
	List<DiopterMapper> findBySchoolIdOrderById(Integer schoolId,int page,int size);
	
	@Query(value="SELECT count(DISTINCT student_id) from diopter_mapper where school_id = ?1",nativeQuery = true)
	int findcountBySchoolId(Integer SchoolId);
	
	@Query(value="SELECT * FROM ( SELECT DISTINCT * FROM diopter_mapper where school_id in ?1 ORDER BY id DESC ) A GROUP BY student_id order by id desc limit ?2,?3",nativeQuery = true)
	List<DiopterMapper> findBySchoolIdOrderById(List<Integer> schoolId,int page,int size);
	
	@Query(value="SELECT count(DISTINCT student_id) from diopter_mapper where school_id in ?1",nativeQuery = true)
	int findcountBySchoolId(List<Integer> SchoolId);
	
	
	@Query(value="SELECT count(DISTINCT student_id) from diopter_mapper ",nativeQuery = true)
	int findcount();
	@Query(value="SELECT * FROM ( SELECT DISTINCT * FROM diopter_mapper ORDER BY id DESC ) A GROUP BY student_id order by id desc limit ?1,?2",nativeQuery = true)
	List<DiopterMapper> findAllByOrderById(int page,int size);
	
	
	@Query(value="SELECT count(DISTINCT student_id) from diopter_mapper where student_name like ?1 and school_id = ?2",nativeQuery = true)
	int findcountByName(String name , Integer schoolId);
	@Query(value="SELECT * FROM ( SELECT DISTINCT * FROM diopter_mapper where student_name like ?1 and school_id = ?2 ORDER BY id DESC ) A GROUP BY student_id order by id desc limit ?3,?4",nativeQuery = true)
	List<DiopterMapper> findByNameOrderByGenTimeDesc(String name,Integer schoolId,int page,int size);
	
	Page<DiopterMapper> findByStudentIdOrderByIdDesc(Integer studentId,Pageable Pageable);
}
