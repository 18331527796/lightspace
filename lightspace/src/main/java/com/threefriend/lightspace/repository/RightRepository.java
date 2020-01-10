package com.threefriend.lightspace.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threefriend.lightspace.mapper.RightMapper;

/** 
 * 权限操作层
 * @author Administrator
 *
 */
public interface RightRepository extends JpaRepository<RightMapper, Integer>{

}
