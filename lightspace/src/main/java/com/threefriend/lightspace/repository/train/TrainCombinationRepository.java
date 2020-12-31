package com.threefriend.lightspace.repository.train;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threefriend.lightspace.mapper.train.TrainCombinationMapper;

public interface TrainCombinationRepository extends JpaRepository<TrainCombinationMapper, Integer>{

	List<TrainCombinationMapper> findByPNameAndIsShowOrderByIdDesc(String pName,Integer isShow);
}
