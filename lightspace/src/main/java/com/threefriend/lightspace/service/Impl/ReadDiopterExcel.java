package com.threefriend.lightspace.service.Impl;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.threefriend.lightspace.mapper.ClassesMapper;
import com.threefriend.lightspace.mapper.DiopterMapper;
import com.threefriend.lightspace.mapper.RegionMapper;
import com.threefriend.lightspace.mapper.SchoolMapper;
import com.threefriend.lightspace.mapper.StudentMapper;
import com.threefriend.lightspace.repository.ClassesRepository;
import com.threefriend.lightspace.repository.DiopterRepository;
import com.threefriend.lightspace.repository.RegionRepository;
import com.threefriend.lightspace.repository.SchoolRepository;
import com.threefriend.lightspace.repository.StudentRepository;
import com.threefriend.lightspace.util.MyBeanUtils;

/**
 * 读取学生excel
 */
@Service
public class ReadDiopterExcel {
	@Autowired
	private StudentRepository student_dao;

	// 总行数
	private int totalRows = 0;
	// 总条数
	private int totalCells = 0;
	// 错误信息接收器
	private String errorMsg;

	// 构造方法
	public ReadDiopterExcel() {
	}

	// 获取总行数
	public int getTotalRows() {
		return totalRows;
	}

	// 获取总列数
	public int getTotalCells() {
		return totalCells;
	}

	// 获取错误信息
	public String getErrorInfo() {
		return errorMsg;
	}

	/**
	 * 读EXCEL文件，获取信息集合
	 * 
	 * @param fielName
	 * @return
	 */
	public List<DiopterMapper> getDiopterInfo(MultipartFile mFile) {
		String fileName = mFile.getOriginalFilename();// 获取文件名
		List<DiopterMapper> DiopterList = null;
		try {
			if (!validateExcel(fileName)) {// 验证文件名是否合格
				return null;
			}
			boolean isExcel2003 = true;// 根据文件名判断文件是2003版本还是2007版本
			if (isExcel2007(fileName)) {
				isExcel2003 = false;
			}
			DiopterList = createExcel(mFile.getInputStream(), isExcel2003);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return DiopterList;
	}

	/**
	 * 根据excel里面的内容读取客户信息
	 * 
	 * @param is输入流
	 * @param isExcel2003
	 *            excel是2003还是2007版本
	 * @return
	 * @throws IOException
	 */
	public List<DiopterMapper> createExcel(InputStream is, boolean isExcel2003) {
		List<DiopterMapper> DiopterList = null;
		try {
			Workbook wb = null;
			if (isExcel2003) {// 当excel是2003时,创建excel2003
				wb = new HSSFWorkbook(is);
			} else {// 当excel是2007时,创建excel2007
				wb = new XSSFWorkbook(is);
			}
			DiopterList = readDiopterValue(wb);// 读取Excel里面客户的信息
		} catch (IOException e) {
			e.printStackTrace();
		}
		return DiopterList;
	}

	/**
	 * 读取Excel里面客户的信息
	 * 
	 * @param wb
	 * @return
	 */
	private List<DiopterMapper> readDiopterValue(Workbook wb) {
		// 得到第一个shell
		Sheet sheet = wb.getSheetAt(0);
		// 得到Excel的行数
		this.totalRows = sheet.getPhysicalNumberOfRows();
		// 得到Excel的列数(前提是有行数)
		//if (totalRows > 1 && sheet.getRow(0) != null) {
		//	this.totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
		//}
		List<DiopterMapper> DiopterList = new ArrayList<DiopterMapper>();
		DiopterMapper Diopter = null;
		int diopterRows = 0;
		// 循环Excel行数
		for (int r = 0; r < totalRows; r++) {
			Row row = sheet.getRow(r);
			if (row == null) {
				continue;
			}
			//每6行是一个对象实例
			if(r%6==0) {
				System.out.println("创建实例");
				Diopter= new DiopterMapper();
				diopterRows = 1;
			}
			if(diopterRows==2) {
				//这里找瞳距
				Cell cell = row.getCell(0);
				if (null != cell) {
					cell.setCellType(Cell.CELL_TYPE_STRING);
					Diopter.setPd(cell.getStringCellValue());
				} else {
					System.out.println("瞳距是空的 读取不到");
				}
			}
			if(diopterRows==4) {
				//这里找左眼的数据
				// 循环Excel的列
				for (int c = 0; c < 11; c++) {
					Cell cell = row.getCell(c);
					if (null != cell) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						switch (c) {
						case 2:
							System.out.println(cell.getStringCellValue());
							String[] ds1 = cell.getStringCellValue().split(":");
							if(ds1.length<2) {
								System.out.println("左眼的球，找不到");
								Diopter.setDs1L("");
							}else {
								Diopter.setDs1L(ds1[1]);
							}
							
							break;
						case 3:// 班級名称
							System.out.println(cell.getStringCellValue());
							String[] dc1 = cell.getStringCellValue().split(":");
							if(dc1.length<2) {
								System.out.println("左眼的柱，找不到");
								Diopter.setDc1L("");
							}else {
								Diopter.setDc1L(dc1[1]);
							}
							break;
						case 4:// 姓名
							System.out.println(cell.getStringCellValue());
							String[] axis = cell.getStringCellValue().split(":");
							if(axis.length<2) {
								System.out.println("左眼的度，找不到");
								Diopter.setAxis1L("");
							}else {
								Diopter.setAxis1L(axis[1]);
							}
							break;
						case 9:
							System.out.println(cell.getStringCellValue());
							String[] gh = cell.getStringCellValue().split(":");
							Diopter.setGhL(gh[1]);
							break;
						case 10:// 姓名
							System.out.println(cell.getStringCellValue());
							String[] gv = cell.getStringCellValue().split(":");
							Diopter.setGvL(gv[1]);
							break;
						default:
							break;
						}
					} else {
						System.out.println("这个列是空的 读取不到");
					}
				}
			}
			if(diopterRows==6) {
				//这里找右眼的数据
				// 循环Excel的列
				for (int c = 0; c < 11; c++) {
					Cell cell = row.getCell(c);
					if (null != cell) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						switch (c) {
						case 2:
							System.out.println(cell.getStringCellValue());
							String[] ds1 = cell.getStringCellValue().split(":");
							if(ds1.length<2) {
								System.out.println("右眼的球，找不到");
								Diopter.setDs1R("");
							}else {
								Diopter.setDs1R(ds1[1]);
							}
							
							break;
						case 3:// 班級名称
							System.out.println(cell.getStringCellValue());
							String[] dc1 = cell.getStringCellValue().split(":");
							if(dc1.length<2) {
								System.out.println("右眼的柱，找不到");
								Diopter.setDc1R("");
							}else {
								Diopter.setDc1R(dc1[1]);
							}
							break;
						case 4:// 姓名
							System.out.println(cell.getStringCellValue());
							String[] axis = cell.getStringCellValue().split(":");
							if(axis.length<2) {
								System.out.println("右眼的度，找不到");
								Diopter.setAxis1R("");
							}else {
								Diopter.setAxis1R(axis[1]);
							}
							break;
						case 9:
							System.out.println(cell.getStringCellValue());
							String[] gh = cell.getStringCellValue().split(":");
							Diopter.setGhR(gh[1]);
							break;
						case 10:// 姓名
							System.out.println(cell.getStringCellValue());
							String[] gv = cell.getStringCellValue().split(":");
							Diopter.setGvR(gv[1]);
							break;
						default:
							break;
						}
					} else {
						System.out.println("这个列是空的 读取不到");
					}
				}
				Diopter.setDiopterLeft(Diopter.getDs1L()+Diopter.getDc1L()+(Diopter.getAxis1L()==null?"":"/"+Diopter.getAxis1L()));
				Diopter.setDiopterRight(Diopter.getDs1R()+Diopter.getDc1R()+(Diopter.getAxis1R()==null?"":"/"+Diopter.getAxis1R()));
				DiopterList.add(Diopter);
			}
			diopterRows++;
		}
		return DiopterList;
	}

	/**
	 * 验证EXCEL文件
	 * 
	 * @param filePath
	 * @return
	 */
	public boolean validateExcel(String filePath) {
		if (filePath == null || !(isExcel2003(filePath) || isExcel2007(filePath))) {
			errorMsg = "文件名不是excel格式";
			return false;
		}
		return true;
	}

	// @描述：是否是2003的excel，返回true是2003
	public static boolean isExcel2003(String filePath) {
		return filePath.matches("^.+\\.(?i)(xls)$");
	}

	// @描述：是否是2007的excel，返回true是2007
	public static boolean isExcel2007(String filePath) {
		return filePath.matches("^.+\\.(?i)(xlsx)$");
	}
	
	/**
	 * 读EXCEL文件，获取信息集合
	 * 
	 * @param fielName
	 * @return
	 */
	public List<StudentMapper> getStudentInfo(Integer classId,MultipartFile mFile) {
		String fileName = mFile.getOriginalFilename();// 获取文件名
		List<StudentMapper> StudentList = null;
		try {
			if (!validateExcel(fileName)) {// 验证文件名是否合格
				return null;
			}
			boolean isExcel2003 = true;// 根据文件名判断文件是2003版本还是2007版本
			if (isExcel2007(fileName)) {
				isExcel2003 = false;
			}
			StudentList = createStudentExcel(classId,mFile.getInputStream(), isExcel2003);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return StudentList;
	}

	/**
	 * 根据excel里面的内容读取客户信息
	 * 
	 * @param is输入流
	 * @param isExcel2003
	 *            excel是2003还是2007版本
	 * @return
	 * @throws IOException
	 */
	public List<StudentMapper> createStudentExcel(Integer id,InputStream is, boolean isExcel2003) {
		List<StudentMapper> StudentList = null;
		try {
			Workbook wb = null;
			if (isExcel2003) {// 当excel是2003时,创建excel2003
				wb = new HSSFWorkbook(is);
			} else {// 当excel是2007时,创建excel2007
				wb = new XSSFWorkbook(is);
			}
			StudentList = readStudentValue(id,wb);// 读取Excel里面客户的信息
		} catch (IOException e) {
			e.printStackTrace();
		}
		return StudentList;
	}

	/**
	 * 读取Excel里面客户的信息
	 * 
	 * @param wb
	 * @return
	 */
	private List<StudentMapper> readStudentValue(Integer id,Workbook wb) {
		// 得到第一个shell
		Sheet sheet = wb.getSheetAt(0);
		// 得到Excel的行数
		this.totalRows = sheet.getPhysicalNumberOfRows();
		List<StudentMapper> StudentList = new ArrayList<StudentMapper>();
		// 循环Excel行数
		for (int r = 0; r < totalRows; r++) {
			Row row = sheet.getRow(r);
			if (row == null) {
				continue;
			}
			Cell cell = row.getCell(0);
			if (null != cell) {
				cell.setCellType(Cell.CELL_TYPE_STRING);
				StudentMapper po = student_dao.findByClassesIdAndName(id,cell.getStringCellValue().trim());
				StudentList.add(po);
			} else {
				System.out.println("瞳距是空的 读取不到");
			}
		}
		return StudentList;
	}
}
