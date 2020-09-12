package com.threefriend.lightspace.repository.schoolclient;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threefriend.lightspace.mapper.schoolclient.SchoolRightMapper;

/** 
 * 权限操作层
 * @author Administrator
 *
 */
public interface SchoolRightRepository extends JpaRepository<SchoolRightMapper, Integer>{

}
