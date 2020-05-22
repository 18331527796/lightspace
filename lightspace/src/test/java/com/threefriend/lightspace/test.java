package com.threefriend.lightspace;



import java.io.File;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.collections.map.HashedMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.threefriend.lightspace.enums.UrlEnums;
import com.threefriend.lightspace.enums.VisionEnums;
import com.threefriend.lightspace.mapper.StudentMapper;
import com.threefriend.lightspace.repository.ClassesRepository;
import com.threefriend.lightspace.repository.SchoolRepository;
import com.threefriend.lightspace.repository.ScreeningWearRepository;
import com.threefriend.lightspace.repository.StudentRepository;
import com.threefriend.lightspace.util.JfreeUtil;
import com.threefriend.lightspace.util.WordUtil;

import cn.afterturn.easypoi.entity.ImageEntity;



@RunWith(SpringRunner.class)
@SpringBootTest
public class test {
	@Autowired
	private StudentRepository student_dao;
	@Autowired
	private ClassesRepository class_dao;
	@Autowired
	private SchoolRepository school_dao;
	@Autowired
	private ScreeningWearRepository screening_wear_dao;
	
	
	@Test
	public void test() throws Exception{
		File file = new File("F:/lol");
		System.out.println(file.isDirectory());
	}
	
	
}
