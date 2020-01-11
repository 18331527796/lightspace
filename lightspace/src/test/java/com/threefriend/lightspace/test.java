package com.threefriend.lightspace;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.threefriend.lightspace.util.RedisUtils;



@RunWith(SpringRunner.class)
@SpringBootTest
public class test {

	@Resource
	private RedisUtils redisUtils;
	
	
	
	@Test
	public void test() {
		redisUtils.delete("redis_key");
		String value = redisUtils.get("redis_key");
		System.out.println(value);
	}
}
