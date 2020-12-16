package com.threefriend.lightspace.repository.train;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threefriend.lightspace.mapper.train.TrainProgramMapper;

public interface TrainProgramRepository extends JpaRepository<TrainProgramMapper, Integer>{

	List<TrainProgramMapper> findByDirectionOrderById(String direction);
}
