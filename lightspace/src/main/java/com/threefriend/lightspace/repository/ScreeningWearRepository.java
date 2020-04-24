package com.threefriend.lightspace.repository;



import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threefriend.lightspace.mapper.xcx.ScreeningMapper;
import com.threefriend.lightspace.mapper.xcx.ScreeningWearMapper;

/**
 * 筛查（戴镜）操作层
 * @author Administrator
 *
 */
public interface ScreeningWearRepository extends JpaRepository<ScreeningWearMapper, Integer>{

	ScreeningWearMapper findTopByStudentIdOrderByGenTime(Integer studentId);
 	
	ScreeningWearMapper findTopByStudentIdOrderByGenTimeDesc(Integer studentId);
	
 	List<ScreeningWearMapper> findByStudentIdOrderByGenTimeDesc(Integer studentId);
 	
 	List<ScreeningWearMapper> findByStudentIdAndGenTimeBetweenOrderById(Integer studentId,Date begin,Date end);
}
