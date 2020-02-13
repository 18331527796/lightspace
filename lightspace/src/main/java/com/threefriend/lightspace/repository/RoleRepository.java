package com.threefriend.lightspace.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threefriend.lightspace.mapper.RoleMapper;

/** 
 * 角色操作层
 * @author Administrator
 *
 */
public interface RoleRepository extends JpaRepository<RoleMapper, Integer>{
	
	List<RoleMapper> findByRoleNameLike(String name);

}
