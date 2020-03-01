package com.threefriend.lightspace.service.Impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableIterator;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.threefriend.lightspace.mapper.StudentWordMapper;
import com.threefriend.lightspace.repository.StudentWordRepository;

import org.apache.poi.hwpf.usermodel.TableCell;  
import org.apache.poi.hwpf.usermodel.TableRow;

@Service
public class ReadStudentWord {
	
	@Autowired
	private StudentWordRepository studentword_dao;
	
	
	public void readStudentWord(MultipartFile file,Integer studentId) throws Exception {
        try{  
        	// 获取文件名
            String fileName = file.getOriginalFilename();
            // 获取文件后缀
            String prefix=fileName.substring(fileName.lastIndexOf("."));
            // 生成临时文件
            final File excelFile = File.createTempFile(new Date().getTime()+"", prefix);
            // MultipartFile to File
            file.transferTo(excelFile);
            //↑↑↑↑↑以上是为了转换格式↑↑↑↑↑
            
            FileInputStream in = new FileInputStream(excelFile);//载入文档  
            POIFSFileSystem pfs = new POIFSFileSystem(in);     
            HWPFDocument hwpf = new HWPFDocument(pfs);     
            Range range = hwpf.getRange();//得到文档的读取范围  
            TableIterator it = new TableIterator(range);  
            //新建记录
            StudentWordMapper po = new StudentWordMapper();
            po.setStudentId(studentId);
            po.setGenTime(new Date());
           //迭代文档中的表格  
            while (it.hasNext()) {     
                Table tb = (Table) it.next();     
                //迭代行，默认从0开始  
                for (int i = 0; i < tb.numRows(); i++) {
                	if(i!=1&&i!=9) {
	                    TableRow tr = tb.getRow(i);     
	                    //迭代列，默认从0开始  
	                    for (int j = 0; j < tr.numCells(); j++) {  
	                        TableCell td = tr.getCell(j);//取得单元格  
	                        switch (j) {
							case 0:
								if(i==0) {
									//取得单元格的内容  
									//姓名
									for(int k=0;k<td.numParagraphs();k++){     
										Paragraph para =td.getParagraph(k);     
										String[] s = para.text().split("：");  
										po.setName(s[1].trim());
									} //end for   
								}
								
								
								
								break;
							case 1:
								if(i==0) {
									//取得单元格的内容  
									//性别
									for(int k=0;k<td.numParagraphs();k++){     
										Paragraph para =td.getParagraph(k);     
										String[] s = para.text().split("：");  
										po.setGender(s[1].trim());
									} //end for  
								}
								
								if(i==4) {
									//取得单元格的内容  
									//眼球运动
									for(int k=0;k<td.numParagraphs();k++){     
										Paragraph para =td.getParagraph(k);     
										String[] s = para.text().split("：");  
										po.setMotion(s[1].trim());
									} //end for   
								}
								
								if(i==5) {
									//取得单元格的内容  
									//遮盖眼位
									for(int k=0;k<td.numParagraphs();k++){     
										Paragraph para =td.getParagraph(k);     
										String[] s = para.text().split("："); 
										po.setCover(s[1].trim());
									} //end for   
								}
								
								if(i==6) {
									//取得单元格的内容  
									//集合近点
									for(int k=0;k<td.numParagraphs();k++){     
										Paragraph para =td.getParagraph(k);     
										String[] s = para.text().split("：");  
										po.setAssembly(s[1].trim());
									} //end for   
								}
								
								if(i==7) {
									//取得单元格的内容  
									//色觉检查
									for(int k=0;k<td.numParagraphs();k++){     
										Paragraph para =td.getParagraph(k);     
										String[] s = para.text().split("：");  
										po.setColourVision(s[1].trim());
									} //end for   
								}
								
								if(i==12) {
									//取得单元格的内容  
									//裂隙灯检查
									for(int k=0;k<td.numParagraphs();k++){     
										Paragraph para =td.getParagraph(k);     
										String[] s = para.text().split("："); 
										po.setSlitLamp(s[1].trim());
									} //end for   
								}
								
								if(i==13) {
									//取得单元格的内容  
									//眼底照相检查
									for(int k=0;k<td.numParagraphs();k++){     
										Paragraph para =td.getParagraph(k);     
										String[] s = para.text().split("：");   
										po.setRetCam(s[1].trim());
									} //end for   
								}
								
								if(i==14) {
									//取得单元格的内容  
									//身高
									for(int k=0;k<td.numParagraphs();k++){     
										Paragraph para =td.getParagraph(k);     
										String[] s = para.text().split("：");  
										po.setHeight(s[1].trim());
									} //end for   
								}
								
								if(i==15) {
									//取得单元格的内容  
									//体重
									for(int k=0;k<td.numParagraphs();k++){     
										Paragraph para =td.getParagraph(k);     
										String[] s = para.text().split("：");   
										po.setWeight(s[1].trim());
									} //end for   
								}
								
								if(i==16) {
									//取得单元格的内容  
									//处理建议
									for(int k=0;k<td.numParagraphs();k++){     
										Paragraph para =td.getParagraph(k);     
										String s = para.text(); 
										po.setSuggest(s.trim());
									} //end for   
								}
								
								break;
							case 2:
								if(i==0) {
									//取得单元格的内容  
									//生日
									for(int k=0;k<td.numParagraphs();k++){     
										Paragraph para =td.getParagraph(k);     
										String[] s = para.text().split("："); 
										po.setBirthday(s[1].trim());
									} //end for  
								}
								
								if(i==2) {
									//取得单元格的内容  
									//右远裸视
									for(int k=0;k<td.numParagraphs();k++){     
										Paragraph para =td.getParagraph(k);     
										String s = para.text();  
										po.setFarRight(s.trim());
									} //end for   
								}
								
								if(i==3) {
									//取得单元格的内容  
									//左远裸视
									for(int k=0;k<td.numParagraphs();k++){     
										Paragraph para =td.getParagraph(k);     
										String s = para.text();  
										po.setFarLeft(s.trim());
									} //end for   
								}
								
								if(i==4) {
									//取得单元格的内容  
									//立体视检查
									for(int k=0;k<td.numParagraphs();k++){     
										Paragraph para =td.getParagraph(k);     
										String[] s = para.text().split("：");   
										po.setStereopsis(s[1].trim());
									} //end for   
								}
								
								if(i==5) {
									//取得单元格的内容  
									//Worth-4点
									for(int k=0;k<td.numParagraphs();k++){     
										Paragraph para =td.getParagraph(k);     
										String[] s = para.text().split("：");   
										po.setWorth(s[1].trim());
									} //end for   
								}
								
								if(i==10) {
									//取得单元格的内容  
									//右水平曲率值
									for(int k=0;k<td.numParagraphs();k++){     
										Paragraph para =td.getParagraph(k);     
										String s = para.text();  
										po.setLevelRight(s.trim());
									} //end for   
								}
								
								if(i==11) {
									//取得单元格的内容  
									//左水平曲率值
									for(int k=0;k<td.numParagraphs();k++){     
										Paragraph para =td.getParagraph(k);     
										String s = para.text(); 
										po.setLevelLeft(s.trim());
									} //end for   
								}
								
								
								
								break;
							case 3:
								if(i==0) {
									//取得单元格的内容  
									//学校
									for(int k=0;k<td.numParagraphs();k++){     
										Paragraph para =td.getParagraph(k);     
										String[] s = para.text().split("："); 
										po.setSchool(s[1].trim());
									} //end for  
								}
								
								if(i==2) {
									//取得单元格的内容  
									//右近裸视
									for(int k=0;k<td.numParagraphs();k++){     
										Paragraph para =td.getParagraph(k);     
										String s = para.text();  
										po.setNearRight(s.trim());
									} //end for   
								}
								
								if(i==3) {
									//取得单元格的内容  
									//左近裸视
									for(int k=0;k<td.numParagraphs();k++){     
										Paragraph para =td.getParagraph(k);     
										String s = para.text();  
										po.setNearLeft(s.trim());
									} //end for   
								}
								
								
								if(i==6) {
									//取得单元格的内容  
									//右眼灵敏度
									for(int k=0;k<td.numParagraphs();k++){     
										Paragraph para =td.getParagraph(k);     
										String[] s = para.text().split("：");  
										po.setSplRight(s[1].trim());
									} //end for   
								}
								
								if(i==7) {
									//取得单元格的内容  
									//左眼灵敏度
									for(int k=0;k<td.numParagraphs();k++){     
										Paragraph para =td.getParagraph(k);     
										String[] s = para.text().split("："); 
										po.setSplLeft(s[1].trim());
									} //end for   
								}
								
								if(i==8) {
									//取得单元格的内容  
									//双眼灵敏度
									for(int k=0;k<td.numParagraphs();k++){     
										Paragraph para =td.getParagraph(k);     
										String[] s = para.text().split("："); 
										po.setSplBinoculus(s[1].trim());
									} //end for   
								}
								
								if(i==10) {
									//取得单元格的内容  
									//右垂直曲率值
									for(int k=0;k<td.numParagraphs();k++){     
										Paragraph para =td.getParagraph(k);     
										String s = para.text();   
										   po.setVerticalRight(s.trim());
									} //end for   
								}
								
								if(i==11) {
									//取得单元格的内容  
									//左垂直曲率值
									for(int k=0;k<td.numParagraphs();k++){     
										Paragraph para =td.getParagraph(k);     
										String s = para.text();  
										po.setVerticalLeft(s.trim());
									} //end for   
								}
								break;
							case 4:
								if(i==0) {
									//取得单元格的内容  
									//电话
									for(int k=0;k<td.numParagraphs();k++){     
										Paragraph para =td.getParagraph(k);     
										String[] s = para.text().split("：");  
										po.setPhone(s[1].trim());
									} //end for  
								}
								
								if(i==2) {
									//取得单元格的内容  
									//右球镜
									for(int k=0;k<td.numParagraphs();k++){     
										Paragraph para =td.getParagraph(k);     
										String s = para.text(); 
										po.setSphRight(s.trim());
									} //end for   
								}
								
								if(i==3) {
									//取得单元格的内容  
									//左球镜
									for(int k=0;k<td.numParagraphs();k++){     
										Paragraph para =td.getParagraph(k);     
										String s = para.text(); 
										po.setSphLeft(s.trim());
									} //end for   
								}
								
								if(i==10) {
									//取得单元格的内容  
									//右眼轴长度
									for(int k=0;k<td.numParagraphs();k++){     
										Paragraph para =td.getParagraph(k);     
										String s = para.text();  
										po.setAxialLengthRight(s.trim());
									} //end for   
								}
								
								if(i==11) {
									//取得单元格的内容  
									//左眼轴长度
									for(int k=0;k<td.numParagraphs();k++){     
										Paragraph para =td.getParagraph(k);     
										String s = para.text();
										po.setAxialLengthLeft(s.trim());
									} //end for   
								}
								
								break;
							case 5:
								if(i==2) {
									//取得单元格的内容  
									//右柱镜
									for(int k=0;k<td.numParagraphs();k++){     
										Paragraph para =td.getParagraph(k);     
										String s = para.text(); 
										po.setCytRight(s.trim());
									} //end for   
								}
								
								if(i==3) {
									//取得单元格的内容  
									//左柱镜
									for(int k=0;k<td.numParagraphs();k++){     
										Paragraph para =td.getParagraph(k);     
										String s = para.text(); 
										po.setCytLeft(s.trim());
									} //end for   
								}
								
								if(i==10) {
									//取得单元格的内容  
									//右前房深度
									for(int k=0;k<td.numParagraphs();k++){     
										Paragraph para =td.getParagraph(k);     
										String s = para.text();  
										po.setAcdRight(s.trim());
									} //end for   
								}
								
								if(i==11) {
									//取得单元格的内容  
									//左前房深度
									for(int k=0;k<td.numParagraphs();k++){     
										Paragraph para =td.getParagraph(k);     
										String s = para.text(); 
										po.setAcdLeft(s.trim());
									} //end for   
								}
								break;
							case 6:
								if(i==2) {
									//取得单元格的内容  
									//右轴位
									for(int k=0;k<td.numParagraphs();k++){     
										Paragraph para =td.getParagraph(k);     
										String s = para.text();
										po.setAxisRight(s.trim());
									} //end for   
								}
								
								if(i==3) {
									//取得单元格的内容  
									//左轴位
									for(int k=0;k<td.numParagraphs();k++){     
										Paragraph para =td.getParagraph(k);     
										String s = para.text();  
										po.setAxisLeft(s.trim());
									} //end for   
								}
								
								if(i==10) {
									//取得单元格的内容  
									//右晶体厚度
									for(int k=0;k<td.numParagraphs();k++){     
										Paragraph para =td.getParagraph(k);     
										String s = para.text(); 
										po.setLtRight(s.trim());
									} //end for   
								}
								
								if(i==11) {
									//取得单元格的内容  
									//左晶体厚度
									for(int k=0;k<td.numParagraphs();k++){     
										Paragraph para =td.getParagraph(k);     
										String s = para.text(); 
										po.setLtLeft(s.trim());
									} //end for   
								}
								break;
							case 7:
								if(i==2) {
									//取得单元格的内容  
									//右矫正视力
									for(int k=0;k<td.numParagraphs();k++){     
										Paragraph para =td.getParagraph(k);     
										String s = para.text(); 
										po.setCorrectRight(s.trim());
									} //end for   
								}
								
								if(i==3) {
									//取得单元格的内容  
									//左矫正视力
									for(int k=0;k<td.numParagraphs();k++){     
										Paragraph para =td.getParagraph(k);     
										String s = para.text();  
										po.setCorrectLeft(s.trim());
									} //end for   
								}
								break;
							case 8:
								if(i==2) {
									//取得单元格的内容  
									//右瞳距
									for(int k=0;k<td.numParagraphs();k++){     
										Paragraph para =td.getParagraph(k);     
										String s = para.text(); 
										po.setIpdRight(s.trim());
									} //end for   
								}
								
								if(i==3) {
									//取得单元格的内容  
									//左瞳距
									for(int k=0;k<td.numParagraphs();k++){     
										Paragraph para =td.getParagraph(k);     
										String s = para.text(); 
										po.setIpdLeft(s.trim());
									} //end for   
								}
								break;
							case 9:
								if(i==2) {
									//取得单元格的内容  
									//右主导眼
									for(int k=0;k<td.numParagraphs();k++){     
										Paragraph para =td.getParagraph(k);     
										String s = para.text(); 
										po.setLeadingRight(s.trim());
									} //end for   
								}
								
								if(i==3) {
									//取得单元格的内容  
									//左主导眼
									for(int k=0;k<td.numParagraphs();k++){     
										Paragraph para =td.getParagraph(k);     
										String s = para.text();  
										po.setLeadingLeft(s.trim());
									} //end for   
								}
								break;
							default:
								break;
							}
	                           
	                    }   //end for  
                	}    
                }   //end for  
            } //end while  
            studentword_dao.save(po);
            
           //程序结束时，删除临时文件
           deleteFile(excelFile);
        }catch(Exception e){  
            e.printStackTrace();  
        }  
    }//end method 

	
	 /**  
     * 删除  
     *   
     * @param files  
     */  
    private void deleteFile(File... files) {  
        for (File file : files) {  
            if (file.exists()) {  
                file.delete();  
            }  
        }
    }
}