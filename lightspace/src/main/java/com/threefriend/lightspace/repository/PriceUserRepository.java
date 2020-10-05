package com.threefriend.lightspace.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threefriend.lightspace.mapper.PriceUserMapper;

public interface PriceUserRepository extends JpaRepository<PriceUserMapper, Integer>{

	PriceUserMapper findByLoginNameAndPassword(String loginName , String password);
}
