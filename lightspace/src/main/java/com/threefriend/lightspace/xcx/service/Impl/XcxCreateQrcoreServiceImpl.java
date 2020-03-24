package com.threefriend.lightspace.xcx.service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.threefriend.lightspace.enums.UrlEnums;
import com.threefriend.lightspace.mapper.StudentMapper;
import com.threefriend.lightspace.repository.ClassesRepository;
import com.threefriend.lightspace.repository.SchoolRepository;
import com.threefriend.lightspace.repository.StudentRepository;
import com.threefriend.lightspace.xcx.service.XcxCreateQrcoreService;
import com.threefriend.lightspace.xcxutil.CreateQrcore;
import com.threefriend.lightspace.xcxutil.WaterMarkUtils;
import com.threefriend.lightspace.xcxutil.ZipUtils;


@Service
public class XcxCreateQrcoreServiceImpl implements XcxCreateQrcoreService{

	@Autowired
	private StudentRepository student_dao;
	
	
	@Override
	public void download(HttpServletResponse response, Map<String, String> params) {
		try {
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
            String accessToken = CreateQrcore.getToken();
            System.out.println("accessToken;" + accessToken);
            String path = UrlEnums.CODE_PATH.getUrl();
            System.out.println(path);
            for (StudentMapper student : ids) {
            	String studentId = student.getId().toString();
            	//生成二维码
            	CreateQrcore.postMiniqrQr(studentId, accessToken, path);
            	//添加图片
            	WaterMarkUtils.graphicsGeneration(studentId,student.getName(), path);
			}
            //创建压缩包
            ZipUtils.fileToZip(path, path, "code");
            //下载压缩包
            ZipUtils.downLoadZip(path, response);
        } catch (Exception e) {
        	System.err.println("二维码生成失败");
        }
		
	}

}
