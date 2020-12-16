package com.threefriend.lightspace.repository.train;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threefriend.lightspace.mapper.train.TrainChildrenMapper;

public interface TrainChildrenRepository extends JpaRepository<TrainChildrenMapper, Integer>{

	List<TrainChildrenMapper> findByOpenId(String parentId);
	
	TrainChildrenMapper findByIdAndOpenId(Integer childrenId,String parentId);
}
