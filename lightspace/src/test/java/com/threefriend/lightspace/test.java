package com.threefriend.lightspace;



import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.threefriend.lightspace.mapper.UserMapper;
import com.threefriend.lightspace.service.Impl.UserServiceImpl;


@RunWith(SpringRunner.class)
@SpringBootTest
public class test {

	@Autowired
	private UserServiceImpl user;
	
	
	@Test
	public void test() {
		
		List<UserMapper> findByLoginName = user.findByLoginName("ffffff");
		System.out.println(findByLoginName.size()+"+++++++++++++++++++");
		
	}
}
