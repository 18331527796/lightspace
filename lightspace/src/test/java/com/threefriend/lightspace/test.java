package com.threefriend.lightspace;

import java.io.File;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import com.threefriend.lightspace.mapper.StudentMapper;
import com.threefriend.lightspace.mapper.xcx.ParentStudentRelation;
import com.threefriend.lightspace.repository.ParentStudentRepository;
import com.threefriend.lightspace.repository.StudentRepository;
import com.threefriend.lightspace.util.CompactAlgorithm;




@RunWith(SpringRunner.class)
@SpringBootTest
public class test {
	@Autowired
	private ParentStudentRepository p_s;
	@Autowired
	private StudentRepository student;
	
	@SuppressWarnings("deprecation")
	@Test
	public void test() throws Exception{
	}
	
	
	
}
