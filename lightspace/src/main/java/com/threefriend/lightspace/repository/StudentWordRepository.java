package com.threefriend.lightspace.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threefriend.lightspace.mapper.StudentMapper;
import com.threefriend.lightspace.mapper.StudentWordMapper;

/**
 * word操作层
 * @author Administrator
 *
 */
public interface StudentWordRepository extends JpaRepository<StudentWordMapper, Integer>{
	
	List<StudentWordMapper> findByOrderByGenTimeDesc();
	
	StudentWordMapper findTopByStudentIdOrderByGenTimeDesc(Integer studentId);
	
	List<StudentWordMapper> findByStudentIdOrderByGenTimeDesc(Integer studentId);

}
