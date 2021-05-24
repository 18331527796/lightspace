package com.threefriend.lightspace;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.threefriend.lightspace.mapper.schoolclient.SchoolSemesterMapper;
import com.threefriend.lightspace.mapper.schoolclient.SchoolStudentRecordMapper;
import com.threefriend.lightspace.mapper.xcx.ScreeningMapper;
import com.threefriend.lightspace.repository.ClassesRepository;
import com.threefriend.lightspace.repository.ScreeningRepository;
import com.threefriend.lightspace.repository.schoolclient.SchoolSemesterRepository;
import com.threefriend.lightspace.repository.schoolclient.SchoolStudentRecordRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
public class test2 {
	
	private final String[] Array = { "所在班级", "学生姓名", "右眼第一次", "左眼第一次", "右眼最后一次", "左眼最后一次" };
	@Autowired
	private SchoolStudentRecordRepository school_student_record_dao;
	@Autowired
	private SchoolSemesterRepository school_semester_dao;
	@Autowired
	private ScreeningRepository screening_dao;
	@Autowired
	private ClassesRepository classes_dao;
	
	@Test
	public void test2(){
		String className = "";
		Integer classId = 0 ;
		
		SchoolSemesterMapper SemesterPo = school_semester_dao.findByYearAndSemesterAndSchoolId("2020 - 2021",3,51);
		
		
		List<SchoolStudentRecordMapper> student = school_student_record_dao.findBySchoolIdAndSemester(51,SemesterPo.getId());
		
		System.out.println(student.size());
		
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		for (SchoolStudentRecordMapper studentMapper : student) {
			if(classId != studentMapper.getClassId()) {
				classId = studentMapper.getClassId();
				className = classes_dao.findById(classId).get().getClassName();
			}
			
			if(map.get(studentMapper.getId()+"")!=null)continue;
			List<ScreeningMapper> list = screening_dao.findByStudentIdOrderByGenTimeDesc(studentMapper.getStudentId());
			ArrayList<String> members = new ArrayList<String>();
			members.add(className);
			members.add(studentMapper.getName());
			members.add(list.size()<1?"暂无数据":list.get(list.size()-1).getVisionRightStr()+"");
			members.add(list.size()<1?"暂无数据":list.get(list.size()-1).getVisionLeftStr()+"");
			members.add(list.size()<1?"暂无数据":list.get(0).getVisionRightStr()+"");
			members.add(list.size()<1?"暂无数据":list.get(0).getVisionLeftStr()+"");
			map.put(studentMapper.getId() + "", members);
		}
		createExcel(map, Array);
		
	}
	
    public synchronized void createExcel(Map<String, List<String>> map, String[] Array) {
    	
        // 第一步，创建一个webbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet("sheet1");
        sheet.setDefaultColumnWidth(20);// 默认列宽
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        HSSFRow row = sheet.createRow((int) 0);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        // 创建一个居中格式
        style.setAlignment(HorizontalAlignment.CENTER);

        // 添加excel title
        HSSFCell cell = null;
        for (int i = 0; i < Array.length; i++) {
            cell = row.createCell((short) i);
            cell.setCellValue(Array[i]);
            cell.setCellStyle(style);
        }
        //添加颜色
        CellStyle color = wb.createCellStyle();
    	//关键点 IndexedColors.AQUA.getIndex() 对应颜色
        color.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        color.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.index);

        // 第五步，写入实体数据 实际应用中这些数据从数据库得到,list中字符串的顺序必须和数组strArray中的顺序一致
        int i = 0;
        for (String str : map.keySet()) {
            row = sheet.createRow((int) i + 1);
            List<String> list = map.get(str);
            // 第四步，创建单元格，并设置值
            for (int j = 0; j < Array.length; j++) {
        		row.createCell((short) j).setCellValue(list.get(j));
        		if(list.get(2).equals("暂无数据"))continue;
        		//散光低于100度 进入判断否则标红
        		if((Double.valueOf(list.get(2))<Double.valueOf(list.get(4)))||(Double.valueOf(list.get(3))<Double.valueOf(list.get(5)))) {
        			row.getCell(j).setCellStyle(color);
        		}	
            }

            // 第六步，将文件存到指定位置
            try (FileOutputStream fout = new FileOutputStream("F:\\Members.xls")){
                wb.write(fout);
                wb.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            i++;
        }
    }
}
