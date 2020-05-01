package com.threefriend.lightspace;



import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.threefriend.lightspace.enums.AccountEnums;
import com.threefriend.lightspace.mapper.StudentMapper;
import com.threefriend.lightspace.mapper.xcx.ScreeningMapper;
import com.threefriend.lightspace.repository.ScreeningRepository;
import com.threefriend.lightspace.repository.StudentRepository;
import com.threefriend.lightspace.service.Impl.xcx.ParentXcxServiceImpl;
import com.threefriend.lightspace.util.ExcelUtil;
import com.threefriend.lightspace.util.xcx.HttpClientUtils;
import com.threefriend.lightspace.util.xcx.SendMessageUtils;
import com.threefriend.lightspace.util.xcx.WeChatUtils;

import net.coobird.thumbnailator.Thumbnails;



@RunWith(SpringRunner.class)
@SpringBootTest
public class test {
	@Autowired
	private ScreeningRepository screening_dao;
	
	
	@Test
	public void test() throws Exception{
		Map<String, List<String>> map = getMember();
		 String[] strArray = { "学校名称", "班级名称", "学生姓名", "右眼裸眼视力", "左眼裸眼视力" };
		ExcelUtil.createExcel(map, strArray);
	}
	
	public Map<String, List<String>> getMember() {
        Map<String, List<String>> map = new HashMap<String, List<String>>();

        try {
        	List<ScreeningMapper> list = screening_dao.findBySchoolIdOrderByGenTimeDesc(50);

            for (int i = 0; i < list.size(); i++) {
                ArrayList<String> members = new ArrayList<String>();
                members.add(list.get(i).getSchoolName() + "");
                members.add(list.get(i).getClassName());
                members.add(list.get(i).getStudentName());
                members.add(list.get(i).getVisionRight());
                members.add(list.get(i).getVisionLeft());
                map.put(list.get(i).getId() + "", members);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
	
}
