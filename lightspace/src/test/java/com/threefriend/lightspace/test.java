package com.threefriend.lightspace;


import java.util.ArrayList;
import java.util.Date;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.xcx.service.Impl.ScreeningServiceImpl;



@RunWith(SpringRunner.class)
@SpringBootTest
public class test {

	/*@Autowired
	private ScreeningServiceImpl pp;*/
	
	/*@Resource
	private RedisUtils redisUtil;*/
	
	/*@Test
	public void test() {
		Long now= new Date().getTime(); 
		for (int j = 0; j < 3000; j++) {
			ResultVO selectStudent = pp.selectStudent();
			System.out.println(selectStudent+"---"+j);
		}
		System.out.println((new Date().getTime()-now)/1000);
	}
	*/
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
