package com.threefriend.lightspace.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.threefriend.lightspace.mapper.StudentWordMapper;

/**
 * word操作层
 * @author Administrator
 *
 */
public interface StudentWordRepository extends JpaRepository<StudentWordMapper, Integer>{
	
	Page<StudentWordMapper> findBySchoolIdOrderById(Integer schoolId,Pageable page);
	
	Page<StudentWordMapper> findByClassIdOrderById(Integer classId,Pageable page);
	
	Page<StudentWordMapper> findByNameLikeOrderById(String name,Pageable page);
	
	Page<StudentWordMapper> findByClassIdAndNameLikeOrderById(Integer classId,String name,Pageable page);
	
	List<StudentWordMapper> findByOrderByGenTimeDesc();
	
	StudentWordMapper findTopByStudentIdOrderByGenTimeDesc(Integer studentId);
	
	List<StudentWordMapper> findByStudentIdOrderByGenTimeDesc(Integer studentId);
	
	@Query(value="SELECT * FROM ( SELECT DISTINCT * FROM student_word_mapper where school_id = ?1 ORDER BY id DESC ) A GROUP BY student_id order by id desc limit ?2,?3",nativeQuery = true)
	List<StudentWordMapper> findBySchoolId(Integer schoolId,int page,int size);

	@Query(value="SELECT count(1) FROM ( SELECT DISTINCT * FROM student_word_mapper where school_id = ?1 ORDER BY id DESC ) A GROUP BY student_id order by id desc limit ?2,?3",nativeQuery = true)
	int countBySchoolId(Integer schoolId);
}
