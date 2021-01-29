package com.price.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.price.mapper.PriceUserMapper;


public interface PriceUserRepository extends JpaRepository<PriceUserMapper, Integer>{

	PriceUserMapper findByLoginNameAndPassword(String loginName , String password);
}
