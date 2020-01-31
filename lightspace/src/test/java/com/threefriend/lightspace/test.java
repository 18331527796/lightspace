package com.threefriend.lightspace;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
		
		/*List<StudentMapper> findByClassesIdOrderBySittingHeight = student_dao.findByClassesIdOrderBySittingHeightDesc(18);
		List<Integer> ids = new ArrayList<>();
		for (StudentMapper studentMapper : findByClassesIdOrderBySittingHeight) {
			ids.add(studentMapper.getId());
		}
		List<RecordMapper> findAllBylastGenTime = record_dao.findAllById(ids);
		System.out.println(findAllBylastGenTime.size()+"!!!!!!!!!!!!!!");
		for (RecordMapper recordMapper : findAllBylastGenTime) {
			System.out.println(recordMapper.getStudentName());
			System.out.println(recordMapper.getGenTime());
			
		}*/
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
