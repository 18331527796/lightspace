package com.threefriend.lightspace.util;


import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;

import com.threefriend.lightspace.enums.UrlEnums;

public class ExcelUtil {
    /**
     * @功能：手工构建一个简单格式的Excel
     */
    public synchronized static void createExcel(Map<String, List<String>> map, String[] strArray) {
    	
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
        for (int i = 0; i < strArray.length; i++) {
            cell = row.createCell((short) i);
            cell.setCellValue(strArray[i]);
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
            for (int j = 0; j < strArray.length; j++) {
        		row.createCell((short) j).setCellValue(list.get(j));
        		if(list.get(5).equals("暂无数据"))continue;
        		//散光低于100度 进入判断否则标红
        		if(list.get(5).indexOf("<")==-1&&list.get(6).indexOf("<")==-1&&Double.valueOf(list.get(5))>=-1.5d&&Double.valueOf(list.get(6))>=-1.5d) {
        			//判断是不是近视
        			if(list.get(3).indexOf("-")!=-1||list.get(4).indexOf("-")!=-1) {
        				//是近视 大于50度标红
        				if(Double.valueOf(list.get(3))<-0.5d||Double.valueOf(list.get(4))<-0.5d) row.getCell(j).setCellStyle(color);
        			}else {
        				//是远视大于150度标红
        				if(Double.valueOf(list.get(3))>1.5d||Double.valueOf(list.get(4))>1.5d) row.getCell(j).setCellStyle(color);
        			}
        		}else {
        			row.getCell(j).setCellStyle(color);
        		}
            }

            // 第六步，将文件存到指定位置
            try (FileOutputStream fout = new FileOutputStream(UrlEnums.FILE_PATH.getUrl()+"Members.xls")){
                wb.write(fout);
                wb.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            i++;
        }
    }
}
