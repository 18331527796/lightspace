package com.threefriend.lightspace.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.threefriend.lightspace.mapper.SortMapper;

/** 
 * 座位操作层
 * @author Administrator
 *
 */
public interface SortRepository extends JpaRepository<SortMapper, Integer>{

	SortMapper findTopByClassIdAndTypeOrderByGenTime(Integer classid,Integer type);
	
	List<SortMapper> findByClassIdOrderByGenTimeDesc(Integer classId);
	
	SortMapper findByClassId(Integer classId);
	@Query(value="select * from sort_mapper GROUP BY class_id ORDER BY id desc",nativeQuery = true)
	List<SortMapper> findAllGroupByClassIdOrderById();
}
