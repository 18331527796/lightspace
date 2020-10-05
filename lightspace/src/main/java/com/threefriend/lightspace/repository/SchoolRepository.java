package com.threefriend.lightspace.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.threefriend.lightspace.mapper.SchoolMapper;

/**
 * 学校操作层
 * @author Administrator
 *
 */
public interface SchoolRepository extends JpaRepository<SchoolMapper, Integer>{

	List<SchoolMapper> findByNameLike(String name);
	
	List<SchoolMapper> findByName(String name);
	
	List<SchoolMapper> findAllByOrderByIdDesc();
	
	List<SchoolMapper> findByRegionIdOrderByIdDesc(Integer regionId);
	
	@Query("select id from SchoolMapper where regionId = ?1")
	List<Integer> findIdByRegionId(Integer regionId);
	
	List<SchoolMapper> findByRegionId(Integer regionId);
	
	Page<SchoolMapper> findByRegionId(Integer regionId , Pageable page);
}
