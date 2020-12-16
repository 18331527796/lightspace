package com.threefriend.lightspace.repository.train;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threefriend.lightspace.mapper.train.TrainParentMapper;


public interface TrainParentRepository extends JpaRepository<TrainParentMapper, Integer>{

	TrainParentMapper findByOpenId(String openId);
	
	TrainParentMapper findByPhone(String phone);
}
