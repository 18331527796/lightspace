package com.threefriend.lightspace.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threefriend.lightspace.mapper.SeriesMapper;

/** 
 * 系列层
 * @author Administrator
 *
 */
public interface SeriesRepository extends JpaRepository<SeriesMapper, Integer>{

	List<SeriesMapper> findByLabelId(Integer labelId);
}
