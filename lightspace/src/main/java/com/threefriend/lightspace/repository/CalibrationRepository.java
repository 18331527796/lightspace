package com.threefriend.lightspace.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threefriend.lightspace.mapper.xcx.CalibrationMapper;

public interface CalibrationRepository extends JpaRepository<CalibrationMapper,Integer>{
	
	CalibrationMapper findByOpenId(String openId);

}
