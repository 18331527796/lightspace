package com.threefriend.lightspace.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.threefriend.lightspace.mapper.ScreeningMapper;

/**
 * 筛查操作层
 * @author Administrator
 *
 */
public interface ScreeningRepository extends JpaRepository<ScreeningMapper, Integer>{

 	ScreeningMapper findTopByStudentIdOrderByGenTime(Integer studentId);
}
