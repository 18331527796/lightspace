package com.threefriend.lightspace.repository;



import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threefriend.lightspace.mapper.xcx.ScreeningMapper;

/**
 * 筛查操作层
 * @author Administrator
 *
 */
public interface ScreeningRepository extends JpaRepository<ScreeningMapper, Integer>{

 	ScreeningMapper findTopByStudentIdOrderByGenTimeDesc(Integer studentId);
 	
 	List<ScreeningMapper> findByStudentIdOrderByGenTimeDesc(Integer studentId);
 	
 	List<ScreeningMapper> findByStudentIdAndGenTimeBetweenOrderById(Integer studentId,Date begin,Date end);
}
