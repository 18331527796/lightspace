package com.threefriend.lightspace.repository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.threefriend.lightspace.mapper.ClassesMapper;
import com.threefriend.lightspace.mapper.SysLogMapper;
import com.threefriend.lightspace.mapper.xcx.IntegralMapper;

/**
 * 积分管理层
 * @author Administrator
 *
 */
public interface IntegralRepository extends JpaRepository<IntegralMapper, Integer>{

	List<IntegralMapper> findByStudentIdOrderByGenTimeDesc(Integer studentId);
	
	//收支标识(0:支出，1:收入)
	@Query(value="SELECT SUM(integral) FROM integral_mapper  WHERE state = ?1 AND student_id = ?2 ORDER BY gen_time desc",nativeQuery = true)
	Long findIntegtalByState(Integer state,Integer studentId);
	
	@Query(value="SELECT SUM(integral) FROM integral_mapper  WHERE student_id = ?1",nativeQuery = true)
	Long findIntegtalByStudentId(Integer studentId);
}
