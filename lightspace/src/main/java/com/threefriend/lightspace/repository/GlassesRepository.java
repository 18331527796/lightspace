package com.threefriend.lightspace.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.threefriend.lightspace.mapper.GlassesMapper;

/** 
 * 系列层
 * @author Administrator
 *
 */
public interface GlassesRepository extends JpaRepository<GlassesMapper, Integer>{

	List<GlassesMapper> findBySeriesIdOrderById(Integer seriesId);
	
	Page<GlassesMapper> findBySeriesId(Integer seriesId,Pageable page);

}
