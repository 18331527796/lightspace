package com.threefriend.lightspace.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threefriend.lightspace.mapper.RegionMapper;

/**
 * 地区操作层
 * @author Administrator
 *
 */
public interface RegionRepository extends JpaRepository<RegionMapper, Integer>{

}
