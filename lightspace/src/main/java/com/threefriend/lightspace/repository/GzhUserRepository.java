package com.threefriend.lightspace.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.threefriend.lightspace.xcx.mapper.GzhUserMapper;


/**
 * 公众号用户操作层
 * @author Administrator
 *
 */
public interface GzhUserRepository extends JpaRepository<GzhUserMapper, Integer>{

	GzhUserMapper findByOpenid(String openId);
	
	GzhUserMapper findByUnionid(String unionId);
	@Transactional
	void deleteByOpenid(String openId);
}
