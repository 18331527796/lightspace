package com.price.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.price.mapper.SeriesMapper;


/** 
 * 系列层
 * @author Administrator
 *
 */
public interface SeriesRepository extends JpaRepository<SeriesMapper, Integer>{

	List<SeriesMapper> findByLabelId(Integer labelId);
}
