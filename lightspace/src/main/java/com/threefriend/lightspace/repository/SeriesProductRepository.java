package com.threefriend.lightspace.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threefriend.lightspace.mapper.SeriesProductMapper;

/** 
 * 权限操作层
 * @author Administrator
 *
 */
public interface SeriesProductRepository extends JpaRepository<SeriesProductMapper, Integer>{

	List<SeriesProductMapper> findBySeriesId(Integer seriesId);
	
	List<SeriesProductMapper> findBySeriesIdAndType(Integer seriesId,Integer type);
}
