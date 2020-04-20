package com.threefriend.lightspace.service.Impl;

import java.io.IOException;
import java.io.InputStream;
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
import org.springframework.web.multipart.MultipartFile;

import com.threefriend.lightspace.mapper.ClassesMapper;
import com.threefriend.lightspace.mapper.RecordMapper;
import com.threefriend.lightspace.mapper.RegionMapper;
import com.threefriend.lightspace.mapper.SchoolMapper;
import com.threefriend.lightspace.mapper.StudentMapper;
import com.threefriend.lightspace.repository.ClassesRepository;
import com.threefriend.lightspace.repository.RegionRepository;
import com.threefriend.lightspace.repository.SchoolRepository;
import com.threefriend.lightspace.repository.StudentRepository;

/**
 *	读取recordexcel
 */
@Service
public class ReadRecordExcel {
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
	public ReadRecordExcel() {
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
	public List<RecordMapper> getRecordInfo(MultipartFile mFile) {
		String fileName = mFile.getOriginalFilename();// 获取文件名
		List<RecordMapper> RecordList = null;
		try {
			if (!validateExcel(fileName)) {// 验证文件名是否合格
				return null;
			}
			boolean isExcel2003 = true;// 根据文件名判断文件是2003版本还是2007版本
			if (isExcel2007(fileName)) {
				isExcel2003 = false;
			}
			RecordList = createExcel(mFile.getInputStream(), isExcel2003);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return RecordList;
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
	public List<RecordMapper> createExcel(InputStream is, boolean isExcel2003) {
		List<RecordMapper> RecordList = null;
		try {
			Workbook wb = null;
			if (isExcel2003) {// 当excel是2003时,创建excel2003
				wb = new HSSFWorkbook(is);
			} else {// 当excel是2007时,创建excel2007
				wb = new XSSFWorkbook(is);
			}
			RecordList = readRecordValue(wb);// 读取Excel里面客户的信息
		} catch (IOException e) {
			e.printStackTrace();
		}
		return RecordList;
	}

	/**
	 * 读取Excel里面客户的信息
	 * 
	 * @param wb
	 * @return
	 */
	/**
	 * @param wb
	 * @return
	 */
	private List<RecordMapper> readRecordValue(Workbook wb) {
		// 得到第一个shell
		Sheet sheet = wb.getSheetAt(0);
		// 得到Excel的行数
		this.totalRows = sheet.getPhysicalNumberOfRows();
		// 得到Excel的列数(前提是有行数)
		if (totalRows > 1 && sheet.getRow(0) != null) {
			this.totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
		}
		List<RecordMapper> RecordList = new ArrayList<RecordMapper>();
		// 循环Excel行数
		Integer regionId=0;
		String regionName="";
		Integer schoolId=0;
		String schoolName="";
		Integer classId=0;
		String className="";
		for (int r = 1; r < totalRows; r++) {
			Row row = sheet.getRow(r);
			if (row == null) {
				continue;
			}
			schoolId=0;
			classId=0;
			StudentMapper student = null;
			RecordMapper Record = new RecordMapper();
			Record.setGenTime(new Date());
			// 循环Excel的列
			for (int c = 0; c < this.totalCells; c++) {
				Cell cell = row.getCell(c);
				if (null != cell) {
					cell.setCellType(Cell.CELL_TYPE_STRING);
					switch (c) {
					case 0://地区名称
						if(cell.getStringCellValue().equals(regionName)) {
							Record.setRegionId(regionId);
							Record.setRegionName(regionName);
						}else {
							RegionMapper region = region_dao.findByName(cell.getStringCellValue());
							if(region!=null) {
								regionId=region.getId();
								regionName=region.getName();
								Record.setRegionId(regionId);
								Record.setRegionName(regionName);
							}
						}
						break;
					case 1://学校名称
						if(cell.getStringCellValue().equals(schoolName)) {
							Record.setSchoolId(schoolId);
							Record.setSchoolName(schoolName);
						}else {
							SchoolMapper school = school_dao.findByName(cell.getStringCellValue()).get(0);
							if(school!=null) {
								schoolId=school.getId();
								schoolName=school.getName();
								Record.setSchoolId(schoolId);
								Record.setSchoolName(schoolName);
							}
						}
						break;
					case 2://班级名称
						if(cell.getStringCellValue().equals(className)) {
							Record.setClassesId(classId);
							Record.setClassesName(className);
						}else {
							ClassesMapper classes = class_dao.findBySchoolIdAndClassName(schoolId,cell.getStringCellValue()).get(0);
							if(classes!=null) {
								classId=classes.getId();
								className=classes.getClassName();
								Record.setClassesId(classId);
								Record.setClassesName(className);
							}
						}
						break;
					case 3://学生姓名
						student = student_dao.findBySchoolIdAndClassesIdAndName(schoolId, classId,cell.getStringCellValue() );
						if(student!=null) {
						Record.setStudentId(student.getId());
						Record.setStudentName(student.getName());
						}
						break;
					case 4://右眼曲率
						Record.setCurvatureRight(Double.valueOf(cell.getStringCellValue()));
						break;
					case 5://左眼曲率
						Record.setCurvatureLeft(Double.valueOf(cell.getStringCellValue()));
						break;
					case 6://右眼矫正视力
						Record.setCvaRight(Double.valueOf(cell.getStringCellValue()));
						break;
					case 7://左眼矫正视力
						Record.setCvaLeft(Double.valueOf(cell.getStringCellValue()));
						break;
					case 8://右眼屈光度
						Record.setDiopterRight(cell.getStringCellValue());
						break;
					case 9://左眼屈光度
						Record.setDiopterLeft(cell.getStringCellValue());
						break;
					case 10://右眼眼轴长度
						Record.setEyeAxisLengthRight(Double.valueOf(cell.getStringCellValue()));
						break;
					case 11://左眼眼轴长度
						Record.setEyeAxisLengthLeft(Double.valueOf(cell.getStringCellValue()));
						break;
					case 12://右眼裸眼视力
						Record.setVisionRightStr(Double.valueOf(cell.getStringCellValue()));
						if(student!=null) student.setVisionRightStr(Double.valueOf(cell.getStringCellValue()));
						break;
					case 13://左眼裸眼视力
						Record.setVisionLeftStr(Double.valueOf(cell.getStringCellValue()));
						if(student!=null) {
							student.setVisionLeftStr(Double.valueOf(cell.getStringCellValue()));
							student_dao.save(student);
						}
						break;
					default:
						break;
					}
				}else {
					System.out.println("这个列是空的 读取不到");
				}
				}
			// 添加到list
			RecordList.add(Record);
			}
		return RecordList;
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
