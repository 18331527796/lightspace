package com.threefriend.schoolclient.service.impl;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.threefriend.lightspace.enums.ResultEnum;
import com.threefriend.lightspace.enums.UrlEnums;
import com.threefriend.lightspace.mapper.ClassesMapper;
import com.threefriend.lightspace.mapper.DiopterMapper;
import com.threefriend.lightspace.mapper.StudentMapper;
import com.threefriend.lightspace.mapper.StudentWordMapper;
import com.threefriend.lightspace.mapper.UserMapper;
import com.threefriend.lightspace.mapper.schoolclient.SchoolClassesMapper;
import com.threefriend.lightspace.mapper.schoolclient.SchoolSemesterMapper;
import com.threefriend.lightspace.mapper.schoolclient.SchoolStudentRecordMapper;
import com.threefriend.lightspace.mapper.schoolclient.UserSchoolsMapper;
import com.threefriend.lightspace.mapper.xcx.ScreeningMapper;
import com.threefriend.lightspace.mapper.xcx.ScreeningWearMapper;
import com.threefriend.lightspace.repository.ClassesRepository;
import com.threefriend.lightspace.repository.DiopterRepository;
import com.threefriend.lightspace.repository.SchoolRepository;
import com.threefriend.lightspace.repository.ScreeningRepository;
import com.threefriend.lightspace.repository.ScreeningWearRepository;
import com.threefriend.lightspace.repository.StudentRepository;
import com.threefriend.lightspace.repository.StudentWordRepository;
import com.threefriend.lightspace.repository.UserRepository;
import com.threefriend.lightspace.repository.schoolclient.SchoolClassRepository;
import com.threefriend.lightspace.repository.schoolclient.SchoolSemesterRepository;
import com.threefriend.lightspace.repository.schoolclient.SchoolStudentRecordRepository;
import com.threefriend.lightspace.repository.schoolclient.UserSchoolsRepository;
import com.threefriend.lightspace.service.Impl.ReadDiopterExcel;
import com.threefriend.lightspace.service.Impl.ReportServiceImpl;
import com.threefriend.lightspace.util.ExcelUtil;
import com.threefriend.lightspace.util.JfreeUtil;
import com.threefriend.lightspace.util.ListUtils;
import com.threefriend.lightspace.util.OptotypeUtils;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.util.WordUtil;
import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.vo.SchoolRecordVO;
import com.threefriend.lightspace.vo.ViewGradeReportVO;
import com.threefriend.schoolclient.service.SchoolRecordService;

import cn.afterturn.easypoi.entity.ImageEntity;

@Service
public class SchoolRecordServiceImpl implements SchoolRecordService{
	
	private final String[] diopterArray = { "学校名称", "班级名称", "学生姓名", "右眼球镜", "左眼球镜", "右眼柱镜", "左眼柱镜", 
											"右眼轴位", "左眼轴位", "右眼水平眼位", "左眼水平眼位", "右眼垂直眼位", "左眼垂直眼位","左眼视力","右眼视力","左眼屈光不正","右眼屈光不正","左眼串镜","右眼串镜"};
	
	@Autowired
	private StudentRepository student_dao;
	@Autowired
	private ClassesRepository class_dao;
	@Autowired
	private ScreeningRepository screening_dao;
	@Autowired
	private ScreeningWearRepository screening_wear_dao;
	@Autowired
	private SchoolClassRepository school_class_dao;
	@Autowired
	private SchoolSemesterRepository school_semester_dao;
	@Autowired
	private SchoolStudentRecordRepository school_student_record_dao;
	@Autowired
	private SchoolRepository school_dao;
	@Autowired
	private DiopterRepository diopter_dao;
	@Autowired
	private UserSchoolsRepository u_s_dao;
	@Autowired
	private ReadDiopterExcel readexcel;

	@Override
	public ResultVO screeningList(Map<String, String> params, HttpSession session){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Integer schoolId = Integer.valueOf(session.getAttribute("schoolId").toString());
		Date begintime = null,endtime = null;
		if(!StringUtils.isEmpty(params.get("begin"))) {
			System.out.println(params.get("begin")+"---"+params.get("end"));
			try {
				begintime = sdf.parse(params.get("begin"));
				endtime = sdf.parse(params.get("end"));
			} catch (ParseException e) {
				System.out.println("时间解析出错");
			}
		}
		Map<String, Object> end = new HashMap<>();
		List<ScreeningMapper> list = null;
		Integer page = 0;
		int count = 0;
		if(!StringUtils.isEmpty(params.get("page")))page = (Integer.valueOf(params.get("page"))-1)*10;
		String type = params.get("type");
		end.put("size", 10);
		end.put("number", (StringUtils.isEmpty(params.get("page")))?0:Integer.valueOf(params.get("page"))-1);
		if("student".equals(type)) {
			if(begintime!=null) {
				list = screening_dao.findByNameOrderByGenTimeDesc("%"+params.get("name")+"%",schoolId,begintime,endtime,page, 10);
				count = screening_dao.findcountByName("%"+params.get("name")+"%",schoolId,begintime,endtime);
			}else {
				list = screening_dao.findByNameOrderByGenTimeDesc("%"+params.get("name")+"%",schoolId,page, 10);
				count = screening_dao.findcountByName("%"+params.get("name")+"%",schoolId);
			}
			end.put("totalElements", count);
			end.put("content", list);
			return (list.size()!=0)?ResultVOUtil.success(end):ResultVOUtil.error(ResultEnum.STUDENTSIZE_NULL.getStatus(), ResultEnum.STUDENTSIZE_NULL.getMessage());
		}
		if("class".equals(type)) {
			if(begintime!=null) {
				list = screening_dao.findByClassIdOrderByGenTimeDesc(Integer.valueOf(params.get("id")),begintime,endtime,page, 10);
				count = screening_dao.findcountByClassId(Integer.valueOf(params.get("id")),begintime,endtime);
			}else {
				list = screening_dao.findByClassIdOrderByGenTimeDesc(Integer.valueOf(params.get("id")),page, 10);
				count = screening_dao.findcountByClassId(Integer.valueOf(params.get("id")));
			}
			end.put("totalElements", count);
			end.put("content", list);
			return (list.size()!=0)?ResultVOUtil.success(end):ResultVOUtil.error(ResultEnum.STUDENTSIZE_NULL.getStatus(), ResultEnum.STUDENTSIZE_NULL.getMessage());
		}
		if(schoolId == 0) {
			List<Integer> schoolIds = new ArrayList<>();
			Integer regionId = Integer.valueOf(session.getAttribute("regionId").toString());
			schoolIds=school_dao.findIdByRegionId(regionId);
			if(begintime!=null) {
				list = screening_dao.findBySchoolIdOrderByGenTimeDesc(schoolIds,begintime,endtime,page, 10);
				count = screening_dao.findcountBySchoolId(schoolIds,begintime,endtime);
			}else {
				list = screening_dao.findBySchoolIdOrderByGenTimeDesc(schoolIds,page, 10);
				count = screening_dao.findcountBySchoolId(schoolIds);
			}
		}else if(schoolId == -1){
			List<Integer> schoolIds = new ArrayList<>();
			Integer userId = Integer.valueOf(session.getAttribute("userId").toString());
			List<UserSchoolsMapper> u_s_list = u_s_dao.findByUserId(userId);
			for (UserSchoolsMapper userSchoolsMapper : u_s_list) {
				schoolIds.add(userSchoolsMapper.getSchoolId());
			}
			if(begintime!=null) {
				list = screening_dao.findBySchoolIdOrderByGenTimeDesc(schoolIds,begintime,endtime,page, 10);
				count = screening_dao.findcountBySchoolId(schoolIds,begintime,endtime);
			}else {
				list = screening_dao.findBySchoolIdOrderByGenTimeDesc(schoolIds,page, 10);
				count = screening_dao.findcountBySchoolId(schoolIds);
			}
		}else {
			if(begintime!=null) {
				list = screening_dao.findBySchoolIdOrderByGenTimeDesc(schoolId,begintime,endtime,page, 10);
				count = screening_dao.findcountBySchoolId(schoolId,begintime,endtime);
			}else {
				list = screening_dao.findBySchoolIdOrderByGenTimeDesc(schoolId,page, 10);
				count = screening_dao.findcountBySchoolId(schoolId);
			}
		}
		end.put("totalElements", count);
		end.put("content", list);
		return (list.size()!=0)?ResultVOUtil.success(end):ResultVOUtil.error(ResultEnum.STUDENTSIZE_NULL.getStatus(), ResultEnum.STUDENTSIZE_NULL.getMessage());
	}

	@Override
	public ResultVO screeningWearList(Map<String, String> params, HttpSession session) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Integer schoolId = Integer.valueOf(session.getAttribute("schoolId").toString());
		Date begintime = null,endtime = null;
		if(!StringUtils.isEmpty(params.get("begin"))) {
			System.out.println(params.get("begin")+"---"+params.get("end"));
			try {
				begintime = sdf.parse(params.get("begin"));
				endtime = sdf.parse(params.get("end"));
			} catch (ParseException e) {
				System.out.println("时间解析出错");
			}
		}
		Map<String, Object> end = new HashMap<>();
		List<ScreeningWearMapper> list = null;
		Integer page = 0;
		int count = 0 ;
		if(!StringUtils.isEmpty(params.get("page")))page = (Integer.valueOf(params.get("page"))-1)*10;
		String type = params.get("type");
		end.put("size", 10);
		end.put("number", (StringUtils.isEmpty(params.get("page")))?0:Integer.valueOf(params.get("page"))-1);
		
		
		if("student".equals(type)) {
			if(begintime!=null) {
				list = screening_wear_dao.findByNameOrderByGenTimeDesc("%"+params.get("name")+"%",schoolId,begintime,endtime,page, 10);
				count = screening_wear_dao.findcountByName("%"+params.get("name")+"%",schoolId,begintime,endtime);
			}else {
				list = screening_wear_dao.findByNameOrderByGenTimeDesc("%"+params.get("name")+"%",schoolId,page, 10);
				count = screening_wear_dao.findcountByName("%"+params.get("name")+"%",schoolId);
			}
			end.put("totalElements", count);
			end.put("content", list);
			return (list.size()!=0)?ResultVOUtil.success(end):ResultVOUtil.error(ResultEnum.STUDENTSIZE_NULL.getStatus(), ResultEnum.STUDENTSIZE_NULL.getMessage());
		}
		if("class".equals(type)) {
			if(begintime!=null) {
				list = screening_wear_dao.findByClassIdOrderByGenTimeDesc(Integer.valueOf(params.get("id")),begintime,endtime,page, 10);
				count = screening_wear_dao.findcountByClassId(Integer.valueOf(params.get("id")),begintime,endtime);
			}else {
				list = screening_wear_dao.findByClassIdOrderByGenTimeDesc(Integer.valueOf(params.get("id")),page, 10);
				count = screening_wear_dao.findcountByClassId(Integer.valueOf(params.get("id")));
			}
			end.put("totalElements", count);
			end.put("content", list);
			return (list.size()!=0)?ResultVOUtil.success(end):ResultVOUtil.error(ResultEnum.STUDENTSIZE_NULL.getStatus(), ResultEnum.STUDENTSIZE_NULL.getMessage());
		}
		if(schoolId == 0) {
			List<Integer> schoolIds = new ArrayList<>();
			Integer regionId = Integer.valueOf(session.getAttribute("regionId").toString());
			schoolIds=school_dao.findIdByRegionId(regionId);
			if(begintime!=null) {
				list = screening_wear_dao.findBySchoolIdOrderByGenTimeDesc(schoolIds,begintime,endtime,page, 10);
				count = screening_wear_dao.findcountBySchoolId(schoolIds,begintime,endtime);
			}else {
				list = screening_wear_dao.findBySchoolIdOrderByGenTimeDesc(schoolIds,page, 10);
				count = screening_wear_dao.findcountBySchoolId(schoolIds);
			}
		}else if(schoolId == -1){
			List<Integer> schoolIds = new ArrayList<>();
			Integer userId = Integer.valueOf(session.getAttribute("userId").toString());
			List<UserSchoolsMapper> u_s_list = u_s_dao.findByUserId(userId);
			for (UserSchoolsMapper userSchoolsMapper : u_s_list) {
				schoolIds.add(userSchoolsMapper.getSchoolId());
			}
			if(begintime!=null) {
				list = screening_wear_dao.findBySchoolIdOrderByGenTimeDesc(schoolIds,begintime,endtime,page, 10);
				count = screening_wear_dao.findcountBySchoolId(schoolIds,begintime,endtime);
			}else {
				list = screening_wear_dao.findBySchoolIdOrderByGenTimeDesc(schoolIds,page, 10);
				count = screening_wear_dao.findcountBySchoolId(schoolIds);
			}
		}else {
			if(begintime!=null) {
				list = screening_wear_dao.findBySchoolIdOrderByGenTimeDesc(schoolId,begintime,endtime,page, 10);
				count = screening_wear_dao.findcountBySchoolId(schoolId,begintime,endtime);
			}else {
				list = screening_wear_dao.findBySchoolIdOrderByGenTimeDesc(schoolId,page, 10);
				count = screening_wear_dao.findcountBySchoolId(schoolId);
			}
		}
		end.put("totalElements", count);
		end.put("content", list);
		return (list.size()!=0)?ResultVOUtil.success(end):ResultVOUtil.error(ResultEnum.STUDENTSIZE_NULL.getStatus(), ResultEnum.STUDENTSIZE_NULL.getMessage());
	
	}

	@Override
	public ResultVO screeningByStudentId(Map<String, String> params) {
		Integer page = 0 ;
		if(!StringUtils.isEmpty(params.get("page"))) page = Integer.valueOf(params.get("page")) - 1;
		Integer studentId = Integer.valueOf(params.get("studentId"));
		Page<ScreeningMapper> list = screening_dao.findByStudentIdOrderByGenTimeDesc(studentId,PageRequest.of(page, 10));
		return (list.getContent().size()!=0)?ResultVOUtil.success(list):ResultVOUtil.error(ResultEnum.STUDENTSIZE_NULL.getStatus(), ResultEnum.STUDENTSIZE_NULL.getMessage());
	}

	@Override
	public ResultVO screeningWearByStudentId(Map<String, String> params) {
		int page = 0 ;
		if(!StringUtils.isEmpty(params.get("page")))page = Integer.valueOf(params.get("page"))-1;
		Integer studentId = Integer.valueOf(params.get("studentId"));
		Page<ScreeningWearMapper> list = screening_wear_dao.findByStudentIdOrderByGenTimeDesc(studentId,PageRequest.of(page, 10));
		return (list.getContent().size()!=0)?ResultVOUtil.success(list):ResultVOUtil.error(ResultEnum.STUDENTSIZE_NULL.getStatus(), ResultEnum.STUDENTSIZE_NULL.getMessage());
	}

	@Override
	public ResultVO deleteScreening(Map<String, String> params) {
		screening_dao.deleteById(Integer.valueOf(params.get("id")));
		return ResultVOUtil.success();
	}

	@Override
	public ResultVO deleteScreeningWear(Map<String, String> params) {
		screening_wear_dao.deleteById(Integer.valueOf(params.get("id")));
		return ResultVOUtil.success();
	}


	@Override
	public ResultVO recordSurvey(Map<String, String> params, HttpSession session) {
		DecimalFormat df=new DecimalFormat("0.00");
		Integer schoolId = Integer.valueOf(session.getAttribute("schoolId").toString());
		List<SchoolStudentRecordMapper> students = null;
		Map<String, Object> end = new HashMap<>();
		List<SchoolRecordVO> gradeList = new ArrayList<SchoolRecordVO>();
		List<Integer> gradeMyopiaList = new ArrayList<>();
		List<String> gradeAvgList = new ArrayList<>();
		List<Integer> schoolIds = new ArrayList<>();
		if(schoolId==0) {
			Integer regionId = Integer.valueOf(session.getAttribute("regionId").toString());
			schoolIds=school_dao.findIdByRegionId(regionId);
		}else if(schoolId == -1){
			Integer userId = Integer.valueOf(session.getAttribute("userId").toString());
			List<UserSchoolsMapper> u_s_list = u_s_dao.findByUserId(userId);
			for (UserSchoolsMapper userSchoolsMapper : u_s_list) {
				schoolIds.add(userSchoolsMapper.getSchoolId());
			}
		}else {
			schoolIds.add(schoolId);
		}
		
		String year = params.get("year");
		Integer semester = Integer.valueOf(params.get("semester"));
		
		List<Integer> SemesterPos = school_semester_dao.findIdByYearAndSemesterAndSchoolIdIn(year,semester,schoolIds);
		if(SemesterPos.size()!=schoolIds.size()) return ResultVOUtil.success();
		
		int gradeGood = 0 , gradeMild = 0, gradeModerate = 0 , gradeSerious = 0 , gradeAll = 0 , gradeMyopia = 0,
			classGood = 0 , classMild = 0, classModerate = 0 , classSerious = 0 , classAll = 0 , classMyopia = 0;
		Double AVG = 0d , SUM = 0d; 
		
		
		for (int i = 1; i < 7; i++) {
			gradeGood = 0 ; gradeMild = 0; gradeModerate = 0 ; gradeSerious = 0 ; gradeAll = 0 ; gradeMyopia = 0;
			SUM = 0d;
			SchoolRecordVO grade = new SchoolRecordVO();
			grade.setName(i+"");
			
				List<SchoolClassesMapper> allclass = school_class_dao.findBySemesterIdInAndGradeOrderByClassNumber(SemesterPos,i);
				//当这个年级没有班级时
				if(allclass.size()<1||allclass==null) {
					grade.setAll(0);
					grade.setGood(0);
					grade.setMild(0);
					grade.setModerate(0);
					grade.setMyopia(0);
					grade.setMyopiaRate("0%");
					grade.setSerious(0);
					grade.setChildren(null);
					gradeList.add(grade);
					gradeMyopiaList.add(0);
					gradeAvgList.add("0");
					continue;
				}
				
				for (SchoolClassesMapper classpo : allclass) {
					SchoolRecordVO classvo = new SchoolRecordVO();
					classvo.setName(classpo.getSchoolName()+classpo.getName());
					
					classGood = 0 ; classMild = 0; classModerate = 0 ; classSerious = 0 ; classAll = 0 ; classMyopia = 0;
					students = school_student_record_dao.findByClassIdAndSemesterIn(classpo.getClassId(),SemesterPos);
					classAll=students.size();
					gradeAll+=classAll;
					
					for (SchoolStudentRecordMapper po : students) {
						
						if(po.getVisionLeftStr()>=po.getVisionRightStr()) {
							AVG = po.getVisionLeftStr();
						}else {
							AVG = po.getVisionRightStr();
						}
						
						SUM+=AVG;
						if(AVG>=1.0) {
							classGood++;
							gradeGood++;
						}else if(AVG<1.0 && AVG >= 0.8 ) {
							classMild++;
							gradeMild++;
							classMyopia++;
							gradeMyopia++;
						}else if(AVG<0.8 && AVG >= 0.4) {
							classModerate++;
							gradeModerate++;
							classMyopia++;
							gradeMyopia++;
						}else {
							classSerious++;
							gradeSerious++;
							classMyopia++;
							gradeMyopia++;
						}
						
					}
					classvo.setClassId(classpo.getClassId());
					classvo.setAll(classAll);
					classvo.setGood(classGood);
					classvo.setMild(classMild);
					classvo.setModerate(classModerate);
					classvo.setSerious(classSerious);
					classvo.setMyopia(classMyopia);
					classvo.setMyopiaRate(df.format(((float)classMyopia/(float)classAll)*100)+"%");
					grade.getChildren().add(classvo);
				}
			
			grade.setAll(gradeAll);
			grade.setGood(gradeGood);
			grade.setMild(gradeMild);
			grade.setModerate(gradeModerate);
			grade.setSerious(gradeSerious);
			grade.setMyopia(gradeMyopia);
			grade.setMyopiaRate(df.format(((float)gradeMyopia/(float)gradeAll)*100)+"%");
			gradeList.add(grade);
			gradeMyopiaList.add(gradeMyopia);
			gradeAvgList.add(df.format(SUM/gradeAll));
		}
		
		end.put("gradeMyopiaList", gradeMyopiaList);
		end.put("gradeAvgList", gradeAvgList);
		end.put("recordSurvey", gradeList);
		
		return ResultVOUtil.success(end);
	}
	
	
	@Override
	public ResultVO RecordVisionGrade(Map<String, String> params,HttpSession session) {
		
		Integer schoolId =Integer.valueOf(session.getAttribute("schoolId").toString());
		
		DecimalFormat df=new DecimalFormat("0.00");
		
		List<Integer> schoolIds = new ArrayList<>();
		if(schoolId==0) {
			Integer regionId = Integer.valueOf(session.getAttribute("regionId").toString());
			schoolIds=school_dao.findIdByRegionId(regionId);
		}else if(schoolId == -1){
			Integer userId = Integer.valueOf(session.getAttribute("userId").toString());
			List<UserSchoolsMapper> u_s_list = u_s_dao.findByUserId(userId);
			for (UserSchoolsMapper userSchoolsMapper : u_s_list) {
				schoolIds.add(userSchoolsMapper.getSchoolId());
			}
		}else {
			schoolIds.add(schoolId);
		}
		
		String year = params.get("year");
		Integer semester = Integer.valueOf(params.get("semester"));
		
		List<Integer> SemesterPos = school_semester_dao.findIdByYearAndSemesterAndSchoolIdIn(year,semester,schoolIds);
		if(SemesterPos.size()!=schoolIds.size()) return ResultVOUtil.success();
		
		List<Integer> allclass = null;
		List<SchoolStudentRecordMapper> students = null;
		int studentNumber = 0;
		
		
		List<List<String>> end = new ArrayList<>();
		List<String> goodList = new ArrayList<>();
		List<String> mildList = new ArrayList<>();
		List<String> moderateList = new ArrayList<>();
		List<String> seriousList = new ArrayList<>();
		
		for (int i = 1; i < 7; i++) {
			allclass = new ArrayList<>();
			int avgGood= 0,avgMild= 0,avgModerate= 0,avgSerious= 0;
			Double LEFT = 0d , RIGHT = 0d , AVG = 0d;
			
				List<SchoolClassesMapper> classlist = school_class_dao.findBySemesterIdInAndGradeOrderByClassNumber(SemesterPos,i);
				for (SchoolClassesMapper schoolClassesMapper : classlist) {
					allclass.add(schoolClassesMapper.getClassId());
				}
				
				
				if(allclass.size()<1||allclass==null) {
					goodList.add("0");
					mildList.add("0");
					moderateList.add("0");
					seriousList.add("0");
					continue;
				}
				students = school_student_record_dao.findBySemesterInAndClassIdIn(SemesterPos,allclass);
				studentNumber =students.size();
				for (SchoolStudentRecordMapper s : students) {
					
					LEFT = s.getVisionLeftStr();
					RIGHT = s.getVisionRightStr();
					if(LEFT <= RIGHT) {
						AVG = LEFT;
					}else {
						AVG = RIGHT;
					}
					
					if(AVG>=1.0) {
						avgGood++;
					}else if(AVG >=0.6 && AVG <1.0 ) {
						avgMild++;
					}else if(AVG >=0.1 && AVG <0.4){
						avgModerate++;
					}else {
						avgSerious++;
					}
				}
			goodList.add(df.format(((float)avgGood/((float)studentNumber))*100));
			mildList.add(df.format(((float)avgMild/((float)studentNumber))*100));
			moderateList.add(df.format(((float)avgModerate/((float)studentNumber))*100));
			seriousList.add(df.format(((float)avgSerious/((float)studentNumber))*100));
		}
		
		end.add(goodList);
		end.add(mildList);
		end.add(moderateList);
		end.add(seriousList);
		
		return ResultVOUtil.success(end);
	}

	@Override
	public ResultVO ViewGradeReport(Map<String, String> params, HttpSession session) {
		
		Integer schoolId =Integer.valueOf(session.getAttribute("schoolId").toString());
		
		DecimalFormat df=new DecimalFormat("0.00");
		
		Integer grade = Integer.valueOf(params.get("grade"));
		List<Integer> schoolIds = new ArrayList<>();
		if(schoolId==0) {
			Integer regionId = Integer.valueOf(session.getAttribute("regionId").toString());
			schoolIds=school_dao.findIdByRegionId(regionId);
		}else if(schoolId == -1){
			Integer userId = Integer.valueOf(session.getAttribute("userId").toString());
			List<UserSchoolsMapper> u_s_list = u_s_dao.findByUserId(userId);
			for (UserSchoolsMapper userSchoolsMapper : u_s_list) {
				schoolIds.add(userSchoolsMapper.getSchoolId());
			}
		}else {
			schoolIds.add(schoolId);
		}
		
		String year = params.get("year");
		Integer semester = Integer.valueOf(params.get("semester"));
		
		List<Integer> SemesterPos = school_semester_dao.findIdByYearAndSemesterAndSchoolIdIn(year,semester,schoolIds);
		if(SemesterPos.size()!=schoolIds.size()) return ResultVOUtil.success();
		
		int gradeTested=0, gradeSum=0, gradeBad=0;
		Double AVG = 0d;
		List<Integer > allclass = new ArrayList<>();
		List<Map<String, Object>> ViewGradeReportVOList = new ArrayList<>();
		Map<String, Object> ViewGradeReportVOmap=null;
		Map<String, Object> end = new HashMap<>();
		List<ViewGradeReportVO> voList = null;
		ViewGradeReportVO sumVO=null; 
		ViewGradeReportVO boyVO=null; 
		ViewGradeReportVO girlVO=null; 
		
			List<SchoolClassesMapper> classlist = school_class_dao.findBySemesterIdInAndGradeOrderByClassNumber(SemesterPos,grade);
			for (SchoolClassesMapper schoolClassesMapper : classlist) {
				allclass.add(schoolClassesMapper.getClassId());
			}
			gradeSum += student_dao.countByClassesId(allclass);
			
			for (SchoolClassesMapper classId : classlist) {
				ViewGradeReportVOmap=new HashMap<>();
				voList = new ArrayList<>();
				sumVO= new ViewGradeReportVO(); 
				boyVO= new ViewGradeReportVO(); 
				girlVO= new ViewGradeReportVO(); 
				int sumAll=0, sumGood=0, sumMild=0, sumModerate=0, sumSerious=0,
						
				 	boyAll=0, boyGood=0, boyMild=0, boyModerate=0, boySerious=0,
				
				 	girlAll=0, girlGood=0, girlMild=0, girlModerate=0, girlSerious=0;
				
				List<SchoolStudentRecordMapper> allStudent = school_student_record_dao.findByClassIdAndSemesterIn(classId.getClassId(),SemesterPos);
				gradeTested += allStudent.size();
				
				for (SchoolStudentRecordMapper s : allStudent) {
					sumAll++;
					if(s.getVisionLeftStr()>=s.getVisionRightStr()) {
						AVG = s.getVisionLeftStr();
					}else {
						AVG = s.getVisionRightStr();
					}
					
					
					if(s.getGender()==0) {
						boyAll++;
						if(AVG>=1.0) {
							sumGood++;
							boyGood++;
						}else if(AVG<1.0 && AVG >= 0.8 ) {
							sumMild++;
							boyMild++;
							gradeBad++;
						}else if(AVG<0.8 && AVG >= 0.4) {
							sumModerate++;
							boyModerate++;
							gradeBad++;
						}else {
							sumSerious++;
							boySerious++;
							gradeBad++;
						}
					}else {
						girlAll++;
						if(AVG>=1.0) {
							sumGood++;
							girlGood++;
						}else if(AVG<1.0 && AVG >= 0.8 ) {
							sumMild++;
							girlMild++;
							gradeBad++;
						}else if(AVG<0.8 && AVG >= 0.4) {
							sumModerate++;
							girlModerate++;
							gradeBad++;
						}else {
							sumSerious++;
							girlSerious++;
							gradeBad++;
						}
					}
				}
				sumVO.setName(" 总计 ");
				sumVO.setAll(sumAll);
				sumVO.setGood(sumGood);
				sumVO.setMild(sumMild);
				sumVO.setModerate(sumModerate);
				sumVO.setSerious(sumSerious);
				sumVO.setPercentage(df.format(((float)(sumAll-sumGood)/(float)sumAll)*100)+"%");
				
				boyVO.setName(" 男生 ");
				boyVO.setAll(boyAll);
				boyVO.setGood(boyGood);
				boyVO.setMild(boyMild);
				boyVO.setModerate(boyModerate);
				boyVO.setSerious(boySerious);
				boyVO.setPercentage(df.format(((float)(boyAll-boyGood)/(float)boyAll)*100)+"%");
				
				girlVO.setName(" 女生 ");
				girlVO.setAll(girlAll);
				girlVO.setGood(girlGood);
				girlVO.setMild(girlMild);
				girlVO.setModerate(girlModerate);
				girlVO.setSerious(girlSerious);
				girlVO.setPercentage(df.format(((float)(girlAll-girlGood)/(float)girlAll)*100)+"%");
				
				voList.add(sumVO);
				voList.add(boyVO);
				voList.add(girlVO);
				
				ViewGradeReportVOmap.put("name", classId.getSchoolName()+" :"+classId.getClassNumber()+"班");
				ViewGradeReportVOmap.put("viewGradeReportVO", voList);
				ViewGradeReportVOList.add(ViewGradeReportVOmap);
			}			
		end.put("gradeTested", gradeTested);
		end.put("gradeSum", gradeSum);
		end.put("gradeBad", gradeBad);
		end.put("gradePercentage", df.format(((float)gradeBad/(float)gradeTested)*100)+"%");
		end.put("classList", ViewGradeReportVOList);
		return ResultVOUtil.success(end);
	}

	@Override
	public ResultVO ViewClassReport(Map<String, String> params, HttpSession session) {
		Integer classId = Integer.valueOf(params.get("classId"));
		
		Integer schoolId = class_dao.findById(classId).get().getSchoolId();
		DecimalFormat df=new DecimalFormat("0.00");
		
		String year = params.get("year");
		Integer semester = Integer.valueOf(params.get("semester"));
		
		SchoolSemesterMapper SemesterPo = school_semester_dao.findByYearAndSemesterAndSchoolId(year,semester,schoolId);
		if(SemesterPo==null) return ResultVOUtil.success();
		
		int gradeTested=0, gradeSum=0, gradeBad=0;
		Double AVG = 0d;
		Map<String, Object> end = new HashMap<>();
		List<ViewGradeReportVO> voList = new ArrayList<>();
		ViewGradeReportVO sumVO=new ViewGradeReportVO(); 
		ViewGradeReportVO boyVO=new ViewGradeReportVO(); 
		ViewGradeReportVO girlVO=new ViewGradeReportVO(); 

		int sumAll=0, sumGood=0, sumMild=0, sumModerate=0, sumSerious=0,
				
			 	boyAll=0, boyGood=0, boyMild=0, boyModerate=0, boySerious=0,
			
			 	girlAll=0, girlGood=0, girlMild=0, girlModerate=0, girlSerious=0;
		
		SchoolClassesMapper schoolclass = school_class_dao.findBySemesterIdAndClassId(SemesterPo.getId(),classId);	
		List<SchoolStudentRecordMapper> allStudent = school_student_record_dao.findByClassIdAndSemester(classId,SemesterPo.getId());
		gradeTested += allStudent.size();
		gradeSum += student_dao.countByClassesId(classId);
		for (SchoolStudentRecordMapper s : allStudent) {
			sumAll++;
			if(s.getVisionLeftStr()>=s.getVisionRightStr()) {
				AVG = s.getVisionLeftStr();
			}else {
				AVG = s.getVisionRightStr();
			}
			
			
			if(s.getGender()==0) {
				boyAll++;
				if(AVG>=1.0) {
					sumGood++;
					boyGood++;
				}else if(AVG<1.0 && AVG >= 0.8 ) {
					sumMild++;
					boyMild++;
					gradeBad++;
				}else if(AVG<0.8 && AVG >= 0.4) {
					sumModerate++;
					boyModerate++;
					gradeBad++;
				}else {
					sumSerious++;
					boySerious++;
					gradeBad++;
				}
			}else {
				girlAll++;
				if(AVG>=1.0) {
					sumGood++;
					girlGood++;
				}else if(AVG<1.0 && AVG >= 0.8 ) {
					sumMild++;
					girlMild++;
					gradeBad++;
				}else if(AVG<0.8 && AVG >= 0.4) {
					sumModerate++;
					girlModerate++;
					gradeBad++;
				}else {
					sumSerious++;
					girlSerious++;
					gradeBad++;
				}
			}
		}
		sumVO.setName("总计");
		sumVO.setAll(sumAll);
		sumVO.setGood(sumGood);
		sumVO.setMild(sumMild);
		sumVO.setModerate(sumModerate);
		sumVO.setSerious(sumSerious);
		sumVO.setPercentage(df.format(((float)(sumAll-sumGood)/(float)sumAll)*100)+"%");
		
		boyVO.setName("男生");
		boyVO.setAll(boyAll);
		boyVO.setGood(boyGood);
		boyVO.setMild(boyMild);
		boyVO.setModerate(boyModerate);
		boyVO.setSerious(boySerious);
		boyVO.setPercentage(df.format(((float)(boyAll-boyGood)/(float)boyAll)*100)+"%");
		
		girlVO.setName("女生");
		girlVO.setAll(girlAll);
		girlVO.setGood(girlGood);
		girlVO.setMild(girlMild);
		girlVO.setModerate(girlModerate);
		girlVO.setSerious(girlSerious);
		girlVO.setPercentage(df.format(((float)(girlAll-girlGood)/(float)girlAll)*100)+"%");
	
		voList.add(sumVO);
		voList.add(boyVO);
		voList.add(girlVO);
		
		end.put("name", schoolclass.getSchoolName()+" :"+schoolclass.getName());
		end.put("gradeTested", gradeTested);
		end.put("gradeSum", gradeSum);
		end.put("gradeBad", gradeBad);
		end.put("gradePercentage", df.format(((float)gradeBad/(float)gradeTested)*100)+"%");
		end.put("viewGradeReport", voList);
		end.put("studentList", allStudent);
		
		return ResultVOUtil.success(end);
	}
	
	
	@Override
	public synchronized ResultVO pushReport(Map<String, String> params,HttpSession session) {
		DecimalFormat df=new DecimalFormat("0.00");

		Integer schoolId = Integer.valueOf(session.getAttribute("schoolId").toString());
		String year = params.get("year");
		Integer semester = Integer.valueOf(params.get("semester"));
		
		HashMap<String, Object> map = new HashMap<>();
		int classId=0, grade = 0,allstudent = 0,good = 0,bad= 0,avgGood= 0,avgMild= 0,avgModerate= 0,avgSerious= 0,leftbad= 0,
			nullstudent=0	,rightbad= 0,avgbad= 0,boy= 0,girl= 0,correct= 0,screeningbad= 0, other = 0;
		String  type,schoolName="",range="";
		Double LEFT = 0d , RIGHT = 0d;
		List<SchoolStudentRecordMapper> students = null;
		type = params.get("type");
		if(!StringUtils.isEmpty(params.get("classId")))classId = Integer.valueOf(params.get("classId"));
		if(!StringUtils.isEmpty(params.get("grade")))grade = Integer.valueOf(params.get("grade"));
		
		
		SchoolSemesterMapper SemesterPo = school_semester_dao.findByYearAndSemesterAndSchoolId(year,semester,schoolId);
		if(SemesterPo==null) return ResultVOUtil.error(ResultEnum.REPORT_ERROR.getStatus(), ResultEnum.REPORT_ERROR.getMessage());
		
		if("school".equals(type)) {
			schoolName = school_dao.findById(schoolId).get().getName();
			range = "全校";
			//所有学生数量
			students= school_student_record_dao.findBySchoolIdAndSemester(schoolId,SemesterPo.getId());
			
		}else if("grade".equals(type)) {
			schoolName = school_dao.findById(schoolId).get().getName()+reaGrade(grade);
			range = reaGrade(grade);
			List<Integer> allclass = class_dao.findIdBySchoolIdAndGrade(schoolId,grade);
			
			if(allclass.size()==0)return ResultVOUtil.error(ResultEnum.REPORT_ERROR.getStatus(), ResultEnum.REPORT_ERROR.getMessage());
			//所有学生数量
			students = school_student_record_dao.findBySemesterAndClassIdIn(SemesterPo.getId(),allclass);
			
		}else if("class".equals(type)) {
			ClassesMapper classesMapper = null;
			Optional<ClassesMapper> findById = class_dao.findById(classId);
			if(findById.isPresent()) {
				classesMapper=findById.get();
			}else {
				System.out.println("这是班级没取到"+params.get("classId"));
				return ResultVOUtil.error(ResultEnum.REPORT_ERROR.getStatus(), ResultEnum.REPORT_ERROR.getMessage());
			}
			schoolName = classesMapper.getSchoolName()+classesMapper.getClassName();
			range = classesMapper.getClassName();
			//所有学生数量
			students = school_student_record_dao.findByClassIdAndSemester(classId,SemesterPo.getId());
			
		}
		
		allstudent = students.size();
		
		for (SchoolStudentRecordMapper po : students) {
			if(po.getVisionLeftStr()==null||po.getVisionRightStr()==null)nullstudent++;
		}
		
		for (SchoolStudentRecordMapper s : students) {
			if(s.getVisionLeftStr()==null||s.getVisionRightStr()==null)continue;
			
			ScreeningWearMapper screeningwear = screening_wear_dao.findTopByStudentIdOrderByGenTimeDesc(s.getStudentId());
			
			LEFT = s.getVisionLeftStr();
			RIGHT = s.getVisionRightStr();
			
				//双眼大于1.0 并且没有戴镜记录 就是良好
				if(LEFT>=1.0d&&RIGHT>=1.0d) {
					good++;
				}else { //这里条件是 视力有不到1.0的眼 
					bad++; //不良
					
					if(LEFT<1.0d&&RIGHT>=1.0d) {
						leftbad++;
					}else if(RIGHT<1.0d&&LEFT>=1.0d) {
						rightbad++;
					}else{
						avgbad++;
					}
					if(s.getGender()==0) {
							boy++;
					}else {
							girl++;
					}
					if(RIGHT>=LEFT) {
						if(LEFT<1.0d&&LEFT>=0.6d) {
							avgMild++;
						}else if(LEFT<0.6d&&LEFT>=0.4d){
							avgModerate++;
						}else{
							avgSerious++;
						}
					}else {
						 if(RIGHT<1.0d&&RIGHT>=0.6d) {
							avgMild++;
						}else if(RIGHT<0.6d&&RIGHT>=0.4d) {
							avgModerate++;
						}else {
							avgSerious++;
						}
					}
				}
				if(screeningwear!=null) {
					correct++;
					if(screeningwear.getVisionLeftStr()<1.0d||screeningwear.getVisionRightStr()<1.0d)screeningbad++;
				}
		}
		 
		//模拟其它普通数据
		map.put("school1", schoolName);
		map.put("range1", range);
		map.put("inspect1", allstudent);
		map.put("actual1", (allstudent-nullstudent));
		
        //模拟饼状图数据
        HashMap<String, Integer> datas = new HashMap<>();
        datas.put("良好",good);
        datas.put("不良",bad);
        ImageEntity imageEntity = JfreeUtil.pieChart("视力不良统计",datas, 500, 200);
        map.put("picture", imageEntity);
        
        //模拟饼状图数据
        HashMap<String, Integer> datas1 = new HashMap<>();
        datas1.put("其他",other);
        datas1.put("重度视力不良",avgSerious);
        datas1.put("轻度视力不良",avgMild);
        datas1.put("中度视力不良",avgModerate);
        ImageEntity imageEntity1 = JfreeUtil.pieChart("视力不良分类",datas1, 500, 200);
        map.put("picture1", imageEntity1);
        
        //模拟饼状图数据
        HashMap<String, Integer> datas2 = new HashMap<>();
        datas2.put("左眼",leftbad);
        datas2.put("右眼",rightbad);
        datas2.put("双眼",avgbad);
        ImageEntity imageEntity2 = JfreeUtil.pieChart("视力不良分布情况",datas2, 500, 200);
        map.put("picture2", imageEntity2);
        
        //模拟饼状图数据
        HashMap<String, Integer> datas3 = new HashMap<>();
        datas3.put("男生",boy);
        datas3.put("女生",girl);
        ImageEntity imageEntity3 = JfreeUtil.pieChart("视力不良分布情况",datas3, 500, 200);
        map.put("picture3", imageEntity3);
        
        //模拟饼状图数据
        HashMap<String, Integer> datas4 = new HashMap<>();
        datas4.put("已矫正",correct);
        datas4.put("未校正",bad-correct);
        ImageEntity imageEntity4 = JfreeUtil.pieChart("视力矫正情况",datas4, 500, 200);
        map.put("picture4", imageEntity4);
        
        //模拟饼状图数据
        HashMap<String, Integer> datas5 = new HashMap<>();
        datas5.put("适配",correct-screeningbad);
        datas5.put("不适配",screeningbad);
        ImageEntity imageEntity5 = JfreeUtil.pieChart("已矫正中戴镜适配率",datas5, 500, 200);
        map.put("picture5", imageEntity5);
 
        map.put("remark1", "实际应检人数为:"+allstudent+"人   实检人数:"
        		+(allstudent-nullstudent)+ "人;\r\n"
        		+ "其中视力良好(1.0以上含)人数为:"+good+"人,占"+df.format(((float)good/((float)(allstudent-nullstudent)))*100)+"%;\r\n"
        		+ "    视力不良(1.0以下)人数为:"+bad+"人,占"+df.format(((float)bad/((float)(allstudent-nullstudent)))*100)+"%;\r\n"
        		+ "视力不良中:\r\n"
        		+ "    轻度视力不良人数为:"+avgMild+"人,占"+df.format(((float)avgMild/(float)bad)*100)+"%;\r\n"
        		+ "    中度视力不良人数为:"+avgModerate+"人,占"+df.format(((float)avgModerate/(float)bad)*100)+"%;\r\n"
        		+ "    重度视力不良人数为:"+avgSerious+"人,占"+df.format(((float)avgSerious/(float)bad)*100)+"%;\r\n"
        		+ "    其他视力不良人数为:"+other+"人,占"+df.format(((float)other/(float)bad)*100)+"%;\r\n");
        
        map.put("remark2", "实际应检人数为:"+allstudent+"人   实检人数:"
        		+(allstudent-nullstudent)+ "人;\r\n"
        		+ "其中仅左眼不良(1.0以下)人数为:"+leftbad+"人,占"+df.format(((float)leftbad/(float)bad)*100)+"%;\r\n"
        		+ "    仅右眼不良(1.0以下)人数为:"+rightbad+"人,占"+df.format(((float)rightbad/(float)bad)*100)+"%;\r\n"
        		+ "    双眼不良(1.0以下)人数为:"+avgbad+"人,占"+df.format(((float)avgbad/(float)bad)*100)+"%;\r\n"
        		+ "    男生视力不良人数为:"+boy+"人,占"+df.format((float)boy/((float)bad)*100)+"%;\r\n"
        		+ "    女生视力不良人数为:"+girl+"人,占"+df.format((float)girl/((float)bad)*100)+"%;");
        
        map.put("remark3", "实际应检人数为:"+allstudent+"人   实检人数:"
        		+(allstudent-nullstudent)+ "人;\r\n"
        		+ "其中视力矫正人数为:"+correct+"人,占"+df.format(((float)correct/((float)bad))*100)+"%;\r\n"
        		+ "    视力未校正人数为:"+(bad-correct)+"人,占"+df.format(((float)((bad-correct))/bad)*100)+"%;\r\n"
        		+ "视力矫正中:\r\n"
        		+ "    适配人数为:"+(correct-screeningbad)+"人,占"+df.format((float)(correct-screeningbad)/((float)correct)*100)+"%;\r\n"
        		+ "    不适配人数为:"+screeningbad+"人,占"+df.format((float)screeningbad/((float)correct)*100)+"%;");

        //word模板相对路径、word生成路径、word生成的文件名称、数据源
        //WordUtil.exportWord("F:/报表模板.docx", "F:/", "报表文件.docx", map);
        WordUtil.exportWord(UrlEnums.FILE_PATH.getUrl()+"报表模板.docx", UrlEnums.FILE_PATH.getUrl(), "报表文件.docx", map);
		
		return ResultVOUtil.success();
	}
	
	private String reaGrade(Integer grade) {
		String end = "";
		switch (grade) {
		case 1:end="一年级";
			break;
		case 2:end="二年级";
			break;
		case 3:end="三年级";
			break;
		case 4:end="四年级";
			break;
		case 5:end="五年级";
			break;
		case 6:end="六年级";
			break;
		}
		return end;
	}

	@Override
	public ResultVO diopterList(Map<String, String> params, HttpSession session) {
		Integer schoolId = Integer.valueOf(session.getAttribute("schoolId").toString());
		
		Map<String, Object> end = new HashMap<>();
		List<DiopterMapper> list = null;
		Integer page = 0;
		int count = 0;
		if(!StringUtils.isEmpty(params.get("page")))page = (Integer.valueOf(params.get("page"))-1)*10;
		String type = params.get("type");
		end.put("size", 10);
		end.put("number", (StringUtils.isEmpty(params.get("page")))?0:Integer.valueOf(params.get("page"))-1);
		if("student".equals(type)) {
			list = diopter_dao.findByNameOrderByGenTimeDesc("%"+params.get("name")+"%",schoolId,page, 10);
			count = diopter_dao.findcountByName("%"+params.get("name")+"%",schoolId);
			end.put("totalElements", count);
			end.put("content", list);
			return (list.size()!=0)?ResultVOUtil.success(end):ResultVOUtil.error(ResultEnum.STUDENTSIZE_NULL.getStatus(), ResultEnum.STUDENTSIZE_NULL.getMessage());
		}
		if("class".equals(type)) {
			list = diopter_dao.findByClassIdOrderById(Integer.valueOf(params.get("id")),page, 10);
			count = diopter_dao.findcountByClassId(Integer.valueOf(params.get("id")));
			end.put("totalElements", count);
			end.put("content", list);
			return (list.size()!=0)?ResultVOUtil.success(end):ResultVOUtil.error(ResultEnum.STUDENTSIZE_NULL.getStatus(), ResultEnum.STUDENTSIZE_NULL.getMessage());
		}
		if(schoolId == 0) {
			List<Integer> schoolIds = new ArrayList<>();
			Integer regionId = Integer.valueOf(session.getAttribute("regionId").toString());
			schoolIds=school_dao.findIdByRegionId(regionId);
			list = diopter_dao.findBySchoolIdOrderById(schoolIds,page, 10);
			count = diopter_dao.findcountBySchoolId(schoolIds);
		}else if(schoolId == -1){
			List<Integer> schoolIds = new ArrayList<>();
			Integer userId = Integer.valueOf(session.getAttribute("userId").toString());
			List<UserSchoolsMapper> u_s_list = u_s_dao.findByUserId(userId);
			for (UserSchoolsMapper userSchoolsMapper : u_s_list) {
				schoolIds.add(userSchoolsMapper.getSchoolId());
			}
			list = diopter_dao.findBySchoolIdOrderById(schoolIds,page, 10);
			count = diopter_dao.findcountBySchoolId(schoolIds);
		}else {
			list = diopter_dao.findBySchoolIdOrderById(schoolId,page, 10);
			count = diopter_dao.findcountBySchoolId(schoolId);
		}
		end.put("totalElements", count);
		end.put("content", list);
		return (list.size()!=0)?ResultVOUtil.success(end):ResultVOUtil.error(ResultEnum.STUDENTSIZE_NULL.getStatus(), ResultEnum.STUDENTSIZE_NULL.getMessage());
	}
	
	@Override
	public ResultVO diopterByStudentId(Map<String, String> params) {
		Integer page = 0 ;
		if(!StringUtils.isEmpty(params.get("page"))) page = Integer.valueOf(params.get("page")) - 1;
		Integer studentId = Integer.valueOf(params.get("studentId"));
		Page<DiopterMapper> list = diopter_dao.findByStudentIdOrderByIdDesc(studentId,PageRequest.of(page, 10));
		return (list.getContent().size()!=0)?ResultVOUtil.success(list):ResultVOUtil.error(ResultEnum.STUDENTSIZE_NULL.getStatus(), ResultEnum.STUDENTSIZE_NULL.getMessage());
	}

	@Override
	public ResultVO deleteDiopter(Map<String, String> params) {
		diopter_dao.deleteById(Integer.valueOf(params.get("id")));
		return ResultVOUtil.success();
	}
	
	@Override
	public ResultVO diopterExcelOut(Map<String, String> params,HttpSession session) {
		Integer classId = 0;
		if(!StringUtils.isEmpty(params.get("classId"))) {
			classId = Integer.valueOf(params.get("classId")); 
		}else {
			classId = Integer.valueOf(session.getAttribute("schoolId").toString());
		}
		List<StudentMapper> list = null;
		if(classId == 0 ) {
			list = student_dao.findBySchoolIdOrderByDiopterLeftDesc(classId);
		}else {
			list = student_dao.findByClassesIdOrderByDiopterLeftDesc(classId);
		}
		ExcelUtil.createExcel(diopterExcel(list), diopterArray);
		return ResultVOUtil.success();
	}

	@Override
	public Map<String, List<String>> diopterExcel(List<StudentMapper> student) {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		for (StudentMapper s_po : student) {
			DiopterMapper diopter = diopter_dao.findTopByStudentIdOrderByIdDesc(s_po.getId());
			ArrayList<String> members = new ArrayList<String>();
			if(diopter==null) {
				members.add(s_po.getSchoolName());
				members.add(s_po.getClassesName());
				members.add(s_po.getName());
				members.add("暂无数据");
				members.add("暂无数据");
				members.add("暂无数据");
				members.add("暂无数据");
				members.add("暂无数据");
				members.add("暂无数据");
				members.add("暂无数据");
				members.add("暂无数据");
				members.add("暂无数据");
				members.add("暂无数据");
				//新加的临时用
				members.add(s_po.getVisionLeftStr()==null?"":OptotypeUtils.vision2onlyvision5(s_po.getVisionLeftStr()));
				members.add(s_po.getVisionRightStr()==null?"":OptotypeUtils.vision2onlyvision5(s_po.getVisionRightStr()));
				
				members.add("9");
				members.add("9");
				members.add("9");
				members.add("9");
				
				map.put(s_po.getId() + "", members);
			}else {
				members.add(s_po.getSchoolName());
				members.add(s_po.getClassesName());
				members.add(s_po.getName());
				members.add(diopter.getDs1R());
				members.add(diopter.getDs1L());
				members.add(diopter.getDc1R());
				members.add(diopter.getDc1L());
				members.add(diopter.getAxis1R());
				members.add(diopter.getAxis1L());
				members.add(diopter.getGhR());
				members.add(diopter.getGhL());
				members.add(diopter.getGvR());
				members.add(diopter.getGvL());
				//这下面都是加来临时用的
				members.add(s_po.getVisionLeftStr()==null?"":OptotypeUtils.vision2onlyvision5(s_po.getVisionLeftStr()));
				members.add(s_po.getVisionRightStr()==null?"":OptotypeUtils.vision2onlyvision5(s_po.getVisionRightStr()));
				//等效球镜
				Double sphericalEquivalent = 0d;
				if(diopter.getDc1L().indexOf("<")==-1) {
					sphericalEquivalent=(Double.valueOf(diopter.getDc1L())/2)+Double.valueOf(diopter.getDs1L());
				}else {
					sphericalEquivalent=(-3.00d/2)+Double.valueOf(diopter.getDs1L());
				}
				if(sphericalEquivalent<-0.50) {
					members.add("1");
				}else if(sphericalEquivalent>=-0.50&&sphericalEquivalent<1.00){
					members.add("0");
				}else {
					members.add("2");
				}
				
				
				if(diopter.getDc1R().indexOf("<")==-1) {
					sphericalEquivalent=(Double.valueOf(diopter.getDc1R())/2)+Double.valueOf(diopter.getDs1R());
				}else {
					sphericalEquivalent=(-3.00d/2)+Double.valueOf(diopter.getDs1R());
				}
				if(sphericalEquivalent<-0.50) {
					members.add("1");
				}else if(sphericalEquivalent>=-0.50&&sphericalEquivalent<1.00){
					members.add("0");
				}else {
					members.add("2");
				}
				
				if(members.get(15).equals("0")) {
					members.add("0");
				}else if(members.get(15).equals("1")){
					members.add("-1");
				}else {
					members.add("1");
				}
				
				if(members.get(16).equals("0")) {
					members.add("0");
				}else if(members.get(15).equals("1")){
					members.add("-1");
				}else {
					members.add("1");
				}
				
				map.put(s_po.getId() + "", members);
			}
		}
		return map;
	}
}
