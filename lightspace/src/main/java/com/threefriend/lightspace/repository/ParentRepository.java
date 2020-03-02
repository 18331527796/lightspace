package com.threefriend.lightspace.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threefriend.lightspace.mapper.ParentMapper;

/**
 * 视标操作层
 * @author Administrator
 *
 */
public interface ParentRepository extends JpaRepository<ParentMapper, Integer>{
	
	ParentMapper findByOpenId(String openId);

}
