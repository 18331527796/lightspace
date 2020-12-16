package com.threefriend.lightspace.repository.train;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threefriend.lightspace.mapper.train.TrainChildrenWordMapper;

public interface TrainChildrenWordRepository extends JpaRepository<TrainChildrenWordMapper, Integer>{

	List<TrainChildrenWordMapper> findByChildrenIdOrderByIdDesc(Integer childrenId);
}
