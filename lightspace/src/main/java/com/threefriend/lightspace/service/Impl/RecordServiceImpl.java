package com.threefriend.lightspace.service.Impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.threefriend.lightspace.enums.RecordEnums;
import com.threefriend.lightspace.enums.ResultEnum;
import com.threefriend.lightspace.enums.VisionEnums;
import com.threefriend.lightspace.mapper.ClassesMapper;
import com.threefriend.lightspace.mapper.RecordMapper;
import com.threefriend.lightspace.mapper.StudentMapper;
import com.threefriend.lightspace.mapper.xcx.ScreeningMapper;
import com.threefriend.lightspace.mapper.xcx.ScreeningWearMapper;
import com.threefriend.lightspace.repository.ClassesRepository;
import com.threefriend.lightspace.repository.RecordRepository;
import com.threefriend.lightspace.repository.SchoolRepository;
import com.threefriend.lightspace.repository.ScreeningRepository;
import com.threefriend.lightspace.repository.ScreeningWearRepository;
import com.threefriend.lightspace.repository.StudentRepository;
import com.threefriend.lightspace.repository.StudentWordRepository;
import com.threefriend.lightspace.service.RecordService;
import com.threefriend.lightspace.util.DownTemplateUtil;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.OneStatisticsVO;
import com.threefriend.lightspace.vo.RecordVO;
import com.threefriend.lightspace.vo.ResultVO;
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
	private ScreeningRepository screening_dao;
	@Autowired
	private ScreeningWearRepository screening_wear_dao;
	@Autowired
	private SchoolRepository school_dao;
	@Autowired
	private ClassesRepository class_dao;
	@Autowired
	private StudentRepository student_dao;
	@Autowired
	private ReadRecordExcel readexcel;
	@Autowired
	private StudentWordRepository studentword_dao;
	
	/*
	 * 数据列表
	 */
	@Override
	public ResultVO recordList(Map<String, String> params,HttpSession session) {
		int page = 0;
		if(!StringUtils.isEmpty(params.get("page")))page= Integer.valueOf(params.get("page"))-1;
		String token = params.get("token");
		String[] split = token.split("-");
		if (split[1].equals("2"))
			return ResultVOUtil.success(record_dao.findBySchoolIdOrderByIdDesc(Integer.valueOf(split[2]),new PageRequest(page, 10)));
		if (split[1].equals("3"))
			return ResultVOUtil.success(record_dao.findByClassesIdOrderByIdDesc(Integer.valueOf(split[2]),new PageRequest(page, 10)));
		Integer roleId = Integer.valueOf(session.getAttribute("roleId").toString());
		if(roleId == 5 ) {
			Integer regionId = Integer.valueOf(session.getAttribute("regionId").toString());
			return ResultVOUtil.success(record_dao.findAllByRegionIdOrderByIdDesc(new PageRequest(page, 10),regionId));
		}
		
		return ResultVOUtil.success(record_dao.findAllByOrderByIdDesc(new PageRequest(page, 10)));
	}

	/*
	 * 新增数据
	 */
	@Override
	public ResultVO addRecord(Map<String, String> params) {
		StudentMapper student = student_dao.findById(Integer.valueOf(params.get("studentId"))).get();
		RecordMapper record = new RecordMapper();
		if (!StringUtils.isEmpty(params.get("curvatureLeft")))
			record.setCurvatureLeft(params.get("curvatureLeft"));
		if (!StringUtils.isEmpty(params.get("curvatureRight")))
			record.setCurvatureRight(params.get("curvatureRight"));
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
		if (!StringUtils.isEmpty(params.get("visionLeft"))) {
			record.setVisionLeftStr(Double.valueOf(params.get("visionLeft")));
		student.setVisionLeftStr(Double.valueOf(params.get("visionLeft")));
		}
		if (!StringUtils.isEmpty(params.get("visionRight"))) {
			record.setVisionRightStr(Double.valueOf(params.get("visionRight")));
			student.setVisionRightStr(Double.valueOf(params.get("visionRight")));
		}
		record.setRegionId(student.getRegionId());
		record.setRegionName(student.getRegionName());
		record.setStudentId(Integer.valueOf(params.get("studentId")));
		record.setStudentName(student.getName());
		record.setSchoolId(Integer.valueOf(params.get("schoolId")));
		record.setSchoolName(school_dao.findById(Integer.valueOf(params.get("schoolId"))).get().getName());
		record.setClassesId(Integer.valueOf(params.get("classId")));
		record.setClassesName(class_dao.findById(Integer.valueOf(params.get("classId"))).get().getClassName());
		record.setGenTime(new Date());
		record_dao.save(record);
		student_dao.save(student);
		return ResultVOUtil.success();
	}

	/*
	 * 修改后保存数据
	 */
	@Override
	public ResultVO saveRecord(Map<String, String> params) {
		RecordMapper record = record_dao.findById(Integer.valueOf(params.get("id"))).get();
		if (!StringUtils.isEmpty(params.get("curvatureLeft")))
			record.setCurvatureLeft(params.get("curvatureLeft"));
		if (!StringUtils.isEmpty(params.get("curvatureRight")))
			record.setCurvatureRight(params.get("curvatureRight"));
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
		return ResultVOUtil.success();
	}

	/*
	 * 删除数据
	 */
	@Override
	public ResultVO deleteRecord(Map<String, String> params) {
		record_dao.deleteById(Integer.valueOf(params.get("id")));
		return ResultVOUtil.success();
	}

	/*
	 * 修改数据
	 */
	@Override
	public ResultVO editRecord(Integer id) {
		RecordMapper po = record_dao.findById(id).get();
		RecordVO vo= new RecordVO();
		BeanUtils.copyProperties(po, vo);
		vo.setVisionLeft(po.getVisionLeftStr());
		vo.setVisionRight(po.getVisionRightStr());
		vo.getRecord_cat().add(po.getSchoolId());
		vo.getRecord_cat().add(po.getClassesId());
		return ResultVOUtil.success(vo);
	}

	

	/*
	 * 模糊查询
	 */
	@Override
	public ResultVO findByName(Map<String, String> params) {
		int page = 0;
		String name = params.get("name");
		if(!StringUtils.isEmpty(params.get("page")))page=Integer.valueOf(params.get("page"))-1;
		Page<RecordMapper> list = record_dao.findAllByStudentNameLikeOrderByIdDesc("%" + name + "%",new PageRequest(page, 10));
		if(list.getContent()==null||list.getContent().size()==0) return ResultVOUtil.error(ResultEnum.RECORDSIZE_NULL.getStatus(), ResultEnum.RECORDSIZE_NULL.getMessage());
		return ResultVOUtil.success(list);
	}

	/*
	 * 按照学生id查询最新的基础数据
	 */
	@Override
	public ResultVO findByStudentId(Integer id) {
		RecordMapper record = record_dao.findTopByStudentIdOrderByGenTimeDesc(id);
		StudentMapper studentMapper = student_dao.findById(id).get();
		RecordVO vo = new RecordVO();
		BeanUtils.copyProperties(record, vo);
		vo.setHeight(Double.valueOf(studentMapper.getHeight()));
		return ResultVOUtil.success(vo);
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
		StudentStatisticsVO end = new StudentStatisticsVO();
		
		OneStatisticsVO visionLeft=new OneStatisticsVO();
		OneStatisticsVO visionRight=new OneStatisticsVO();
		OneStatisticsVO eyeAxisLengthLeft=new OneStatisticsVO();
		OneStatisticsVO eyeAxisLengthRight=new OneStatisticsVO();
		
		Integer screeningType = student_dao.findById(id).get().getScreeningType();
		//这里注释是因为个人概况不能好好的展示  改用日常检测的数据展示
		//List<RecordMapper> all = record_dao.findAllByStudentIdAndGenTimeBetweenOrderByGenTime(id,befor,after);
		
		
		if(screeningType==1) {
			List<ScreeningMapper> screening = screening_dao.findByStudentIdAndGenTimeBetweenOrderById(id, befor, after);
			for (ScreeningMapper po : screening) {
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
				
				/*if(eyeAxisLengthLeft.getName()==null||eyeAxisLengthLeft.getName()=="") {
					eyeAxisLengthLeft.setName(RecordEnums.EYEAXISLENGTHLEFT.getName());
				}
				eyeAxisLengthLeft.getyDataList().add(po.getEyeAxisLengthLeft());
				eyeAxisLengthLeft.getxDataList().add(time);
				
				if(eyeAxisLengthRight.getName()==null||eyeAxisLengthRight.getName()=="") {
					eyeAxisLengthRight.setName(RecordEnums.EYEAXISLENGTHRIGHT.getName());
				}
				eyeAxisLengthRight.getyDataList().add(po.getEyeAxisLengthRight());
				eyeAxisLengthRight.getxDataList().add(time);*/
			}
		}else {
			List<ScreeningWearMapper> screeningwear = screening_wear_dao.findByStudentIdAndGenTimeBetweenOrderById(id, befor, after);
			
			for (ScreeningWearMapper po : screeningwear) {
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
			}
		}
		
		
		
		
		end.setVisionLeft(visionLeft);
		end.setVisionRight(visionRight);
		end.setEyeAxisLengthLeft(eyeAxisLengthLeft);
		end.setEyeAxisLengthRight(eyeAxisLengthRight);
		end.setStudnetWord(studentword_dao.findTopByStudentIdOrderByGenTimeDesc(id));
		return end;
	}

	
	
	@SuppressWarnings("serial")
	@Override
	public List<List<StatisticsVO>> schoolStatisticsOld(Integer schoolId) {
		
		List<StatisticsVO> left = new ArrayList<>();
		List<StatisticsVO> right = new ArrayList<>();
		List<StatisticsVO> avg = new ArrayList<>();
		List<List<StatisticsVO>> end = new ArrayList<>();
		
		int leftGood=0,leftMild=0,leftModerate=0,leftSerious=0,rightGood=0,rightMild=0,rightModerate=0,rightSerious=0,
				avgGood=0,avgMild=0,avgModerate=0,avgSerious=0;
		Double visionLeft=0d,visionRight=0d,visionavg = 0d;
		
		
		List<StudentMapper> allStudent = student_dao.findBySchoolId(schoolId);
		
		for (StudentMapper student : allStudent) {
			visionLeft=student.getVisionLeftStr();
			visionRight=student.getVisionRightStr();
			if(visionLeft==null||visionRight==null)continue;
			if(visionLeft>=visionRight) {
				visionavg = visionRight;
			}else {
				visionavg = visionLeft;
			}
				
			if(visionLeft>VisionEnums.MILD.getType()) {
				leftGood++;
			}else if(visionLeft<VisionEnums.MILD.getType()&&visionLeft>VisionEnums.MODERATE.getType()) {
				leftMild++;
			}else if(visionLeft<VisionEnums.MODERATE.getType()&&visionLeft>VisionEnums.SERIOUS.getType()){
				leftModerate++;
			}else if(visionLeft<VisionEnums.SERIOUS.getType()){
				leftSerious++;
			}
			
			if(visionRight>VisionEnums.MILD.getType()) {
				rightGood++;
			}else if(visionRight<VisionEnums.MILD.getType()&&visionRight>VisionEnums.MODERATE.getType()) {
				rightMild++;
			}else if(visionRight<VisionEnums.MODERATE.getType()&&visionRight>VisionEnums.SERIOUS.getType()){
				rightModerate++;
			}else if(visionRight<VisionEnums.SERIOUS.getType()){
				rightSerious++;
			}
			
			if(visionavg>VisionEnums.MILD.getType()) {
				avgGood++;
			}else if(visionavg<VisionEnums.MILD.getType()&&visionavg>VisionEnums.MODERATE.getType()) {
				avgMild++;
			}else if(visionavg<VisionEnums.MODERATE.getType()&&visionavg>VisionEnums.SERIOUS.getType()){
				avgModerate++;
			}else if(visionavg<VisionEnums.SERIOUS.getType()){
				avgSerious++;
			}
		}
		
		left.add(new StatisticsVO("良好", leftGood,"leftGood",new HashMap<String,String>() {{put("color", "#0793FF");}}));
		left.add(new StatisticsVO("轻度不良", leftMild,"leftMild",new HashMap<String,String>() {{put("color", "#FFAA07");}}));
		left.add(new StatisticsVO("中度不良", leftModerate,"leftModerate",new HashMap<String,String>() {{put("color", "#FF0724");}}));
		left.add(new StatisticsVO("重度不良", leftSerious,"leftSerious",new HashMap<String,String>() {{put("color", "#6F0427");}}));
		end.add(left);
		right.add(new StatisticsVO("良好", rightGood,"rightGood",new HashMap<String,String>() {{put("color", "#0793FF");}}));
		right.add(new StatisticsVO("轻度不良", rightMild,"rightMild",new HashMap<String,String>() {{put("color", "#FFAA07");}}));
		right.add(new StatisticsVO("中度不良", rightModerate,"rightModerate",new HashMap<String,String>() {{put("color", "#FF0724");}}));
		right.add(new StatisticsVO("重度不良", rightSerious,"rightSerious",new HashMap<String,String>() {{put("color", "#6F0427");}}));
		end.add(right);
		avg.add(new StatisticsVO("良好", avgGood,"avgGood",new HashMap<String,String>() {{put("color", "#0793FF");}}));
		avg.add(new StatisticsVO("轻度不良", avgMild,"avgMild",new HashMap<String,String>() {{put("color", "#FFAA07");}}));
		avg.add(new StatisticsVO("中度不良", avgModerate,"avgModerate",new HashMap<String,String>() {{put("color", "#FF0724");}}));
		avg.add(new StatisticsVO("重度不良", avgSerious,"avgSerious",new HashMap<String,String>() {{put("color", "#6F0427");}}));
		end.add(avg);
		return end;
	}
	
	@SuppressWarnings("serial")
	@Override
	public ResultVO classStatisticsOld(Integer id , List<Integer> ids) {
		List<Integer> classId = new ArrayList<>();
		if(ids==null||ids.size()==0) {
			classId.add(id);
		}else {
			classId.addAll(ids);
		}
		List<StatisticsVO> left = new ArrayList<>();
		List<StatisticsVO> right = new ArrayList<>();
		List<StatisticsVO> avg = new ArrayList<>();
		List<List<StatisticsVO>> end = new ArrayList<>();
		
		int leftGood=0,leftMild=0,leftModerate=0,leftSerious=0,rightGood=0,rightMild=0,rightModerate=0,rightSerious=0,
				avgGood=0,avgMild=0,avgModerate=0,avgSerious=0;
		Double visionLeft=0d,visionRight=0d,visionavg = 0d;
		
		List<StudentMapper> allStudent = student_dao.findByClassesId(classId);
		
		for (StudentMapper student : allStudent) {
			visionLeft=student.getVisionLeftStr();
			visionRight=student.getVisionRightStr();
			if(visionLeft==null||visionRight==null)continue;
			if(visionLeft>=visionRight) {
				visionavg = visionRight;
			}else {
				visionavg = visionLeft;
			}
			if(visionLeft>VisionEnums.MILD.getType()) {
				leftGood++;
			}else if(visionLeft<VisionEnums.MILD.getType()&&visionLeft>VisionEnums.MODERATE.getType()) {
				leftMild++;
			}else if(visionLeft<VisionEnums.MODERATE.getType()&&visionLeft>VisionEnums.SERIOUS.getType()){
				leftModerate++;
			}else if(visionLeft<VisionEnums.SERIOUS.getType()){
				leftSerious++;
			}
			
			if(visionRight>VisionEnums.MILD.getType()) {
				rightGood++;
			}else if(visionRight<VisionEnums.MILD.getType()&&visionRight>VisionEnums.MODERATE.getType()) {
				rightMild++;
			}else if(visionRight<VisionEnums.MODERATE.getType()&&visionRight>VisionEnums.SERIOUS.getType()){
				rightModerate++;
			}else if(visionRight<VisionEnums.SERIOUS.getType()){
				rightSerious++;
			}
			
			if(visionavg>VisionEnums.MILD.getType()) {
				avgGood++;
			}else if(visionavg<VisionEnums.MILD.getType()&&visionavg>VisionEnums.MODERATE.getType()) {
				avgMild++;
			}else if(visionavg<VisionEnums.MODERATE.getType()&&visionavg>VisionEnums.SERIOUS.getType()){
				avgModerate++;
			}else if(visionavg<VisionEnums.SERIOUS.getType()){
				avgSerious++;
			}
		}
		
		left.add(new StatisticsVO("良好", leftGood,"leftGood",new HashMap<String,String>() {{put("color", "#0793FF");}}));
		left.add(new StatisticsVO("轻度不良", leftMild,"leftMild",new HashMap<String,String>() {{put("color", "#FFAA07");}}));
		left.add(new StatisticsVO("中度不良", leftModerate,"leftModerate",new HashMap<String,String>() {{put("color", "#FF0724");}}));
		left.add(new StatisticsVO("重度不良", leftSerious,"leftSerious",new HashMap<String,String>() {{put("color", "#6F0427");}}));
		end.add(left);
		right.add(new StatisticsVO("良好", rightGood,"rightGood",new HashMap<String,String>() {{put("color", "#0793FF");}}));
		right.add(new StatisticsVO("轻度不良", rightMild,"rightMild",new HashMap<String,String>() {{put("color", "#FFAA07");}}));
		right.add(new StatisticsVO("中度不良", rightModerate,"rightModerate",new HashMap<String,String>() {{put("color", "#FF0724");}}));
		right.add(new StatisticsVO("重度不良", rightSerious,"rightSerious",new HashMap<String,String>() {{put("color", "#6F0427");}}));
		end.add(right);
		avg.add(new StatisticsVO("良好", avgGood,"avgGood",new HashMap<String,String>() {{put("color", "#0793FF");}}));
		avg.add(new StatisticsVO("轻度不良", avgMild,"avgMild",new HashMap<String,String>() {{put("color", "#FFAA07");}}));
		avg.add(new StatisticsVO("中度不良", avgModerate,"avgModerate",new HashMap<String,String>() {{put("color", "#FF0724");}}));
		avg.add(new StatisticsVO("重度不良", avgSerious,"avgSerious",new HashMap<String,String>() {{put("color", "#6F0427");}}));
		end.add(avg);
		return ResultVOUtil.success(end);
	}
	
	
	
	/* 
	 * 批量导入检测数据
	 */
	@Override
	@Transactional
	public ResultVO readRecordExcel(MultipartFile file,Map<String, String> params) {
		List<RecordMapper> recordInfo = readexcel.getRecordInfo(file);
		if(recordInfo==null) {
			System.out.println("读取excel数据返回值是空的");
			return ResultVOUtil.error(ResultEnum.READEXCEL_ERROR.getStatus(), ResultEnum.READEXCEL_ERROR.READEXCEL_ERROR.getMessage());
		}
		
        for (RecordMapper recordMapper : recordInfo) {
        	record_dao.save(recordMapper);
        }
        return ResultVOUtil.success();
	}

	/* 
	 * 文件下载（流方式）（暂停使用）
	 */
	@Override
	public void download(HttpServletResponse response) {
		DownTemplateUtil.downTemplate(response, PATH, FILENAME);
	}

	@Override
	public ResultVO classInfiltration(Map<String, String> params) {
		Integer classId = Integer.valueOf(params.get("classId"));
		//哪个眼
		int type =Integer.valueOf(params.get("type"));
		//哪个状态
		int state=Integer.valueOf(params.get("state"));
		Double visionLeft=0d,visionRight=0d,visionavg = 0d;
		List<StudentMapper> end = new ArrayList<>();
		List<StudentMapper> allStudent = student_dao.findByClassesId(new ArrayList<Integer>() {{add(classId);}});
		for (StudentMapper student : allStudent) {
			visionLeft=student.getVisionLeftStr();
			visionRight=student.getVisionRightStr();
			if(visionLeft==null||visionRight==null)continue;
			visionavg = (visionLeft+visionRight)/2;
			if(state==1) {
				if(type==1) {
					if(visionLeft>VisionEnums.MILD.getType())end.add(student);
					continue;
				}else if(type==2) {
					if(visionRight>VisionEnums.MILD.getType())end.add(student);
					continue;
				}else if(type==3) {
					if(visionavg>VisionEnums.MILD.getType())end.add(student);
					continue;
				}
			}else if(state==2) {
				if(type==1) {
					if(visionLeft<VisionEnums.MILD.getType()&&visionLeft>VisionEnums.MODERATE.getType())end.add(student);
					continue;
				}else if(type==2) {
					if(visionRight<VisionEnums.MILD.getType()&&visionRight>VisionEnums.MODERATE.getType())end.add(student);
					continue;
				}else if(type==3) {
					if(visionavg<VisionEnums.MILD.getType()&&visionavg>VisionEnums.MODERATE.getType())end.add(student);
					continue;
				}
			}else if(state==3) {
				if(type==1) {
					if(visionLeft<VisionEnums.MODERATE.getType()&&visionLeft>VisionEnums.SERIOUS.getType())end.add(student);
					continue;
				}else if(type==2) {
					if(visionRight<VisionEnums.MODERATE.getType()&&visionRight>VisionEnums.SERIOUS.getType())end.add(student);
					continue;
				}else if(type==3) {
					if(visionavg<VisionEnums.MODERATE.getType()&&visionavg>VisionEnums.SERIOUS.getType())end.add(student);
					continue;
				}
			}else if(state==4) {
				if(type==1) {
					if(visionLeft<VisionEnums.SERIOUS.getType())end.add(student);
				}else if(type==2) {
					if(visionRight<VisionEnums.SERIOUS.getType())end.add(student);
				}else if(type==3) {
					if(visionavg<VisionEnums.SERIOUS.getType())end.add(student);
				}
			}
		}
		return ResultVOUtil.success(end);
	}

	@Override
	public ResultVO gradeInfiltration(Map<String, String> params) {
		//哪个眼
		int type =Integer.valueOf(params.get("type"));
		//哪个状态
		int state=Integer.valueOf(params.get("state"));
		Double visionLeft=0d,visionRight=0d,visionavg = 0d;
		Integer value=0;
		Integer schoolId = Integer.valueOf(params.get("schoolId"));
		List<Map<String, String>> end = new ArrayList<>();
		List<Integer> grades = new ArrayList<Integer>() {{add(1);add(2);add(3);add(4);add(5);add(6);}};
		for (Integer grade : grades) {
			List<Integer> classids = class_dao.findIdBySchoolIdAndGrade(schoolId, grade);
			if(classids==null||classids.size()==0)continue;
			List<StudentMapper> allStudent = student_dao.findByClassesId(classids);
			value=0;
			for (StudentMapper student : allStudent) {
				visionLeft=student.getVisionLeftStr();
				visionRight=student.getVisionRightStr();
				if(visionLeft==null||visionRight==null)continue;
				visionavg = (visionLeft+visionRight)/2;
				if(state==1) {
					if(type==1) {
						if(visionLeft>VisionEnums.MILD.getType())value++;
					}else if(type==2) {
						if(visionRight>VisionEnums.MILD.getType())value++;
					}else if(type==3) {
						if(visionavg>VisionEnums.MILD.getType())value++;
					}
				}else if(state==2) {
					if(type==1) {
						if(visionLeft<VisionEnums.MILD.getType()&&visionLeft>VisionEnums.MODERATE.getType())value++;
					}else if(type==2) {
						if(visionRight<VisionEnums.MILD.getType()&&visionRight>VisionEnums.MODERATE.getType())value++;
					}else if(type==3) {
						if(visionavg<VisionEnums.MILD.getType()&&visionavg>VisionEnums.MODERATE.getType())value++;
					}
				}else if(state==3) {
					if(type==1) {
						if(visionLeft<VisionEnums.MODERATE.getType()&&visionLeft>VisionEnums.SERIOUS.getType())value++;
					}else if(type==2) {
						if(visionRight<VisionEnums.MODERATE.getType()&&visionRight>VisionEnums.SERIOUS.getType())value++;
					}else if(type==3) {
						if(visionavg<VisionEnums.MODERATE.getType()&&visionavg>VisionEnums.SERIOUS.getType())value++;
					}
				}else if(state==4) {
					if(type==1) {
						if(visionLeft<VisionEnums.SERIOUS.getType())value++;
					}else if(type==2) {
						if(visionRight<VisionEnums.SERIOUS.getType())value++;
					}else if(type==3) {
						if(visionavg<VisionEnums.SERIOUS.getType())value++;
					}
				}
			}
			String number = value.toString();
			end.add(new HashMap<String,String>() {{
				put("value", number+"");
				put("grade", chengeGrade(grade));
			}});
		}
		return ResultVOUtil.success(end);
	}
	
	public String chengeGrade(Integer grade) {
		String gradeStr = null;
		switch (grade) {
		case 1:
			gradeStr = "一年級";
			break;
		case 2:
			gradeStr = "二年級";
			break;
		case 3:
			gradeStr = "三年級";
			break;
		case 4:
			gradeStr = "四年級";
			break;
		case 5:
			gradeStr = "五年級";
			break;
		case 6:
			gradeStr = "六年級";
			break;
		}
		return gradeStr;
	}
	
	@Override
	public ResultVO grade2ClassInfiltration(Map<String, String> params) {
		//哪个眼
		int type =Integer.valueOf(params.get("type"));
		//哪个状态
		int state=Integer.valueOf(params.get("state"));
		Double visionLeft=0d,visionRight=0d,visionavg = 0d;
		Integer value=0;
		Integer schoolId = Integer.valueOf(params.get("schoolId"));
		Integer grade = Integer.valueOf(params.get("grade"));
		List<Map<String, String>> end = new ArrayList<>();
		List<Integer> classids = class_dao.findIdBySchoolIdAndGrade(schoolId, grade);
		for (Integer integer : classids) {
			List<StudentMapper> allStudent = student_dao.findByClassesId(integer);
			value=0;
			for (StudentMapper student : allStudent) {
				visionLeft=student.getVisionLeftStr();
				visionRight=student.getVisionRightStr();
				if(visionLeft==null||visionRight==null)continue;
				visionavg = (visionLeft+visionRight)/2;
				if(state==1) {
					if(type==1) {
						if(visionLeft>VisionEnums.MILD.getType())value++;
					}else if(type==2) {
						if(visionRight>VisionEnums.MILD.getType())value++;
					}else if(type==3) {
						if(visionavg>VisionEnums.MILD.getType())value++;
					}
				}else if(state==2) {
					if(type==1) {
						if(visionLeft<VisionEnums.MILD.getType()&&visionLeft>VisionEnums.MODERATE.getType())value++;
					}else if(type==2) {
						if(visionRight<VisionEnums.MILD.getType()&&visionRight>VisionEnums.MODERATE.getType())value++;
					}else if(type==3) {
						if(visionavg<VisionEnums.MILD.getType()&&visionavg>VisionEnums.MODERATE.getType())value++;
					}
				}else if(state==3) {
					if(type==1) {
						if(visionLeft<VisionEnums.MODERATE.getType()&&visionLeft>VisionEnums.SERIOUS.getType())value++;
					}else if(type==2) {
						if(visionRight<VisionEnums.MODERATE.getType()&&visionRight>VisionEnums.SERIOUS.getType())value++;
					}else if(type==3) {
						if(visionavg<VisionEnums.MODERATE.getType()&&visionavg>VisionEnums.SERIOUS.getType())value++;
					}
				}else if(state==4) {
					if(type==1) {
						if(visionLeft<VisionEnums.SERIOUS.getType())value++;
					}else if(type==2) {
						if(visionRight<VisionEnums.SERIOUS.getType())value++;
					}else if(type==3) {
						if(visionavg<VisionEnums.SERIOUS.getType())value++;
					}
				}
			}
			String number = value.toString();
			end.add(new HashMap<String,String>() {{
				put("classId", allStudent.get(0).getClassesId()+"");
				put("schoolNmae", allStudent.get(0).getSchoolName());
				put("name", allStudent.get(0).getClassesName());
				put("value", number+"");
			}});
		}
		return ResultVOUtil.success(end);
	}
	
	
	
	
}
