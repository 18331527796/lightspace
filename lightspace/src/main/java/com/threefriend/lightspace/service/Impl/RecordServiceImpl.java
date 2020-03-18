package com.threefriend.lightspace.service.Impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.threefriend.lightspace.enums.RecordEnums;
import com.threefriend.lightspace.enums.ResultEnum;
import com.threefriend.lightspace.enums.VisionEnums;
import com.threefriend.lightspace.mapper.RecordMapper;
import com.threefriend.lightspace.repository.ClassesRepository;
import com.threefriend.lightspace.repository.RecordRepository;
import com.threefriend.lightspace.repository.RegionRepository;
import com.threefriend.lightspace.repository.SchoolRepository;
import com.threefriend.lightspace.repository.StudentRepository;
import com.threefriend.lightspace.repository.StudentWordRepository;
import com.threefriend.lightspace.service.RecordService;
import com.threefriend.lightspace.util.DownTemplateUtil;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ClassStatisticsVO;
import com.threefriend.lightspace.vo.OneStatisticsVO;
import com.threefriend.lightspace.vo.RecordVO;
import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.vo.SchoolStatisticsVO;
import com.threefriend.lightspace.vo.StatisticsVO;
import com.threefriend.lightspace.vo.StudentStatisticsVO;

/**
 * 基础数据业务逻辑实现类
 */
@Service
public class RecordServiceImpl implements RecordService {
	
	private static final String PATH="E:\\检查数据导入模板.xlsx"; 
	private static final String FILENAME="检查数据导入模板.xlsx"; 

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
	@Autowired
	private ReadRecordExcel readexcel;
	@Autowired
	private StudentWordRepository studentword_dao;

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
			record.setVisionLeftStr(Double.valueOf(params.get("visionLeft")));
		if (!StringUtils.isEmpty(params.get("visionRight")))
			record.setVisionRightStr(Double.valueOf(params.get("visionRight")));
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
			record.setVisionLeftStr(Double.valueOf(params.get("visionLeft")));
		if (!StringUtils.isEmpty(params.get("visionRight")))
			record.setVisionRightStr(Double.valueOf(params.get("visionRight")));
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
		vo.setVisionLeft(po.getVisionLeftStr());
		vo.setVisionRight(po.getVisionRightStr());
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
	public StudentStatisticsVO findAllByStudentId(Integer id,Long timeLong) {
		Date after= new Date();
		Date befor= new Date(after.getTime()-(timeLong*1000));
		DateFormat format =new SimpleDateFormat("yyyy/MM/dd");
		List<RecordMapper> all = record_dao.findAllByStudentIdAndGenTimeBetweenOrderByGenTime(id,befor,after);
		StudentStatisticsVO end = new StudentStatisticsVO();
		
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
			visionLeft.getyDataList().add(po.getVisionLeftStr());
			visionLeft.getxDataList().add(time);
			
			if(visionRight.getName()==null||visionRight.getName()=="") {
				visionRight.setName(RecordEnums.VISIONRIGHT.getName());
			}
			visionRight.getyDataList().add(po.getVisionRightStr());
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
		end.setVisionLeft(visionLeft);
		end.setVisionRight(visionRight);
		end.setCurvatureLeft(curvatureLeft);
		end.setCurvatureRight(curvatureRight);
		end.setEyeAxisLengthLeft(eyeAxisLengthLeft);
		end.setEyeAxisLengthRight(eyeAxisLengthRight);
		end.setStudnetWord(studentword_dao.findByStudentIdOrderByGenTime(id));
		return end;
	}

	@Override
	public SchoolStatisticsVO schoolStatistics(Integer schoolId) {
		//返回对象格式
		SchoolStatisticsVO statistics = new SchoolStatisticsVO();
		//学校总人数
		int schoolTotal = student_dao.countBySchoolId(schoolId);
		//实验班人数
		int schoolTest = 0;
		List<Integer> allclass = class_dao.AllclassIdBySchoolIdAndExperiment(schoolId, 1);
			schoolTest += student_dao.countByClassesIdIn(allclass);
		
		//传数据
		statistics.setSchoolTotal(schoolTotal);
		statistics.setSchoolTest(schoolTest);
		statistics.setSchoolNormal(schoolTotal-schoolTest);
		
		//从这里开始 是学校全部人的概况
		List<StatisticsVO> left = new ArrayList<>();
		List<StatisticsVO> right = new ArrayList<>();
		List<StatisticsVO> avg = new ArrayList<>();
		List<List<StatisticsVO>> end = new ArrayList<>();
		Integer leftGood = record_dao.countTopBySchoolIdAndVisionLeftGreaterThanOrderByStudentId(schoolId,
				VisionEnums.NORMAL.getType());
		Integer leftMild = record_dao.countTopBySchoolIdAndVisionLeftBetweenOrderByStudentId(schoolId,
				VisionEnums.MILD.getType(), VisionEnums.NORMAL.getType());
		Integer leftModerate = record_dao.countTopBySchoolIdAndVisionLeftBetweenOrderByStudentId(schoolId,
				VisionEnums.MODERATE.getType(), VisionEnums.MILD1.getType());
		Integer leftSerious = record_dao.countTopBySchoolIdAndVisionLeftBetweenOrderByStudentId(schoolId,
				VisionEnums.SERIOUS1.getType(), VisionEnums.SERIOUS.getType());
		Integer rightGood = record_dao.countTopBySchoolIdAndVisionRightGreaterThanOrderByStudentId(schoolId,
				VisionEnums.NORMAL.getType());
		Integer rightMild = record_dao.countTopBySchoolIdAndVisionRightBetweenOrderByStudentId(schoolId,
				VisionEnums.MILD.getType(), VisionEnums.NORMAL.getType());
		Integer rightModerate = record_dao.countTopBySchoolIdAndVisionRightBetweenOrderByStudentId(schoolId,
				VisionEnums.MODERATE.getType(), VisionEnums.MILD1.getType());
		Integer rightSerious = record_dao.countTopBySchoolIdAndVisionRightBetweenOrderByStudentId(schoolId,
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
		statistics.setTotalData(end);
		//
		List<Integer> classId = class_dao.AllclassIdBySchoolIdAndExperiment(schoolId, 1);
		List<StatisticsVO> left1 = new ArrayList<>();
		List<StatisticsVO> right1 = new ArrayList<>();
		List<StatisticsVO> avg1 = new ArrayList<>();
		List<List<StatisticsVO>> end1 = new ArrayList<>();
		Integer leftGood1 = 0,leftMild1 = 0,leftModerate1 = 0,leftSerious1 = 0,
				rightGood1= 0,rightMild1= 0,rightModerate1= 0,rightSerious1= 0,
				avgGood1= 0,avgMild1= 0,avgModerate1= 0,avgSerious1= 0;
			 leftGood1 += record_dao.countTopByClassesIdInAndVisionLeftGreaterThanOrderByStudentId(classId,
					VisionEnums.NORMAL.getType());
			 leftMild1 += record_dao.countTopByClassesIdInAndVisionLeftBetweenOrderByStudentId(classId,
					VisionEnums.MILD.getType(), VisionEnums.NORMAL.getType());
			 leftModerate1 += record_dao.countTopByClassesIdInAndVisionLeftBetweenOrderByStudentId(classId,
					VisionEnums.MODERATE.getType(), VisionEnums.MILD1.getType());
			 leftSerious1 += record_dao.countTopByClassesIdInAndVisionLeftBetweenOrderByStudentId(classId,
					VisionEnums.SERIOUS1.getType(), VisionEnums.SERIOUS.getType());
			 rightGood1 += record_dao.countTopByClassesIdInAndVisionRightGreaterThanOrderByStudentId(classId,
					VisionEnums.NORMAL.getType());
			 rightMild1 += record_dao.countTopByClassesIdInAndVisionRightBetweenOrderByStudentId(classId,
					VisionEnums.MILD.getType(), VisionEnums.NORMAL.getType());
			 rightModerate1 += record_dao.countTopByClassesIdInAndVisionRightBetweenOrderByStudentId(classId,
					VisionEnums.MODERATE.getType(), VisionEnums.MILD1.getType());
			 rightSerious1 += record_dao.countTopByClassesIdInAndVisionRightBetweenOrderByStudentId(classId,
					VisionEnums.SERIOUS1.getType(), VisionEnums.SERIOUS.getType());
			 avgGood1 += record_dao.classInAvgVision(classId, VisionEnums.NORMAL.getType());
			 avgMild1 += record_dao.classInAvgVision(classId, VisionEnums.MILD.getType(),
					VisionEnums.NORMAL.getType());
			 avgModerate1 += record_dao.classInAvgVision(classId, VisionEnums.MODERATE.getType(),
					VisionEnums.MILD1.getType());
			 avgSerious1 += record_dao.classInAvgVision(classId, VisionEnums.SERIOUS1.getType(),
					VisionEnums.SERIOUS.getType());
		left1.add(new StatisticsVO("良好", leftGood1,new HashMap<String,String>() {{put("color", "#0793FF");}}));
		left1.add(new StatisticsVO("轻度不良", leftMild1,new HashMap<String,String>() {{put("color", "#FFAA07");}}));
		left1.add(new StatisticsVO("中度不良", leftModerate1,new HashMap<String,String>() {{put("color", "#FF0724");}}));
		left1.add(new StatisticsVO("重度不良", leftSerious1,new HashMap<String,String>() {{put("color", "#6F0427");}}));
		end1.add(left1);
		right1.add(new StatisticsVO("良好", rightGood1,new HashMap<String,String>() {{put("color", "#0793FF");}}));
		right1.add(new StatisticsVO("轻度不良", rightMild1,new HashMap<String,String>() {{put("color", "#FFAA07");}}));
		right1.add(new StatisticsVO("中度不良", rightModerate1,new HashMap<String,String>() {{put("color", "#FF0724");}}));
		right1.add(new StatisticsVO("重度不良", rightSerious1,new HashMap<String,String>() {{put("color", "#6F0427");}}));
		end1.add(right1);
		avg1.add(new StatisticsVO("良好", avgGood1,new HashMap<String,String>() {{put("color", "#0793FF");}}));
		avg1.add(new StatisticsVO("轻度不良", avgMild1,new HashMap<String,String>() {{put("color", "#FFAA07");}}));
		avg1.add(new StatisticsVO("中度不良", avgModerate1,new HashMap<String,String>() {{put("color", "#FF0724");}}));
		avg1.add(new StatisticsVO("重度不良", avgSerious1,new HashMap<String,String>() {{put("color", "#6F0427");}}));
		end1.add(avg1);
		statistics.setTestData(end1);
		
		List<Integer> normalIds = class_dao.AllclassIdBySchoolIdAndExperiment(schoolId, 0);
		List<StatisticsVO> left2 = new ArrayList<>();
		List<StatisticsVO> right2 = new ArrayList<>();
		List<StatisticsVO> avg2 = new ArrayList<>();
		List<List<StatisticsVO>> end2 = new ArrayList<>();
		Integer leftGood2 = 0,leftMild2 = 0,leftModerate2 = 0,leftSerious2 = 0,
				rightGood2= 0,rightMild2= 0,rightModerate2= 0,rightSerious2= 0,
				avgGood2= 0,avgMild2= 0,avgModerate2= 0,avgSerious2= 0;
			leftGood2 += record_dao.countTopByClassesIdInAndVisionLeftGreaterThanOrderByStudentId(normalIds,
					VisionEnums.NORMAL.getType());
			 leftMild2 += record_dao.countTopByClassesIdInAndVisionLeftBetweenOrderByStudentId(normalIds,
					VisionEnums.MILD.getType(), VisionEnums.NORMAL.getType());
			 leftModerate2 += record_dao.countTopByClassesIdInAndVisionLeftBetweenOrderByStudentId(normalIds,
					VisionEnums.MODERATE.getType(), VisionEnums.MILD1.getType());
			 leftSerious2 += record_dao.countTopByClassesIdInAndVisionLeftBetweenOrderByStudentId(normalIds,
					VisionEnums.SERIOUS1.getType(), VisionEnums.SERIOUS.getType());
			 rightGood2 += record_dao.countTopByClassesIdInAndVisionRightGreaterThanOrderByStudentId(normalIds,
					VisionEnums.NORMAL.getType());
			 rightMild2 += record_dao.countTopByClassesIdInAndVisionRightBetweenOrderByStudentId(normalIds,
					VisionEnums.MILD.getType(), VisionEnums.NORMAL.getType());
			 rightModerate2 += record_dao.countTopByClassesIdInAndVisionRightBetweenOrderByStudentId(normalIds,
					VisionEnums.MODERATE.getType(), VisionEnums.MILD1.getType());
			 rightSerious2 += record_dao.countTopByClassesIdInAndVisionRightBetweenOrderByStudentId(normalIds,
					VisionEnums.SERIOUS1.getType(), VisionEnums.SERIOUS.getType());
			 avgGood2 += record_dao.classInAvgVision(normalIds, VisionEnums.NORMAL.getType());
			 avgMild2 += record_dao.classInAvgVision(normalIds, VisionEnums.MILD.getType(),
					VisionEnums.NORMAL.getType());
			 avgModerate2 += record_dao.classInAvgVision(normalIds, VisionEnums.MODERATE.getType(),
					VisionEnums.MILD1.getType());
			 avgSerious2 += record_dao.classInAvgVision(normalIds, VisionEnums.SERIOUS1.getType(),
					VisionEnums.SERIOUS.getType());
		left2.add(new StatisticsVO("良好", leftGood2,new HashMap<String,String>() {{put("color", "#0793FF");}}));
		left2.add(new StatisticsVO("轻度不良", leftMild2,new HashMap<String,String>() {{put("color", "#FFAA07");}}));
		left2.add(new StatisticsVO("中度不良", leftModerate2,new HashMap<String,String>() {{put("color", "#FF0724");}}));
		left2.add(new StatisticsVO("重度不良", leftSerious2,new HashMap<String,String>() {{put("color", "#6F0427");}}));
		end2.add(left2);
		right2.add(new StatisticsVO("良好", rightGood2,new HashMap<String,String>() {{put("color", "#0793FF");}}));
		right2.add(new StatisticsVO("轻度不良", rightMild2,new HashMap<String,String>() {{put("color", "#FFAA07");}}));
		right2.add(new StatisticsVO("中度不良", rightModerate2,new HashMap<String,String>() {{put("color", "#FF0724");}}));
		right2.add(new StatisticsVO("重度不良", rightSerious2,new HashMap<String,String>() {{put("color", "#6F0427");}}));
		end2.add(right2);
		avg2.add(new StatisticsVO("良好", avgGood2,new HashMap<String,String>() {{put("color", "#0793FF");}}));
		avg2.add(new StatisticsVO("轻度不良", avgMild2,new HashMap<String,String>() {{put("color", "#FFAA07");}}));
		avg2.add(new StatisticsVO("中度不良", avgModerate2,new HashMap<String,String>() {{put("color", "#FF0724");}}));
		avg2.add(new StatisticsVO("重度不良", avgSerious2,new HashMap<String,String>() {{put("color", "#6F0427");}}));
		end2.add(avg2);
		statistics.setNormalData(end2);
		
		return statistics;
	}
	
	@Override
	public List<List<StatisticsVO>> schoolStatisticsOld(Integer schoolId) {
		
		List<StatisticsVO> left = new ArrayList<>();
		List<StatisticsVO> right = new ArrayList<>();
		List<StatisticsVO> avg = new ArrayList<>();
		List<List<StatisticsVO>> end = new ArrayList<>();
		Integer leftGood = record_dao.countTopBySchoolIdAndVisionLeftGreaterThanOrderByStudentId(schoolId,
				VisionEnums.NORMAL.getType());
		Integer leftMild = record_dao.countTopBySchoolIdAndVisionLeftBetweenOrderByStudentId(schoolId,
				VisionEnums.MILD.getType(), VisionEnums.NORMAL.getType());
		Integer leftModerate = record_dao.countTopBySchoolIdAndVisionLeftBetweenOrderByStudentId(schoolId,
				VisionEnums.MODERATE.getType(), VisionEnums.MILD1.getType());
		Integer leftSerious = record_dao.countTopBySchoolIdAndVisionLeftBetweenOrderByStudentId(schoolId,
				VisionEnums.SERIOUS1.getType(), VisionEnums.SERIOUS.getType());
		Integer rightGood = record_dao.countTopBySchoolIdAndVisionRightGreaterThanOrderByStudentId(schoolId,
				VisionEnums.NORMAL.getType());
		Integer rightMild = record_dao.countTopBySchoolIdAndVisionRightBetweenOrderByStudentId(schoolId,
				VisionEnums.MILD.getType(), VisionEnums.NORMAL.getType());
		Integer rightModerate = record_dao.countTopBySchoolIdAndVisionRightBetweenOrderByStudentId(schoolId,
				VisionEnums.MODERATE.getType(), VisionEnums.MILD1.getType());
		Integer rightSerious = record_dao.countTopBySchoolIdAndVisionRightBetweenOrderByStudentId(schoolId,
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
	public List<List<StatisticsVO>> classStatisticsOld(Integer id) {
		List<Integer> classId = new ArrayList<>();
		classId.add(id);
		List<StatisticsVO> left = new ArrayList<>();
		List<StatisticsVO> right = new ArrayList<>();
		List<StatisticsVO> avg = new ArrayList<>();
		List<List<StatisticsVO>> end = new ArrayList<>();
		Integer leftGood = record_dao.countTopByClassesIdInAndVisionLeftGreaterThanOrderByStudentId(classId,
				VisionEnums.NORMAL.getType());
		Integer leftMild = record_dao.countTopByClassesIdInAndVisionLeftBetweenOrderByStudentId(classId,
				VisionEnums.MILD.getType(), VisionEnums.NORMAL.getType());
		Integer leftModerate = record_dao.countTopByClassesIdInAndVisionLeftBetweenOrderByStudentId(classId,
				VisionEnums.MODERATE.getType(), VisionEnums.MILD1.getType());
		Integer leftSerious = record_dao.countTopByClassesIdInAndVisionLeftBetweenOrderByStudentId(classId,
				VisionEnums.SERIOUS1.getType(), VisionEnums.SERIOUS.getType());
		Integer rightGood = record_dao.countTopByClassesIdInAndVisionLeftGreaterThanOrderByStudentId(classId,
				VisionEnums.NORMAL.getType());
		Integer rightMild = record_dao.countTopByClassesIdInAndVisionLeftBetweenOrderByStudentId(classId,
				VisionEnums.MILD.getType(), VisionEnums.NORMAL.getType());
		Integer rightModerate = record_dao.countTopByClassesIdInAndVisionLeftBetweenOrderByStudentId(classId,
				VisionEnums.MODERATE.getType(), VisionEnums.MILD1.getType());
		Integer rightSerious = record_dao.countTopByClassesIdInAndVisionLeftBetweenOrderByStudentId(classId,
				VisionEnums.SERIOUS1.getType(), VisionEnums.SERIOUS.getType());
		Integer avgGood = record_dao.classInAvgVision(classId, VisionEnums.NORMAL.getType());
		Integer avgMild = record_dao.classInAvgVision(classId, VisionEnums.MILD.getType(),
				VisionEnums.NORMAL.getType());
		Integer avgModerate = record_dao.classInAvgVision(classId, VisionEnums.MODERATE.getType(),
				VisionEnums.MILD1.getType());
		Integer avgSerious = record_dao.classInAvgVision(classId, VisionEnums.SERIOUS1.getType(),
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
	public ClassStatisticsVO classStatistics(Integer id) {
		List<Integer> classId = new ArrayList<>();
		classId.add(id);
		//新建返回对象
		ClassStatisticsVO  statistics = new ClassStatisticsVO();
		//查这个班里的总人数
		int total = student_dao.countByClassesId(id);
		//这个班里的矫正人数
		int correct = student_dao.countByClassesIdAndCorrect(id, 1);
		statistics.setTotalStudent(total);
		statistics.setCorrectedStudent(correct);
		statistics.setUncorrectStudent(total-correct);
		List<StatisticsVO> left = new ArrayList<>();
		List<StatisticsVO> right = new ArrayList<>();
		List<StatisticsVO> avg = new ArrayList<>();
		List<List<StatisticsVO>> end = new ArrayList<>();
		Integer leftGood = record_dao.countTopByClassesIdInAndVisionLeftGreaterThanOrderByStudentId(classId,
				VisionEnums.NORMAL.getType());
		Integer leftMild = record_dao.countTopByClassesIdInAndVisionLeftBetweenOrderByStudentId(classId,
				VisionEnums.MILD.getType(), VisionEnums.NORMAL.getType());
		Integer leftModerate = record_dao.countTopByClassesIdInAndVisionLeftBetweenOrderByStudentId(classId,
				VisionEnums.MODERATE.getType(), VisionEnums.MILD1.getType());
		Integer leftSerious = record_dao.countTopByClassesIdInAndVisionLeftBetweenOrderByStudentId(classId,
				VisionEnums.SERIOUS1.getType(), VisionEnums.SERIOUS.getType());
		Integer rightGood = record_dao.countTopByClassesIdInAndVisionLeftGreaterThanOrderByStudentId(classId,
				VisionEnums.NORMAL.getType());
		Integer rightMild = record_dao.countTopByClassesIdInAndVisionLeftBetweenOrderByStudentId(classId,
				VisionEnums.MILD.getType(), VisionEnums.NORMAL.getType());
		Integer rightModerate = record_dao.countTopByClassesIdInAndVisionLeftBetweenOrderByStudentId(classId,
				VisionEnums.MODERATE.getType(), VisionEnums.MILD1.getType());
		Integer rightSerious = record_dao.countTopByClassesIdInAndVisionLeftBetweenOrderByStudentId(classId,
				VisionEnums.SERIOUS1.getType(), VisionEnums.SERIOUS.getType());
		Integer avgGood = record_dao.classInAvgVision(classId, VisionEnums.NORMAL.getType());
		Integer avgMild = record_dao.classInAvgVision(classId, VisionEnums.MILD.getType(),
				VisionEnums.NORMAL.getType());
		Integer avgModerate = record_dao.classInAvgVision(classId, VisionEnums.MODERATE.getType(),
				VisionEnums.MILD1.getType());
		Integer avgSerious = record_dao.classInAvgVision(classId, VisionEnums.SERIOUS1.getType(),
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
		statistics.setData(end);
		return statistics;
	}
	
	/* 
	 * 批量导入检测数据
	 */
	@Override
	@Transactional
	public ResultVO readRecordExcel(MultipartFile file,String token) {
		List<RecordMapper> recordInfo = readexcel.getRecordInfo(file);
		if(recordInfo==null) {
			System.out.println("读取excel数据返回值是空的");
			return ResultVOUtil.error(ResultEnum.READEXCEL_ERROR.getStatus(), ResultEnum.READEXCEL_ERROR.READEXCEL_ERROR.getMessage());
		}
		
        for (RecordMapper recordMapper : recordInfo) {
        	record_dao.save(recordMapper);
        }
        
		String[] split = token.split("-");
		if (split[1].equals("3"))
			return ResultVOUtil.success(record_dao.findBySchoolId(Integer.valueOf(split[2])));
		if (split[1].equals("4"))
			return ResultVOUtil.success(record_dao.findByClassesId(Integer.valueOf(split[2])));
		return ResultVOUtil.success(record_dao.findAll());
	}

	/* 
	 * 文件下载（流方式）（暂停使用）
	 */
	@Override
	public void download(HttpServletResponse response) {
		DownTemplateUtil.downTemplate(response, PATH, FILENAME);
	}
}
