package com.threefriend.lightspace.service;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.multipart.MultipartFile;

import com.threefriend.lightspace.mapper.StudentMapper;
import com.threefriend.lightspace.mapper.xcx.ScreeningMapper;
import com.threefriend.lightspace.mapper.xcx.ScreeningWearMapper;
import com.threefriend.lightspace.vo.ResultVO;

public interface ScreeningService {
	
	ResultVO screeningList(Map<String, String> params);
	
	ResultVO deleteScreening(Map<String, String> params);
	
	ResultVO screeningWearList(Map<String, String> params);
	
	ResultVO deleteScreeningWear(Map<String, String> params);
	
	ResultVO screeningExcel(Map<String, String> params);
	
	ResultVO screeningWearExcel(Map<String, String> params);
	
	ResultVO screeningByStudent(Map<String, String> params);
	
	ResultVO screeningWearByStudent(Map<String, String> params);
	
	ResultVO screeningStudentExcel(Map<String, String> params);
	
	ResultVO screeningWearStudentExcel(Map<String, String> params);
	
	Map<String, List<String>> Excel(List<ScreeningMapper> list,List<StudentMapper> student);
	
	Map<String, List<String>> WearExcel(List<ScreeningWearMapper> list,List<StudentMapper> student);
	
	ResultVO diopterList(Map<String, String> params);
	
	Map<String, List<String>> diopterExcel(List<StudentMapper> student);
	
	ResultVO diopterExcelOut(Map<String, String> params);
	
	ResultVO saveDiopter(Map<String, String> params);
	
	ResultVO diopterByStudent(Map<String, String> params);
	
	ResultVO deleteDiopter(Map<String, String> params);

	ResultVO readDiopterExcel(Integer classId,MultipartFile export,MultipartFile nameList);
	
}
