package com.threefriend.lightspace.repository;



import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threefriend.lightspace.mapper.ScreeningMapper;

/**
 * 筛查操作层
 * @author Administrator
 *
 */
public interface ScreeningRepository extends JpaRepository<ScreeningMapper, Integer>{

 	ScreeningMapper findTopByStudentIdOrderByGenTime(Integer studentId);
 	
 	List<ScreeningMapper> findByStudentIdOrderByGenTime(Integer studentId);
 	
 	List<ScreeningMapper> findByStudentIdAndGenTimeBetween(Integer studentId,Date begin,Date end);
}
