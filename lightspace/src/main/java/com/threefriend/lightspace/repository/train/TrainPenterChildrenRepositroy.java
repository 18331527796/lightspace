package com.threefriend.lightspace.repository.train;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threefriend.lightspace.mapper.train.TrainParentChildrenMapper;

public interface TrainPenterChildrenRepositroy extends JpaRepository<TrainParentChildrenMapper, Integer>{

	List<TrainParentChildrenMapper> findByOpenId(String openId);
	
	List<TrainParentChildrenMapper> findByOpenIdAndChildrenId(String openId,Integer childrenId);
}
