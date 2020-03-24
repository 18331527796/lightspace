package com.threefriend.lightspace;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.threefriend.lightspace.enums.UrlEnums;
import com.threefriend.lightspace.enums.VisionEnums;
import com.threefriend.lightspace.mapper.RegionMapper;
import com.threefriend.lightspace.repository.RecordRepository;
import com.threefriend.lightspace.repository.RegionRepository;
import com.threefriend.lightspace.xcxutil.CreateQrcore;
import com.threefriend.lightspace.xcxutil.WaterMarkUtils;
import com.threefriend.lightspace.xcxutil.ZipUtils;



@RunWith(SpringRunner.class)
@SpringBootTest
public class test {

	@Autowired
	private RecordRepository record_dao;
	
	
	
	@Test
	public void test() throws ParseException {
		try {
        	//生成token
            String accessToken = CreateQrcore.getToken();
            System.out.println("accessToken;" + accessToken);
            String ids = "437";
            String path = UrlEnums.CODE_PATH.getUrl();
            System.err.println(path);
            //生成二维码
            CreateQrcore.postMiniqrQr(ids, accessToken, path);
            //添加图片
            WaterMarkUtils.graphicsGeneration(ids,"吴禹霏", path);
            //创建压缩包
            ZipUtils.fileToZip(path, path, "code");
        } catch (Exception e) {
        	System.err.println("二维码生成失败");
        }
	}
	
}
