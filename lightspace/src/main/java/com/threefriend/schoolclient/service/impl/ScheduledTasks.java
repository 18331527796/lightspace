package com.threefriend.schoolclient.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.threefriend.lightspace.mapper.ClassesMapper;
import com.threefriend.lightspace.mapper.StudentMapper;
import com.threefriend.lightspace.repository.ClassesRepository;
import com.threefriend.lightspace.repository.StudentRepository;

/**
 * 定时器任务 每年的 9/1 自动升年级
 * @author Administrator
 *
 */
@Component
public class ScheduledTasks {
	
	@Autowired
	private ClassesRepository classes_dao;
	@Autowired
	private StudentRepository student_dao;

    //输出时间格式
    private static final SimpleDateFormat format = new SimpleDateFormat("HH(hh):mm:ss S");

    //表达式 每年的9/1日 00：00：00 执行此任务
    @Scheduled(cron="0 0 0 1 9 ?")
    public void firstScheduledTasks(){
        System.out.println("定时任务执行，现在时间是 : "+format.format(new Date())+"进行升年级操作");
        
        
    		List<ClassesMapper> findAll = classes_dao.findAll();
    		for (ClassesMapper cpo : findAll) {
    			if(cpo.getClassName().contains("社会")||cpo.getClassName().contains("已毕业")) {
    				System.out.println(cpo.getClassName()+"跳过");
    				continue;
    			}
    			String name = equalsClass(cpo.getClassName());
    			if(name.contains("已毕业")) {
    				cpo.setFinish(1);
    			}
    			cpo.setClassName(name);
    			cpo.setGrade(cpo.getGrade()+1);
    			cpo.setClassNumber(cpo.getClassNumber()+1);
    			List<StudentMapper> student = student_dao.findByClassesId(cpo.getId());
    			for (StudentMapper spo : student) {
    				spo.setClassesName(cpo.getClassName());
    			}
    			student_dao.saveAll(student);
    		}
    		classes_dao.saveAll(findAll);

    	
    }
    
    
    private String equalsClass(String name) {
		if(name.contains("(")) return name;
		String str = name.substring(0, 1);
		String end = name.substring(1);
		switch (str) {
		case "一":
			str="二";
			break;
		case "二":
			str="三";
			break;
		case "三":
			str="四";	
			break;
		case "四":
			str="五";		
			break;
		case "五":
			str="六";			
			break;
		default:
			str=name+"(已毕业)";
			break;
		}
		if(str.contains("("))return str;
		return str+end;
	}
}