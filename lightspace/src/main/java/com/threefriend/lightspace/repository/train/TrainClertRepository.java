package com.threefriend.lightspace.repository.train;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threefriend.lightspace.mapper.train.TrainClertMapper;

public interface TrainClertRepository extends JpaRepository<TrainClertMapper, Integer>{

	TrainClertMapper findByLoginNameAndPassword(String loginName,String password);
	
	TrainClertMapper findByOpenId(String openId);
}
