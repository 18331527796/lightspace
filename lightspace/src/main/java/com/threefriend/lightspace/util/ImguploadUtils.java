package com.threefriend.lightspace.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.springframework.web.multipart.MultipartFile;

import com.threefriend.lightspace.enums.UrlEnums;

import net.coobird.thumbnailator.Thumbnails;

public class ImguploadUtils {

	public static String uploadImg(MultipartFile[] multipartFile,String pathname) {
		StringBuilder str = new StringBuilder();
		int i =1;
		for (MultipartFile file : multipartFile) {
			
	        File targetFile=null; 
	        String fileName=file.getOriginalFilename();//获取文件名加后缀
	        //先判断文件是否存在
	        SimpleDateFormat sdf = new SimpleDateFormat("MMdd");
	        String fileAdd = sdf.format(new Date());
	        
	        if(fileName!=null&&fileName!=""){
	        	String path = UrlEnums.TOMCAT_IMG.getUrl()+"\\"+pathname; //文件存储位置  线上
	        	//String path = "F:/"+pathname; //文件存储位置  线下
	            fileName=new Date().getTime()+"_"+new Random().nextInt(100)+".jpg";//新的文件名
	 
	            //获取文件夹路径
	            File file2 =new File(path);
	            //如果文件夹不存在则创建
	            if(!file2 .exists()  && !file2 .isDirectory()){
	            	file2 .mkdir();
	            }
	            
	            //获取文件夹路径
	            File file1 =new File(path+"/"+fileAdd);
	            //如果文件夹不存在则创建
	            if(!file1 .exists()  && !file1 .isDirectory()){
	                file1 .mkdir();
	            }
	            //将图片存入文件夹
	            targetFile = new File(file1, fileName);
	            try {
	                //将上传的文件写到服务器上指定的文件。
	                file.transferTo(targetFile);
	                Thumbnails.of(path+"\\"+fileAdd+"\\"+fileName) 
	                .scale(1f) 
	                .outputQuality(0.25f) 
	                .toFile(path+"/"+fileAdd+"/"+fileName);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	        if(i==1) {
	        	str.append(pathname+"/"+fileAdd+"/"+fileName);
	        	i=2;
	        }else {
	        	str.append(","+pathname+"/"+fileAdd+"/"+fileName);
	        }
		}
		return str.toString();
	}
	
	public static String uploadImg(MultipartFile file,String pathname) {
        File targetFile=null; 
        String fileName=file.getOriginalFilename();//获取文件名加后缀
        //先判断文件是否存在
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String fileAdd = sdf.format(new Date());
        
        if(fileName!=null&&fileName!=""){
        	String path = UrlEnums.TOMCAT_IMG.getUrl()+"\\"+pathname; //文件存储位置  线上
        	//String path = "F:/"+pathname; //文件存储位置  线下
            String fileF = fileName.substring(fileName.lastIndexOf("."), fileName.length());//文件后缀
            fileName=new Date().getTime()+"_"+new Random().nextInt(1000)+fileF;//新的文件名
            //获取文件夹路径
            File file1 =new File(path);
            //如果文件夹不存在则创建
            if(!file1 .exists()  && !file1 .isDirectory()){
                file1 .mkdir();
            }
            
            //获取文件夹路径
            File file2 =new File(path+"/"+fileAdd);
            //如果文件夹不存在则创建
            if(!file2 .exists()  && !file2 .isDirectory()){
            	file2 .mkdir();
            }
            //将图片存入文件夹
            targetFile = new File(file2, fileName);
            try {
                //将上传的文件写到服务器上指定的文件。
                file.transferTo(targetFile);
                Thumbnails.of(path+"\\"+fileAdd+"\\"+fileName) 
                .scale(1f) 
                .outputQuality(0.25f) 
                .toFile(path+"/"+fileAdd+"/"+fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
		return pathname+"/"+fileAdd+"/"+fileName;
	}
}
