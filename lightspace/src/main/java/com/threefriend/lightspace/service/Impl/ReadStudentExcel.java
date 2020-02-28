package com.threefriend.lightspace.service.Impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
import org.springframework.web.multipart.MultipartFile;

import com.threefriend.lightspace.mapper.ClassesMapper;
import com.threefriend.lightspace.mapper.RegionMapper;
import com.threefriend.lightspace.mapper.SchoolMapper;
import com.threefriend.lightspace.mapper.StudentMapper;
import com.threefriend.lightspace.repository.ClassesRepository;
import com.threefriend.lightspace.repository.RegionRepository;
import com.threefriend.lightspace.repository.SchoolRepository;
import com.threefriend.lightspace.repository.StudentRepository;

@Service
public class ReadStudentExcel {
	@Autowired
	private RegionRepository region_dao;
	@Autowired
	private SchoolRepository school_dao;
	@Autowired
	private ClassesRepository class_dao;
	@Autowired
	private StudentRepository student_dao;
	
	// 总行数
	private int totalRows = 0;
	// 总条数
	private int totalCells = 0;
	// 错误信息接收器
	private String errorMsg;

	// 构造方法
	public ReadStudentExcel() {
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
	public List<StudentMapper> getStudentInfo(MultipartFile mFile) {
		long count = region_dao.count();
		String fileName = mFile.getOriginalFilename();// 获取文件名
		List<StudentMapper> studentList = null;
		try {
			if (!validateExcel(fileName)) {// 验证文件名是否合格
				return null;
			}
			boolean isExcel2003 = true;// 根据文件名判断文件是2003版本还是2007版本
			if (isExcel2007(fileName)) {
				isExcel2003 = false;
			}
			studentList = createExcel(mFile.getInputStream(), isExcel2003);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return studentList;
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
	public List<StudentMapper> createExcel(InputStream is, boolean isExcel2003) {
		List<StudentMapper> studentList = null;
		try {
			Workbook wb = null;
			if (isExcel2003) {// 当excel是2003时,创建excel2003
				wb = new HSSFWorkbook(is);
			} else {// 当excel是2007时,创建excel2007
				wb = new XSSFWorkbook(is);
			}
			studentList = readStudentValue(wb);// 读取Excel里面客户的信息
		} catch (IOException e) {
			e.printStackTrace();
		}
		return studentList;
	}

	/**
	 * 读取Excel里面客户的信息
	 * 
	 * @param wb
	 * @return
	 */
	private List<StudentMapper> readStudentValue(Workbook wb) {
		// 得到第一个shell
		Sheet sheet = wb.getSheetAt(0);
		// 得到Excel的行数
		this.totalRows = sheet.getPhysicalNumberOfRows(); 
		// 得到Excel的列数(前提是有行数)
		if (totalRows > 1 && sheet.getRow(0) != null) {
			this.totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
		}
		List<StudentMapper> studentList = new ArrayList<StudentMapper>();
		// 循环Excel行数
		Integer schoolId;
		String name = "";
		boolean flag=true;
		for (int r = 1; r < totalRows; r++) {
			Row row = sheet.getRow(r);
			if (row == null) {
				continue;
			}
			StudentMapper student = new StudentMapper();
			schoolId=0;
			// 循环Excel的列
			for (int c = 0; c < this.totalCells; c++) {
				Cell cell = row.getCell(c);
				if (null != cell) {
					cell.setCellType(Cell.CELL_TYPE_STRING);
					System.out.println(cell.getStringCellValue());
					switch (c) {
					case 0://姓名
						name=cell.getStringCellValue();
						student.setName(cell.getStringCellValue());
						break;
					case 1://性别 ( 0 : 男 1 ：女)
						if(cell.getStringCellValue().equals("男")) {
							student.setGender(0);
						}else {
							student.setGender(1);
						}
						break;
					case 2://年齡
						student.setAge(Integer.valueOf(cell.getStringCellValue()));
						break;
					case 3://身高
						student.setHeight(cell.getStringCellValue());
						break;
					case 4://体重
						student.setWeight(cell.getStringCellValue());
						break;
					case 5://性格
						student.setNature(cell.getStringCellValue());
						break;
					case 6://是否矫正（0：否 1：是）
						if(cell.getStringCellValue().equals("是")) {
							student.setCorrect(1);
						}else {
							student.setCorrect(0);;
						}
						break;
					case 7://坐姿高度
						student.setSittingHeight(cell.getStringCellValue());
						break;
					case 8://椅子高度
						student.setChairHeight(cell.getStringCellValue());
						break;
					case 9://地区名称
						RegionMapper region = region_dao.findByName(cell.getStringCellValue());
						if(region!=null) {
						student.setRegionId(region.getId());
						student.setRegionName(region.getName());
						}
						break;
					case 10://学校名称
						SchoolMapper school = school_dao.findByName(cell.getStringCellValue()).get(0);
						if(school!=null) {
						schoolId=school.getId();
						student.setSchoolId(schoolId);
						student.setSchoolName(school.getName());
						}
						break;
					case 11://班級名称
						ClassesMapper classes = class_dao.findBySchoolIdAndClassName(schoolId, cell.getStringCellValue()).get(0);
						if(classes!=null) {
						student.setClassesId(classes.getId());
						student.setClassesName(classes.getClassName());
						}
						break;
					case 12:
						StudentMapper po = student_dao.findByNameAndParentPhone(name ,cell.getStringCellValue());
						if(po!=null)flag=false;
						student.setParentPhone(cell.getStringCellValue());
						break;
					case 13://备注
						student.setDescription(cell.getStringCellValue());
						break;
					default:
						break;
					}
				}else {
					System.out.println("这个列是空的 读取不到");
				}
				}
			// 添加到list
			if(flag)studentList.add(student);
			}
		return studentList;
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
}
