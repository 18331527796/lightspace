package com.threefriend.lightspace;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.threefriend.lightspace.mapper.UserMapper;
import com.threefriend.lightspace.repository.UserRepository;
import com.threefriend.lightspace.service.Impl.UserServiceImpl;
import com.threefriend.lightspace.util.RedisUtils;



@RunWith(SpringRunner.class)
@SpringBootTest
public class test {

	
	@Autowired
	private UserServiceImpl user_impl;
	
	@Resource
	private RedisUtils redisUtil;
	
	@Test
	public void test() {
		/*String token= "57565a5a47494e5b5f5c4a51";
		redisUtils.set(token, "2");
		redisUtils.setTime(token, 1800);*/
		String value = redisUtil.get("57565a5a47494d5a595f4a51");
		System.out.println(value);
	}
	
	/*@Test
	public void userRightTest() {
		user_impl.getUserRight("00");
	}
	
	@Test
	public void login() {
		List<UserMapper> findByloginNameAndPassword = user_dao.findByloginNameAndPassword("admin", "e10adc3949ba59abbe56e057f20f883e");
		System.out.println(findByloginNameAndPassword.size());
	}*/
}
