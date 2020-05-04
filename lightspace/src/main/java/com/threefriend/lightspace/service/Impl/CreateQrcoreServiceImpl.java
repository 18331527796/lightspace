package com.threefriend.lightspace.service.Impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.threefriend.lightspace.enums.AccountEnums;
import com.threefriend.lightspace.enums.UrlEnums;
import com.threefriend.lightspace.mapper.StudentMapper;
import com.threefriend.lightspace.repository.ClassesRepository;
import com.threefriend.lightspace.repository.SchoolRepository;
import com.threefriend.lightspace.repository.StudentRepository;
import com.threefriend.lightspace.service.CreateQrcoreService;
import com.threefriend.lightspace.util.CreateQrcore;
import com.threefriend.lightspace.util.DrowMailUtils;
import com.threefriend.lightspace.util.RedisUtils;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.util.WaterMarkUtils;
import com.threefriend.lightspace.util.ZipUtils;
import com.threefriend.lightspace.vo.ResultVO;


@Service
public class CreateQrcoreServiceImpl implements CreateQrcoreService{

	@Autowired
	private StudentRepository student_dao;
	@Resource
	private RedisUtils redisUtil;
	
	@Override
	public ResultVO download(HttpServletResponse response, Map<String, String> params) {
		String path = UrlEnums.CODE_PATH.getUrl();
		try {
			File file = new File(path);
			ZipUtils.deleteDirectory(file);
			String type = params.get("type");
			Integer id = Integer.valueOf(params.get("id"));
			List<StudentMapper> ids = new ArrayList<>();
			if(type.equals("student")) {
				StudentMapper studentMapper = student_dao.findById(id).get();
				ids.add(studentMapper);
			}else if(type.equals("class")) {
				ids=student_dao.findByClassesId(id);
			}else if(type.equals("school")) {
				ids=student_dao.findBySchoolId(id);
			}
			//生成token
			String accessToken = redisUtil.get("XCXTOKEN");
			if (accessToken == null || "".equals(accessToken)) {
				accessToken = CreateQrcore.getToken();
				redisUtil.setValueTime("XCXTOKEN", accessToken, 7000);
			}
            System.out.println(path);
            for (StudentMapper student : ids) {
            	String studentId = student.getId().toString();
            	//生成二维码
            	String imgcode = CreateQrcore.postMiniqrQr(studentId,student.getName(), accessToken, path);
            	//添加图片
            	//String imgcode = WaterMarkUtils.graphicsGeneration(studentId,student.getName(), path);
            	//生成一封信
            	DrowMailUtils.graphicsGeneration(UrlEnums.TOMCAT_IMG+"\\光亮空间logo.png", imgcode, "", imgcode,student.getName());
			}
            //创建压缩包
            ZipUtils.fileToZip(path, path, "code");
        } catch (Exception e) {
        	System.err.println("二维码生成失败");
        }
		return ResultVOUtil.success();
	}

}