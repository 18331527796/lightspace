package com.threefriend.lightspace;



import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.threefriend.lightspace.enums.AccountEnums;
import com.threefriend.lightspace.mapper.StudentMapper;
import com.threefriend.lightspace.repository.StudentRepository;
import com.threefriend.lightspace.service.Impl.xcx.ParentXcxServiceImpl;
import com.threefriend.lightspace.util.xcx.HttpClientUtils;
import com.threefriend.lightspace.util.xcx.SendMessageUtils;
import com.threefriend.lightspace.util.xcx.WeChatUtils;

import net.coobird.thumbnailator.Thumbnails;



@RunWith(SpringRunner.class)
@SpringBootTest
public class test {
	@Autowired
	private StudentRepository impl;
	
	
	@Test
	public void test() throws Exception{
		Thumbnails.of("C:\\Users\\Administrator\\Desktop\\79730190_p0.png") 
        .scale(1f) 
        .outputQuality(0.25f) 
        .toFile("C:\\Users\\Administrator\\Desktop\\p0.jpg");
		
	}
	
}
