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
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.threefriend.lightspace.enums.AnswerTypeEnums;
import com.threefriend.lightspace.mapper.xcx.AnswerMapper;
import com.threefriend.lightspace.repository.AnswerRepository;

/**
 *	读取answerexcel
 */
@Service
public class ReadAnswerExcel {
	@Autowired
	private AnswerRepository answer_dao;
	// 总行数
	private int totalRows = 0;
	// 总条数
	private int totalCells = 0;
	// 错误信息接收器
	private String errorMsg;

	// 构造方法
	public ReadAnswerExcel() {
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
	public List<AnswerMapper> getAnswerInfo(MultipartFile mFile) {
		String fileName = mFile.getOriginalFilename();// 获取文件名
		List<AnswerMapper> RecordList = null;
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
	public List<AnswerMapper> createExcel(InputStream is, boolean isExcel2003) {
		List<AnswerMapper> AnswerList = null;
		try {
			Workbook wb = null;
			if (isExcel2003) {// 当excel是2003时,创建excel2003
				wb = new HSSFWorkbook(is);
			} else {// 当excel是2007时,创建excel2007
				wb = new XSSFWorkbook(is);
			}
			AnswerList = readRecordValue(wb);// 读取Excel里面客户的信息
		} catch (IOException e) {
			e.printStackTrace();
		}
		return AnswerList;
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
	private List<AnswerMapper> readRecordValue(Workbook wb) {
		// 得到第一个shell
		Sheet sheet = wb.getSheetAt(0);
		// 得到Excel的行数
		this.totalRows = sheet.getPhysicalNumberOfRows();
		// 得到Excel的列数(前提是有行数)
		if (totalRows > 1 && sheet.getRow(0) != null) {
			this.totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
		}
		List<AnswerMapper> AnswerList = new ArrayList<AnswerMapper>();
		// 循环Excel行数
		for (int r = 1; r < totalRows; r++) {
			Row row = sheet.getRow(r);
			if (row == null) {
				continue;
			}
			AnswerMapper Answer = new AnswerMapper();
			StringBuilder optionsStr = new StringBuilder();
			// 循环Excel的列
			for (int c = 0; c < this.totalCells; c++) {
				Cell cell = row.getCell(c);
				if (null != cell) {
					cell.setCellType(Cell.CELL_TYPE_STRING);
					switch (c) {
					case 0://题目
						Answer.setTitle(cell.getStringCellValue());
						break;
					case 1://题目类型
						if(cell.getStringCellValue().equals(AnswerTypeEnums.SINGLE.getMessage())) {
							Answer.setType(AnswerTypeEnums.SINGLE.getCode());
						}else {
							Answer.setType(AnswerTypeEnums.MULTIPLE.getCode());
						}
						break;
					case 2://选项一
						if(!StringUtils.isEmpty(cell.getStringCellValue()))
						optionsStr.append("1").append(cell.getStringCellValue());
						break;
					case 3://选项二
						if(!StringUtils.isEmpty(cell.getStringCellValue()))
						optionsStr.append("-2").append(cell.getStringCellValue());
						break;
					case 4://选项三
						if(!StringUtils.isEmpty(cell.getStringCellValue()))
						optionsStr.append("-3").append(cell.getStringCellValue());
						break;
					case 5://选项四
						if(!StringUtils.isEmpty(cell.getStringCellValue()))
						optionsStr.append("-4").append(cell.getStringCellValue());
						break;
					case 6://答案
						Answer.setOptionStr(optionsStr.toString());
						Answer.setKeyStr(cell.getStringCellValue());
						break;
					case 7://题目等级
						Answer.setLevel(Integer.valueOf(cell.getStringCellValue()));
						break;
					case 8://题目解释
						if(!StringUtils.isEmpty(cell.getStringCellValue()))
						Answer.setExplains(cell.getStringCellValue());
						break;
					default:
						break;
					}
				}else {
					System.out.println("这个列是空的 读取不到");
				}
				}
			// 添加到list
			AnswerList.add(Answer);
			}
		return AnswerList;
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
