package com.threefriend.lightspace.service.Impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.threefriend.lightspace.constant.RecordEnums;
import com.threefriend.lightspace.constant.VisionEnums;
import com.threefriend.lightspace.enums.ResultEnum;
import com.threefriend.lightspace.mapper.RecordMapper;
import com.threefriend.lightspace.repository.ClassesRepository;
import com.threefriend.lightspace.repository.RecordRepository;
import com.threefriend.lightspace.repository.RegionRepository;
import com.threefriend.lightspace.repository.SchoolRepository;
import com.threefriend.lightspace.repository.StudentRepository;
import com.threefriend.lightspace.service.RecordService;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.OneStatisticsVO;
import com.threefriend.lightspace.vo.RecordVO;
import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.vo.StatisticsVO;

/**
 * 基础数据业务逻辑实现类
 */
@Service
public class RecordServiceImpl implements RecordService {

	@Autowired
	private RecordRepository record_dao;
	@Autowired
	private SchoolRepository school_dao;
	@Autowired
	private ClassesRepository class_dao;
	@Autowired
	private StudentRepository student_dao;
	@Autowired
	private RegionRepository region_dao;

	/*
	 * 新增数据
	 */
	@Override
	public List<RecordMapper> addRecord(Map<String, String> params) {
		System.out.println(params.get("cvaLeft")+"---"+params.get("curvatureRight")+"---"+params.get("diopterLeft")+"---"+params.get("diopterRight"));
		RecordMapper record = new RecordMapper();
		if (!StringUtils.isEmpty(params.get("curvatureLeft")))
			record.setCurvatureLeft(Double.valueOf(params.get("curvatureLeft")));
		if (!StringUtils.isEmpty(params.get("curvatureRight")))
			record.setCurvatureRight(Double.valueOf(params.get("curvatureRight")));
		if (!StringUtils.isEmpty(params.get("cvaLeft")))
			record.setCvaLeft(Double.valueOf(params.get("cvaLeft")));
		if (!StringUtils.isEmpty(params.get("cvaRight")))
			record.setCvaRight(Double.valueOf(params.get("cvaRight")));
		if (!StringUtils.isEmpty(params.get("diopterLeft")))
			record.setDiopterLeft(params.get("diopterLeft"));
		if (!StringUtils.isEmpty(params.get("diopterRight")))
			record.setDiopterRight(params.get("diopterRight"));
		if (!StringUtils.isEmpty(params.get("eyeAxisLengthLeft")))
			record.setEyeAxisLengthLeft(Double.valueOf(params.get("eyeAxisLengthLeft")));
		if (!StringUtils.isEmpty(params.get("eyeAxisLengthRight")))
			record.setEyeAxisLengthRight(Double.valueOf(params.get("eyeAxisLengthRight")));
		if (!StringUtils.isEmpty(params.get("visionLeft")))
			record.setVisionLeft(Double.valueOf(params.get("visionLeft")));
		if (!StringUtils.isEmpty(params.get("visionRight")))
			record.setVisionRight(Double.valueOf(params.get("visionRight")));
		record.setRegionId(1);
		record.setRegionName("唐山");
		record.setStudentId(Integer.valueOf(params.get("studentId")));
		record.setStudentName(student_dao.findById(Integer.valueOf(params.get("studentId"))).get().getName());
		record.setSchoolId(Integer.valueOf(params.get("schoolId")));
		record.setSchoolName(school_dao.findById(Integer.valueOf(params.get("schoolId"))).get().getName());
		record.setClassesId(Integer.valueOf(params.get("classId")));
		record.setClassesName(class_dao.findById(Integer.valueOf(params.get("classId"))).get().getClassName());
		record.setGenTime(new Date());
		record_dao.save(record);
		String[] split = params.get("token").split("-");
		if (split[1].equals("3"))
			return record_dao.findBySchoolId(Integer.valueOf(split[2]));
		if (split[1].equals("4"))
			return record_dao.findByClassesId(Integer.valueOf(split[2]));
		return record_dao.findAll();
	}

	/*
	 * 修改后保存数据
	 */
	@Override
	public List<RecordMapper> saveRecord(Map<String, String> params) {
		System.out.println(params.get("cvaLeft")+"---"+params.get("curvatureRight")+"---"+params.get("diopterLeft")+"---"+params.get("diopterRight"));
		RecordMapper record = record_dao.findById(Integer.valueOf(params.get("id"))).get();
		if (!StringUtils.isEmpty(params.get("curvatureLeft")))
			record.setCurvatureLeft(Double.valueOf(params.get("curvatureLeft")));
		if (!StringUtils.isEmpty(params.get("curvatureRight")))
			record.setCurvatureRight(Double.valueOf(params.get("curvatureRight")));
		if (!StringUtils.isEmpty(params.get("cvaLeft"))) {
			record.setCvaLeft(Double.valueOf(params.get("cvaLeft")));
		}else{
			record.setCvaLeft(0.00);
		}
		if (!StringUtils.isEmpty(params.get("cvaRight"))) {
			record.setCvaRight(Double.valueOf(params.get("cvaRight")));
		}else {
			record.setCvaRight(0.00);
		}
		if (!StringUtils.isEmpty(params.get("diopterLeft"))) {
			record.setDiopterLeft(params.get("diopterLeft"));
		}else {
			record.setDiopterLeft("");
		}
		if (!StringUtils.isEmpty(params.get("diopterRight"))) {
			record.setDiopterRight(params.get("diopterRight"));
		}else {
			record.setDiopterRight("");
		}
		if (!StringUtils.isEmpty(params.get("eyeAxisLengthLeft")))
			record.setEyeAxisLengthLeft(Double.valueOf(params.get("eyeAxisLengthLeft")));
		if (!StringUtils.isEmpty(params.get("eyeAxisLengthRight")))
			record.setEyeAxisLengthRight(Double.valueOf(params.get("eyeAxisLengthRight")));
		if (!StringUtils.isEmpty(params.get("visionLeft")))
			record.setVisionLeft(Double.valueOf(params.get("visionLeft")));
		if (!StringUtils.isEmpty(params.get("visionRight")))
			record.setVisionRight(Double.valueOf(params.get("visionRight")));
		record_dao.save(record);
		String[] split = params.get("token").split("-");
		if (split[1].equals("3"))
			return record_dao.findBySchoolId(Integer.valueOf(split[2]));
		if (split[1].equals("4"))
			return record_dao.findByClassesId(Integer.valueOf(split[2]));
		return record_dao.findAll();
	}

	/*
	 * 删除数据
	 */
	@Override
	public List<RecordMapper> deleteRecord(Integer id, String token) {
		record_dao.deleteById(id);
		String[] split = token.split("-");
		if (split[1].equals("3"))
			return record_dao.findBySchoolId(Integer.valueOf(split[2]));
		if (split[1].equals("4"))
			return record_dao.findByClassesId(Integer.valueOf(split[2]));
		return record_dao.findAll();
	}

	/*
	 * 修改数据
	 */
	@Override
	public RecordVO editRecord(Integer id) {
		RecordMapper po = record_dao.findById(id).get();
		RecordVO vo= new RecordVO();
		BeanUtils.copyProperties(po, vo);
		vo.getRecord_cat().add(po.getSchoolId());
		vo.getRecord_cat().add(po.getClassesId());
		return vo;
	}

	/*
	 * 数据列表
	 */
	@Override
	public List<RecordMapper> recordList(String token) {
		String[] split = token.split("-");
		if (split[1].equals("3"))
			return record_dao.findBySchoolId(Integer.valueOf(split[2]));
		if (split[1].equals("4"))
			return record_dao.findByClassesId(Integer.valueOf(split[2]));
		return record_dao.findAll();
	}

	/*
	 * 模糊查询
	 */
	@Override
	public ResultVO findByName(String name) {
		List<RecordMapper> list = record_dao.findAllByStudentNameLike("%" + name + "%");
		if(list==null||list.size()==0) return ResultVOUtil.error(ResultEnum.RECORDSIZE_NULL.getStatus(), ResultEnum.RECORDSIZE_NULL.getMessage());
		return ResultVOUtil.success(list);
	}

	/*
	 * 按照学生id查询最新的基础数据
	 */
	@Override
	public RecordMapper findByStudentId(Integer id) {
		return record_dao.findTopByStudentIdOrderByGenTime(id);
	}

	/*
	 * 按照学生id查询所有的数据
	 * 
	 */
	@Override
	public List<OneStatisticsVO> findAllByStudentId(Integer id,Long timeLong) {
		Date after= new Date();
		Date befor= new Date(after.getTime()-(timeLong*1000));
		DateFormat format =new SimpleDateFormat("yyyy/MM/dd");
		List<RecordMapper> all = record_dao.findAllByStudentIdAndGenTimeBetween(id,befor,after);
		List<OneStatisticsVO> end= new ArrayList<>();
		OneStatisticsVO visionLeft=new OneStatisticsVO();
		OneStatisticsVO visionRight=new OneStatisticsVO();
		OneStatisticsVO eyeAxisLengthLeft=new OneStatisticsVO();
		OneStatisticsVO eyeAxisLengthRight=new OneStatisticsVO();
		OneStatisticsVO curvatureLeft=new OneStatisticsVO();
		OneStatisticsVO curvatureRight=new OneStatisticsVO();
		
		for (RecordMapper po : all) {
			String time =format.format(po.getGenTime());
			if(visionLeft.getName()==null||visionLeft.getName()=="") {
				visionLeft.setName(RecordEnums.VISIONLEFT.getName());
			}
			visionLeft.getyDataList().add(po.getVisionLeft());
			visionLeft.getxDataList().add(time);
			
			if(visionRight.getName()==null||visionRight.getName()=="") {
				visionRight.setName(RecordEnums.VISIONRIGHT.getName());
			}
			visionRight.getyDataList().add(po.getVisionRight());
			visionRight.getxDataList().add(time);
			
			if(eyeAxisLengthLeft.getName()==null||eyeAxisLengthLeft.getName()=="") {
				eyeAxisLengthLeft.setName(RecordEnums.EYEAXISLENGTHLEFT.getName());
			}
			eyeAxisLengthLeft.getyDataList().add(po.getEyeAxisLengthLeft());
			eyeAxisLengthLeft.getxDataList().add(time);
			
			if(eyeAxisLengthRight.getName()==null||eyeAxisLengthRight.getName()=="") {
				eyeAxisLengthRight.setName(RecordEnums.EYEAXISLENGTHRIGHT.getName());
			}
			eyeAxisLengthRight.getyDataList().add(po.getEyeAxisLengthRight());
			eyeAxisLengthRight.getxDataList().add(time);
			
			if(curvatureLeft.getName()==null||curvatureLeft.getName()=="") {
				curvatureLeft.setName(RecordEnums.CURVATURELEFT.getName());
			}
			curvatureLeft.getyDataList().add(po.getCurvatureLeft());
			curvatureLeft.getxDataList().add(time);
			
			if(curvatureRight.getName()==null||curvatureRight.getName()=="") {
				curvatureRight.setName(RecordEnums.CURVATURERIGHT.getName());
			}
			curvatureRight.getyDataList().add(po.getCurvatureRight());
			curvatureRight.getxDataList().add(time);
			
		}
		end.add(visionLeft);
		end.add(visionRight);
		end.add(eyeAxisLengthLeft);
		end.add(eyeAxisLengthRight);
		end.add(curvatureLeft);
		end.add(curvatureRight);
		return end;
	}

	@Override
	public List<List<StatisticsVO>> schoolStatistics(Integer schoolId) {
		List<StatisticsVO> left = new ArrayList<>();
		List<StatisticsVO> right = new ArrayList<>();
		List<StatisticsVO> avg = new ArrayList<>();
		List<List<StatisticsVO>> end = new ArrayList<>();
		Integer leftGood = record_dao.countBySchoolIdAndVisionLeftGreaterThanOrderByStudentId(schoolId,
				VisionEnums.NORMAL.getType());
		Integer leftMild = record_dao.countBySchoolIdAndVisionLeftBetweenOrderByStudentId(schoolId,
				VisionEnums.MILD.getType(), VisionEnums.NORMAL.getType());
		Integer leftModerate = record_dao.countBySchoolIdAndVisionLeftBetweenOrderByStudentId(schoolId,
				VisionEnums.MODERATE.getType(), VisionEnums.MILD1.getType());
		Integer leftSerious = record_dao.countBySchoolIdAndVisionLeftBetweenOrderByStudentId(schoolId,
				VisionEnums.SERIOUS1.getType(), VisionEnums.SERIOUS.getType());
		Integer rightGood = record_dao.countBySchoolIdAndVisionRightGreaterThanOrderByStudentId(schoolId,
				VisionEnums.NORMAL.getType());
		Integer rightMild = record_dao.countBySchoolIdAndVisionRightBetweenOrderByStudentId(schoolId,
				VisionEnums.MILD.getType(), VisionEnums.NORMAL.getType());
		Integer rightModerate = record_dao.countBySchoolIdAndVisionRightBetweenOrderByStudentId(schoolId,
				VisionEnums.MODERATE.getType(), VisionEnums.MILD1.getType());
		Integer rightSerious = record_dao.countBySchoolIdAndVisionRightBetweenOrderByStudentId(schoolId,
				VisionEnums.SERIOUS1.getType(), VisionEnums.SERIOUS.getType());
		Integer avgGood = record_dao.schoolAvgVision(schoolId, VisionEnums.NORMAL.getType());
		Integer avgMild = record_dao.schoolAvgVision(schoolId, VisionEnums.MILD.getType(),
				VisionEnums.NORMAL.getType());
		Integer avgModerate = record_dao.schoolAvgVision(schoolId, VisionEnums.MODERATE.getType(),
				VisionEnums.MILD1.getType());
		Integer avgSerious = record_dao.schoolAvgVision(schoolId, VisionEnums.SERIOUS1.getType(),
				VisionEnums.SERIOUS.getType());
		left.add(new StatisticsVO("良好", leftGood,new HashMap<String,String>() {{put("color", "#0793FF");}}));
		left.add(new StatisticsVO("轻度不良", leftMild,new HashMap<String,String>() {{put("color", "#FFAA07");}}));
		left.add(new StatisticsVO("中度不良", leftModerate,new HashMap<String,String>() {{put("color", "#FF0724");}}));
		left.add(new StatisticsVO("重度不良", leftSerious,new HashMap<String,String>() {{put("color", "#6F0427");}}));
		end.add(left);
		right.add(new StatisticsVO("良好", rightGood,new HashMap<String,String>() {{put("color", "#0793FF");}}));
		right.add(new StatisticsVO("轻度不良", rightMild,new HashMap<String,String>() {{put("color", "#FFAA07");}}));
		right.add(new StatisticsVO("中度不良", rightModerate,new HashMap<String,String>() {{put("color", "#FF0724");}}));
		right.add(new StatisticsVO("重度不良", rightSerious,new HashMap<String,String>() {{put("color", "#6F0427");}}));
		end.add(right);
		avg.add(new StatisticsVO("良好", avgGood,new HashMap<String,String>() {{put("color", "#0793FF");}}));
		avg.add(new StatisticsVO("轻度不良", avgMild,new HashMap<String,String>() {{put("color", "#FFAA07");}}));
		avg.add(new StatisticsVO("中度不良", avgModerate,new HashMap<String,String>() {{put("color", "#FF0724");}}));
		avg.add(new StatisticsVO("重度不良", avgSerious,new HashMap<String,String>() {{put("color", "#6F0427");}}));
		end.add(avg);
		return end;
	}

	@Override
	public List<List<StatisticsVO>> classStatistics(Integer classId) {
		List<StatisticsVO> left = new ArrayList<>();
		List<StatisticsVO> right = new ArrayList<>();
		List<StatisticsVO> avg = new ArrayList<>();
		List<List<StatisticsVO>> end = new ArrayList<>();
		Integer leftGood = record_dao.countByClassesIdAndVisionLeftGreaterThanOrderByStudentId(classId,
				VisionEnums.NORMAL.getType());
		Integer leftMild = record_dao.countByClassesIdAndVisionLeftBetweenOrderByStudentId(classId,
				VisionEnums.MILD.getType(), VisionEnums.NORMAL.getType());
		Integer leftModerate = record_dao.countByClassesIdAndVisionLeftBetweenOrderByStudentId(classId,
				VisionEnums.MODERATE.getType(), VisionEnums.MILD1.getType());
		Integer leftSerious = record_dao.countByClassesIdAndVisionLeftBetweenOrderByStudentId(classId,
				VisionEnums.SERIOUS1.getType(), VisionEnums.SERIOUS.getType());
		Integer rightGood = record_dao.countByClassesIdAndVisionRightGreaterThanOrderByStudentId(classId,
				VisionEnums.NORMAL.getType());
		Integer rightMild = record_dao.countByClassesIdAndVisionRightBetweenOrderByStudentId(classId,
				VisionEnums.MILD.getType(), VisionEnums.NORMAL.getType());
		Integer rightModerate = record_dao.countByClassesIdAndVisionRightBetweenOrderByStudentId(classId,
				VisionEnums.MODERATE.getType(), VisionEnums.MILD1.getType());
		Integer rightSerious = record_dao.countByClassesIdAndVisionRightBetweenOrderByStudentId(classId,
				VisionEnums.SERIOUS1.getType(), VisionEnums.SERIOUS.getType());
		Integer avgGood = record_dao.classAvgVision(classId, VisionEnums.NORMAL.getType());
		Integer avgMild = record_dao.classAvgVision(classId, VisionEnums.MILD.getType(),
				VisionEnums.NORMAL.getType());
		Integer avgModerate = record_dao.classAvgVision(classId, VisionEnums.MODERATE.getType(),
				VisionEnums.MILD1.getType());
		Integer avgSerious = record_dao.classAvgVision(classId, VisionEnums.SERIOUS1.getType(),
				VisionEnums.SERIOUS.getType());
		left.add(new StatisticsVO("良好", leftGood,new HashMap<String,String>() {{put("color", "#0793FF");}}));
		left.add(new StatisticsVO("轻度不良", leftMild,new HashMap<String,String>() {{put("color", "#FFAA07");}}));
		left.add(new StatisticsVO("中度不良", leftModerate,new HashMap<String,String>() {{put("color", "#FF0724");}}));
		left.add(new StatisticsVO("重度不良", leftSerious,new HashMap<String,String>() {{put("color", "#6F0427");}}));
		end.add(left);
		right.add(new StatisticsVO("良好", rightGood,new HashMap<String,String>() {{put("color", "#0793FF");}}));
		right.add(new StatisticsVO("轻度不良", rightMild,new HashMap<String,String>() {{put("color", "#FFAA07");}}));
		right.add(new StatisticsVO("中度不良", rightModerate,new HashMap<String,String>() {{put("color", "#FF0724");}}));
		right.add(new StatisticsVO("重度不良", rightSerious,new HashMap<String,String>() {{put("color", "#6F0427");}}));
		end.add(right);
		avg.add(new StatisticsVO("良好", avgGood,new HashMap<String,String>() {{put("color", "#0793FF");}}));
		avg.add(new StatisticsVO("轻度不良", avgMild,new HashMap<String,String>() {{put("color", "#FFAA07");}}));
		avg.add(new StatisticsVO("中度不良", avgModerate,new HashMap<String,String>() {{put("color", "#FF0724");}}));
		avg.add(new StatisticsVO("重度不良", avgSerious,new HashMap<String,String>() {{put("color", "#6F0427");}}));
		end.add(avg);
		return end;
	}

}
