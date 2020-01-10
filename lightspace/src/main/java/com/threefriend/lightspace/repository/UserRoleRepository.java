package com.threefriend.lightspace.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threefriend.lightspace.mapper.UserRoleRelation;

/**
 * 用户角色操作层
 * @author Administrator
 *
 */
public interface UserRoleRepository extends JpaRepository<UserRoleRelation, Integer>{

}
