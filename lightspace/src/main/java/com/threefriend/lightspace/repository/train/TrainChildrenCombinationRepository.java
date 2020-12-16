package com.threefriend.lightspace.repository.train;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.threefriend.lightspace.mapper.train.TrainChildrenCombinationMapper;

public interface TrainChildrenCombinationRepository extends JpaRepository<TrainChildrenCombinationMapper, Integer>{
	@Query(value="select * from train_children_combination_mapper where children_id = ?1 order by is_success desc , sort",nativeQuery = true)
	List<TrainChildrenCombinationMapper> findByChildrenIdOrderByIsOpen(Integer childrenId);
	
	TrainChildrenCombinationMapper findTopByIsOpenAndChildrenId(Integer isOpen,Integer childrenId);
	
	int countByChildrenId(Integer childrenId);
}
