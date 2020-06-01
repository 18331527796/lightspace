package com.threefriend.lightspace.service.Impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.threefriend.lightspace.enums.UrlEnums;
import com.threefriend.lightspace.mapper.StudentMapper;
import com.threefriend.lightspace.repository.ClassesRepository;
import com.threefriend.lightspace.repository.SchoolRepository;
import com.threefriend.lightspace.repository.StudentRepository;
import com.threefriend.lightspace.service.CreateQrcoreService;
import com.threefriend.lightspace.util.CompactAlgorithm;
import com.threefriend.lightspace.util.CreateQrcore;
import com.threefriend.lightspace.util.DrowMailUtils;
import com.threefriend.lightspace.util.RedisUtils;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.util.WXPayUtil;
import com.threefriend.lightspace.util.ZipUtils;
import com.threefriend.lightspace.vo.ResultVO;


@Service
public class CreateQrcoreServiceImpl implements CreateQrcoreService{

	@Autowired
	private StudentRepository student_dao;
	@Autowired
	private ClassesRepository class_dao;
	@Autowired
	private SchoolRepository school_dao;
	@Resource
	private RedisUtils redisUtil;
	
	@Override
	public synchronized ResultVO download(HttpServletResponse response, Map<String, String> params) {
		String createCode = WXPayUtil.createCode(8);
		String path = UrlEnums.CODE_PATH.getUrl()+createCode+"/";
		String zipName = "code";
		try {
			//File file = new File(path);
			//ZipUtils.deleteDirectory(file);
			String type = params.get("type");
			Integer id = Integer.valueOf(params.get("id"));
			List<StudentMapper> ids = new ArrayList<>();
			if(type.equals("student")) {
				StudentMapper studentMapper = student_dao.findById(id).get();
				zipName = studentMapper.getName();
				ids.add(studentMapper);
			}else if(type.equals("class")) {
				ids=student_dao.findByClassesId(id);
				zipName = class_dao.findById(id).get().getClassName();
			}else if(type.equals("school")) {
				ids=student_dao.findBySchoolId(id);
				zipName = school_dao.findById(id).get().getName();
			}
			//生成token
			String accessToken = redisUtil.get("XCXTOKEN");
			if (accessToken == null || "".equals(accessToken)) {
				accessToken = CreateQrcore.getToken();
				redisUtil.setValueTime("XCXTOKEN", accessToken, 7000);
			}
            /*for (StudentMapper student : ids) {
            	String studentId = student.getId().toString();
            	//生成二维码
            	String imgcode = CreateQrcore.postMiniqrQr(studentId,student.getName(), accessToken, path);
            	//添加图片
            	//String imgcode = WaterMarkUtils.graphicsGeneration(studentId,student.getName(), path);
            	//生成一封信
            	DrowMailUtils.graphicsGeneration(UrlEnums.TOMCAT_IMG+"\\光亮空间logo.png", imgcode, "", imgcode,student.getName());
			}*/
            //开启线程池 生成一封信
            long start = System.currentTimeMillis();
    		ExecutorService executor = Executors.newFixedThreadPool(5);
    		int size = ids.size();
			int batch = size % 20 == 0 ? size / 20 : size / 20 + 1;
			for (int j=0; j<batch; j++) {
				int end = (j+1)*20;
				if (end > size) {
					end = size;
					}
				List<StudentMapper> newList = ids.subList(j*20, end);
				studentText test = new studentText(newList,accessToken,path,type);
				executor.execute(test);
			}
    		executor.shutdown();
    		while (true) {
    			if (executor.isTerminated()) {
    				break;
    			}
    		}
			long date = System.currentTimeMillis() - start;
			System.out.println("===========================打包完毕===========================");
			System.out.println("===========================总共用时===========================");
			System.out.println("===========================" + date/1000 + "===========================");
            //创建压缩包
            //ZipUtils.fileToZip(path, path, zipName);
			File f = new File(path);
			new CompactAlgorithm(new File( UrlEnums.CODE_PATH.getUrl()+zipName+".zip")).zipFiles(f);
        } catch (Exception e) {
        	System.err.println("二维码生成失败");
        }
		return ResultVOUtil.success("https://www.guangliangkongjian.com/code/"+zipName+".zip");
	}
	
	/**
	 * 线程内部类
	 * @author Administrator
	 *
	 */
	class  studentText implements Runnable{
        private List<StudentMapper> student;
        private String token ;
        private String path ; 
        private String type ;
        public studentText(List<StudentMapper> student,String token ,String path,String type) {
        	this.student = student;
        	this.token = token;
        	this.path = path ;
        	this.type = type;
        }
        public void run()
        {for (StudentMapper studentMapper : student) {
        	//生成二维码
        	String imgcode = CreateQrcore.postMiniqrQr(studentMapper.getId()+"",studentMapper.getName(), token, path,type,studentMapper.getClassesName());
        	//生成一封信
        	DrowMailUtils.graphicsGeneration("", imgcode, "", imgcode,studentMapper.getName());
        }

        }
	}

}
