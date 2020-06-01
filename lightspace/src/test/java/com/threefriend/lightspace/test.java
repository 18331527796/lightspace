package com.threefriend.lightspace;

import java.io.File;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
	
	@Test
	public void test() throws Exception{
		
		List<ParentStudentRelation> findByStudentId = p_s.findByStudentId(617);
		
		for (ParentStudentRelation parentStudentRelation : findByStudentId) {
			
			List<ParentStudentRelation> findByParentId = p_s.findByParentId(parentStudentRelation.getParentId());
			System.out.println(findByParentId.size());
			for (ParentStudentRelation parentStudentRelation2 : findByParentId) {
				
				System.out.println(student.findById(parentStudentRelation2.getStudentId()).get().getName());
				
			}
			Thread.currentThread().sleep(200);
			System.err.println("NEXT PAEENT SYSO");
		}
		
	}
	
	
	
}
