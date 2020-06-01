package com.threefriend.lightspace.service.Impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.threefriend.lightspace.enums.ResultEnum;
import com.threefriend.lightspace.enums.UrlEnums;
import com.threefriend.lightspace.enums.VisionEnums;
import com.threefriend.lightspace.mapper.ClassesMapper;
import com.threefriend.lightspace.mapper.StudentMapper;
import com.threefriend.lightspace.mapper.xcx.ScreeningMapper;
import com.threefriend.lightspace.mapper.xcx.ScreeningWearMapper;
import com.threefriend.lightspace.repository.ClassesRepository;
import com.threefriend.lightspace.repository.SchoolRepository;
import com.threefriend.lightspace.repository.ScreeningRepository;
import com.threefriend.lightspace.repository.ScreeningWearRepository;
import com.threefriend.lightspace.repository.StudentRepository;
import com.threefriend.lightspace.service.ReportService;
import com.threefriend.lightspace.util.JfreeUtil;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.util.WordUtil;
import com.threefriend.lightspace.vo.ResultVO;

import cn.afterturn.easypoi.entity.ImageEntity;

@Service
public class ReportServiceImpl implements ReportService{
	
	@Autowired
	private StudentRepository student_dao;
	@Autowired
	private ClassesRepository class_dao;
	@Autowired
	private SchoolRepository school_dao;
	@Autowired
	private ScreeningWearRepository screening_wear_dao;
	@Autowired
	private ScreeningRepository screening_dao;

	@Override
	public ResultVO pushReport(Map<String, String> params) {
		DecimalFormat df=new DecimalFormat("0.00");
		HashMap<String, Object> map = new HashMap<>();
		int id,grade = 0,allstudent = 0,good = 0,bad= 0,avgGood= 0,avgMild= 0,avgModerate= 0,avgSerious= 0,leftbad= 0,
			nullstudent=0	,rightbad= 0,avgbad= 0,boy= 0,girl= 0,correct= 0,screeningbad= 0, other = 0;
		String  type,schoolName="",range="";
		Double LEFT = 0d , RIGHT = 0d;
		List<StudentMapper> students = null;
		type = params.get("type");
		id = Integer.valueOf(params.get("id"));
		if(!StringUtils.isEmpty(params.get("grade")))grade = Integer.valueOf(params.get("grade"));
		
		if("school".equals(type)) {
			schoolName = school_dao.findById(id).get().getName();
			range = "全校";
			//所有学生数量
			allstudent = student_dao.countBySchoolId(id);
			students= student_dao.findBySchoolId(id);
			for (StudentMapper s : students) {
				if(s.getVisionLeftStr()==null||s.getVisionRightStr()==null) {
					nullstudent++;
					continue;
				}
				ScreeningMapper screening = screening_dao.findTopByStudentIdOrderByGenTimeDesc(s.getId());
				ScreeningWearMapper screeningwear = screening_wear_dao.findTopByStudentIdOrderByGenTimeDesc(s.getId());
				
				if(screening!=null) {//如果裸眼不为空
					LEFT = screening.getVisionLeftStr();
					RIGHT = screening.getVisionRightStr();
					//双眼大于1.0 并且没有戴镜记录 就是良好
					if(LEFT>=1.0d&&RIGHT>=1.0d&&screeningwear==null) {
							good++;
					}else { //这里条件是 视力有不到1.0的眼 或者 有戴镜记录
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
				}else {
					LEFT = screeningwear.getVisionLeftStr();
					RIGHT = screeningwear.getVisionRightStr();
					bad++;avgbad++;other++;correct++;
					if(LEFT<1.0d||RIGHT<1.0d) {
						screeningbad++;
					}
					if(s.getGender()==0) {
						boy++;
					}else {
						girl++;
					}
				}
			}
			
		}else if("grade".equals(type)) {
			schoolName = school_dao.findById(id).get().getName()+reaGrade(grade);
			range = reaGrade(grade);
			List<Integer> allclass = class_dao.findIdBySchoolIdAndGrade(id,grade);
			
			if(allclass.size()==0)return ResultVOUtil.error(ResultEnum.REPORT_ERROR.getStatus(), ResultEnum.REPORT_ERROR.getMessage());
			//所有学生数量
			allstudent = student_dao.countByClassesId(allclass);
			students = student_dao.findByClassesId(allclass);
			for (StudentMapper s : students) {
				if(s.getVisionLeftStr()==null||s.getVisionRightStr()==null) {
					nullstudent++;
					continue;
				}
				ScreeningMapper screening = screening_dao.findTopByStudentIdOrderByGenTimeDesc(s.getId());
				ScreeningWearMapper screeningwear = screening_wear_dao.findTopByStudentIdOrderByGenTimeDesc(s.getId());
				
				if(screening!=null) {//如果裸眼不为空
					LEFT = screening.getVisionLeftStr();
					RIGHT = screening.getVisionRightStr();
					//双眼大于1.0 并且没有戴镜记录 就是良好
					if(LEFT>=1.0d&&RIGHT>=1.0d&&screeningwear==null) {
							good++;
					}else { //这里条件是 视力有不到1.0的眼 或者 有戴镜记录
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
				}else {
					LEFT = screeningwear.getVisionLeftStr();
					RIGHT = screeningwear.getVisionRightStr();
					bad++;avgbad++;other++;correct++;
					if(LEFT<1.0d||RIGHT<1.0d) {
						screeningbad++;
					}
					if(s.getGender()==0) {
						boy++;
					}else {
						girl++;
					}
				}
			}
		}else if("class".equals(type)) {
			List ids = new ArrayList<Integer>() {{add(id);}};
			ClassesMapper classesMapper = class_dao.findById(id).get();
			schoolName = classesMapper.getSchoolName()+classesMapper.getClassName();
			range = classesMapper.getClassName();
			//所有学生数量
			allstudent = student_dao.countByClassesId(id);
			students = student_dao.findByClassesId(id);
			for (StudentMapper s : students) {
				if(s.getVisionLeftStr()==null||s.getVisionRightStr()==null) {
					nullstudent++;
					continue;
				}
				ScreeningMapper screening = screening_dao.findTopByStudentIdOrderByGenTimeDesc(s.getId());
				ScreeningWearMapper screeningwear = screening_wear_dao.findTopByStudentIdOrderByGenTimeDesc(s.getId());
				
				if(screening!=null) {//如果裸眼不为空
					LEFT = screening.getVisionLeftStr();
					RIGHT = screening.getVisionRightStr();
					//双眼大于1.0 并且没有戴镜记录 就是良好
					if(LEFT>=1.0d&&RIGHT>=1.0d&&screeningwear==null) {
							good++;
					}else { //这里条件是 视力有不到1.0的眼 或者 有戴镜记录
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
				}else {
					LEFT = screeningwear.getVisionLeftStr();
					RIGHT = screeningwear.getVisionRightStr();
					bad++;avgbad++;other++;correct++;
					if(LEFT<1.0d||RIGHT<1.0d) {
						screeningbad++;
					}
					if(s.getGender()==0) {
						boy++;
					}else {
						girl++;
					}
				}
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

	@Override
	public String reaGrade(Integer grade) {
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

}
