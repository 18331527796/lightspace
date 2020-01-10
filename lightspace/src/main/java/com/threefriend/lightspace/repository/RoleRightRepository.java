package com.threefriend.lightspace.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threefriend.lightspace.mapper.RoleRightRelation;

/**
 * 角色权限操作层
 * 
 * @author Administrator
 *
 */
public interface RoleRightRepository extends JpaRepository<RoleRightRelation, Integer>{

}
