package com.threefriend.lightspace.repository.train;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threefriend.lightspace.mapper.train.TrainChildrenRowMapper;

public interface TrainChildrenRowRepository extends JpaRepository<TrainChildrenRowMapper, Integer>{
	@Transactional
	void deleteByChildrenCombinationId(Integer c_c_id);
	
	List<TrainChildrenRowMapper> findByChildrenCombinationIdOrderByRow(Integer c_c_id);
	
	TrainChildrenRowMapper findByChildrenCombinationIdAndRow(Integer c_c_id,Integer row);
	
	int countByChildrenCombinationIdAndIsSuccess(Integer c_c_id,Integer isSuccess); 
}
