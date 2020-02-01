package com.threefriend.lightspace.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threefriend.lightspace.mapper.SortMapper;

/** 
 * 座位操作层
 * @author Administrator
 *
 */
public interface SortRepository extends JpaRepository<SortMapper, Integer>{

	SortMapper findTopByClassIdAndTypeOrderByGenTime(Integer classid,Integer type);
}
