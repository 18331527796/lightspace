package com.threefriend.lightspace;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.threefriend.lightspace.repository.ScreeningRepository;
import com.threefriend.lightspace.repository.SortRepository;
import com.threefriend.lightspace.repository.StudentRepository;




@RunWith(SpringRunner.class)
@SpringBootTest
public class test {
	
	
	@SuppressWarnings("deprecation")
	@Test
	public void test(){
		
        
		String filePath = "C:/Users/Administrator/Desktop/主播礼物表/用来统计主播工资的word.docx";
        String content = readWord(filePath);
        String[] split = content.split("\n");
        
        String []player =new String[] {"团团","晚安","兰兰","冉冉","香菜","阿柚","雪儿","大三","萌幼","芯儿","水蜜桃","妮妮","共枕",
        		"伊人","萌崽","小鹿","媛媛","米米","苏苏","御可","美之心","曦曦","爽爽","卿","米朵儿","婉妍","熙迟","久奈","林夕","啵啵",
        		"闲闲","盲盒"};
        
        Map<String, List<String>> map = new LinkedHashMap<>();
        for (String string : player) {
        	map.put(string, new ArrayList<>());
		}
        
        for (String string : split) {
        	string=string.replaceAll(" ", "").replaceAll("。", "").replaceAll("，", "");
        	System.out.println("----");
        	System.out.println(string);
        	if(string.indexOf("盲盒")!=-1) {
        		string=string.replaceAll("盲盒", "");
        		map.get("盲盒").add(string.replaceAll("[\\u4e00-\\u9fa5]", ""));
        	}
        	String a = string.replaceAll("[\\u4e00-\\u9fa5]", ""); 
            String b = string.replaceAll("[0-9]", "");
            System.out.println("输出数字："+a);
            System.out.println("输出文字："+b);
            map.get(b).add(a);
        }
        
        createExcel(map);

	}
	public static String readWord(String path) {
		String buffer = "";
		try {
			if (path.endsWith(".doc")) {
				InputStream is = new FileInputStream(new File(path));
				WordExtractor ex = new WordExtractor(is);
				buffer = ex.getText();
				ex.close();
			} else if (path.endsWith("docx")) {
				OPCPackage opcPackage = POIXMLDocument.openPackage(path);
				POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);
				buffer = extractor.getText();
				extractor.close();
			} else {
				System.out.println("此文件不是word文件！");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return buffer;
	}
	
	
public synchronized static void createExcel(Map<String, List<String>> map) {
    	
        // 第一步，创建一个webbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet("sheet1");
        sheet.setDefaultColumnWidth(8);// 默认列宽
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        HSSFRow row = sheet.createRow((int) 0);

        // 第五步，写入实体数据 实际应用中这些数据从数据库得到,list中字符串的顺序必须和数组strArray中的顺序一致
        int i = 0, sumrow = 0;
        for (String str : map.keySet()) {
        	if(map.get(str).size()>sumrow) sumrow=map.get(str).size()+2;
        }
        
        Calendar ca =Calendar.getInstance();
        int k = ca.get(Calendar.DATE)-1;
        
        for (String str : map.keySet()) {
        	System.out.println("--------"+str);
        	Integer sum = 0;
            row = sheet.createRow(i);
            List<String> list = map.get(str);
            row.createCell(0).setCellValue(str);
            // 第四步，创建单元格，并设置值
            for (int j = 1; j < map.get(str).size()+1; j++) {
            	row.createCell((short) j).setCellValue(list.get(j-1));
            	sum+=Integer.valueOf(list.get(j-1));
    		}
            row.createCell(sumrow).setCellValue(sum);
            row.createCell(sumrow+1).setCellValue(Double.valueOf(sum*0.05));
	        // 第六步，将文件存到指定位置
	        try (FileOutputStream fout = new FileOutputStream("C:/Users/Administrator/Desktop/"+k+"号.xls")){
	            wb.write(fout);
	            wb.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        i++;
        }
    }

	public void initTimer() {
		Timer t = new Timer();// 创建Timer对象
		TimerTask tt = new TimerTask() {
			 public void run() {
				 System.out.println("我就是想试试这个功能");
			 }
		};//创建TimerTask对象，Timer对象会调用TimerTask的run()方法
		while (true) {// 这个是用来停止此任务的,否则就一直循环执行此任务了
		try {
  
		 int ch;
			ch = System.in.read();
			if (ch - 'c' == 0) {
				t.cancel();// 使用这个方法退出任务
			}
			t.schedule(tt,1000,3000) ;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
 }
	
	
}
