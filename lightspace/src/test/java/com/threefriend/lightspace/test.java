package com.threefriend.lightspace;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.threefriend.lightspace.mapper.RegionMapper;
import com.threefriend.lightspace.repository.RegionRepository;



@RunWith(SpringRunner.class)
@SpringBootTest
public class test {

	@Autowired
	private RegionRepository retion_dao;
	
	
	
	@Test
	public void test() {
		RegionMapper findByName = retion_dao.findByName("唐山");
		if(findByName==null) {
			System.out.println("空的");
		}else {
			System.out.println("有东西");
		}
	}
	
}
