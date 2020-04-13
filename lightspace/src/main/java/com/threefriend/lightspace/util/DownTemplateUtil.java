package com.threefriend.lightspace.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * 下载模板工具类
 */
public class DownTemplateUtil {

	public static void downTemplate(HttpServletResponse response,String path,String fileName) {
		InputStream inputStream=null;
	    try {
	        response.reset();
	        response.setContentType("application/vnd.ms-excel");
	        response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes(), "UTF-8"));
	        response.setCharacterEncoding("utf-8");
	       ServletOutputStream outputStream = response.getOutputStream();

	        inputStream= new FileInputStream(new File(path));
	        byte [] buff=new byte[1024];
	        int length=0;
	        while((length=inputStream.read(buff))!=-1){
	            outputStream.write(buff, 0, length);
	        }
	        if(outputStream!=null){
	            outputStream.flush();
	            outputStream.close();
	        }
	    }catch (Exception e){
	        e.printStackTrace();
	    }finally {
	        if(inputStream!=null){

	            try {
	                inputStream.close();
	            } catch (IOException e) {
	                System.out.println("关闭资源出错");
	                e.printStackTrace();
	            }
	        }
	    }
	}
}
