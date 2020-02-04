package com.threefriend.lightspace.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threefriend.lightspace.mapper.SortMapper;

/** 
 * 座位操作层
 * @author Administrator
 *
 */
public interface SortRepository extends JpaRepository<SortMapper, Integer>{

	SortMapper findTopByClassIdAndTypeOrderByGenTime(Integer classid,Integer type);
	
	List<SortMapper> findByClassIdOrderByGenTimeDesc(Integer classId);
	
	int countByClassId(Integer classId);
}
