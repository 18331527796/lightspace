package com.threefriend.lightspace;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.threefriend.lightspace.constant.VisionEnums;
import com.threefriend.lightspace.mapper.RecordMapper;
import com.threefriend.lightspace.mapper.StudentMapper;
import com.threefriend.lightspace.repository.RecordRepository;
import com.threefriend.lightspace.repository.RoleRightRepository;
import com.threefriend.lightspace.repository.StudentRepository;
import com.threefriend.lightspace.util.RedisUtils;



@RunWith(SpringRunner.class)
@SpringBootTest
public class test {

	
	@Autowired
	private RoleRightRepository role_right_dao;
	
	@Autowired
	private StudentRepository student_dao;
	@Autowired
	private RecordRepository record_dao;
	
	/*@Resource
	private RedisUtils redisUtil;*/
	
	@Test
	public void test() {
		
		int i = record_dao.countBySchoolIdAndVisionLeftBetweenOrderByStudentId(22,0.8,3.0);
		System.out.println(i);
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
