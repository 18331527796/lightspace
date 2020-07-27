package com.threefriend.lightspace.repository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.threefriend.lightspace.mapper.ClassesMapper;
import com.threefriend.lightspace.mapper.SysLogMapper;
import com.threefriend.lightspace.mapper.xcx.IntegralMapper;
import com.threefriend.lightspace.vo.IntegralVO;

/**
 * 积分管理层
 * @author Administrator
 *
 */
public interface IntegralRepository extends JpaRepository<IntegralMapper, Integer>{

	Page<IntegralMapper> findByStudentId(Integer studentId,Pageable page);
	
	List<IntegralMapper> findByStudentIdOrderByGenTimeDesc(Integer studentId);
	
	IntegralMapper findByStudentIdAndDetailedAndGenTimeBetween(Integer studentId,String detailed,Date begin , Date end);
	
	int countByStudentIdAndDetailedAndGenTimeBetween(Integer studentId,String detailed,Date begin , Date end);
	
	//收支标识(0:支出，1:收入)
	@Query(value="SELECT SUM(integral) FROM integral_mapper  WHERE state = ?1 AND student_id = ?2 ORDER BY gen_time desc",nativeQuery = true)
	Long findIntegtalByState(Integer state,Integer studentId);
	
	@Query(value="select student_id from integral_mapper a where state = 1 group by student_id order by SUM(integral) desc limit 10 ",nativeQuery = true)
	List<Integer> integtalRanking();
	
	@Query(value="select count(1) from ( select SUM(integral) from integral_mapper where state = 1 group by student_id HAVING SUM(integral) >= (select SUM(integral) from integral_mapper where student_id = ?1 and state = 1) ) a",nativeQuery = true)
	Long myRanking(Integer studentId);
}
