package com.threefriend.lightspace.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threefriend.lightspace.mapper.RotationPicMapper;
import com.threefriend.lightspace.mapper.xcx.GzhUserMapper;

public interface RotationPicRepository extends JpaRepository<RotationPicMapper, Integer>{

}
