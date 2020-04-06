package com.threefriend.lightspace.repository;



import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threefriend.lightspace.mapper.ScreeningMapper;
import com.threefriend.lightspace.mapper.ScreeningWearMapper;

/**
 * 筛查操作层
 * @author Administrator
 *
 */
public interface ScreeningWearRepository extends JpaRepository<ScreeningWearMapper, Integer>{

	ScreeningWearMapper findTopByStudentIdOrderByGenTime(Integer studentId);
 	
 	List<ScreeningWearMapper> findByStudentIdOrderByGenTime(Integer studentId);
 	
 	List<ScreeningWearMapper> findByStudentIdAndGenTimeBetween(Integer studentId,Date begin,Date end);
}
