package com.threefriend.lightspace;



import org.apache.commons.lang.time.DateFormatUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;



@RunWith(SpringRunner.class)
@SpringBootTest
public class test {
	
	
	
	@Test
	public void test(){
		String array = "[{\"levelPre\":0.6,\"right\":5,\"wrong\":0},{\"levelPre\":0.8,\"right\":5,\"wrong\":0}]";
		JSONArray jsonarray = JSONArray.fromObject(array); 
		for (Object object : jsonarray) {
			JSONObject  jsonObj  = JSONObject.fromObject(object);
			System.out.println(jsonObj.get("right"));
		}
	}
	
}
