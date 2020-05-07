package com.threefriend.lightspace;



import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.collections.map.HashedMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.threefriend.lightspace.enums.VisionEnums;
import com.threefriend.lightspace.mapper.StudentMapper;
import com.threefriend.lightspace.repository.ClassesRepository;
import com.threefriend.lightspace.repository.SchoolRepository;
import com.threefriend.lightspace.repository.ScreeningWearRepository;
import com.threefriend.lightspace.repository.StudentRepository;
import com.threefriend.lightspace.util.JfreeUtil;
import com.threefriend.lightspace.util.WordUtil;

import cn.afterturn.easypoi.entity.ImageEntity;



@RunWith(SpringRunner.class)
@SpringBootTest
public class test {
	@Autowired
	private StudentRepository student_dao;
	@Autowired
	private ClassesRepository class_dao;
	@Autowired
	private SchoolRepository school_dao;
	@Autowired
	private ScreeningWearRepository screening_wear_dao;
	
	
	@Test
	public void test() throws Exception{
		DecimalFormat df=new DecimalFormat("0.00");
		HashMap<String, Object> map = new HashMap<>();
		int id,grade = 0,allstudent = 0,good = 0,bad= 0,avgGood= 0,avgMild= 0,avgModerate= 0,avgSerious= 0,leftbad= 0,
				rightbad= 0,avgbad= 0,boy= 0,girl= 0,correct= 0,screeningbad= 0;
		id = 50;
		String schoolName = school_dao.findById(id).get().getName();
		//所有学生数量
		allstudent = student_dao.countBySchoolId(id);
		List<StudentMapper> findBySchoolId = student_dao.findBySchoolId(id);
		for (StudentMapper s : findBySchoolId) {
			if(s.getVisionLeftStr()>=1.0d&&s.getVisionRightStr()>=1.0d)good++;
			if(s.getVisionRightStr()<1.0d||s.getVisionLeftStr()<1.0d)bad++;
			if(s.getVisionLeftStr()<1.0d&&s.getVisionRightStr()>=1.0d)leftbad++;
			if(s.getVisionRightStr()<1.0d&&s.getVisionLeftStr()>=1.0d)rightbad++;
			if(s.getVisionRightStr()<1.0d&&s.getVisionLeftStr()<1.0d)avgbad++;
			if((s.getVisionRightStr()<1.0d||s.getVisionLeftStr()<1.0d)&&s.getGender()==0)boy++;
			if((s.getVisionRightStr()<1.0d||s.getVisionLeftStr()<1.0d)&&s.getGender()==1)girl++;
		}
		avgGood = student_dao.schoolAvgVision(id, VisionEnums.NORMAL.getType());
		avgMild = student_dao.schoolAvgVision(id, VisionEnums.MILD.getType(),
				VisionEnums.NORMAL.getType());
		avgModerate = student_dao.schoolAvgVision(id, VisionEnums.MODERATE.getType(),
				VisionEnums.MILD1.getType());
		avgSerious = student_dao.schoolAvgVision(id, 0d, VisionEnums.SERIOUS.getType());
		
		correct = screening_wear_dao.findcountBySchoolId(id);
		
		
		screeningbad = screening_wear_dao.findcountBySchoolId(id,0.8d);
		 
		//模拟其它普通数据
		map.put("school1", schoolName);
		map.put("range1", schoolName);
		map.put("inspect1", allstudent);
		map.put("actual1", allstudent);
		
        //模拟饼状图数据
        HashMap<String, Integer> datas = new HashMap<>();
        datas.put("良好",good);
        datas.put("不良",bad);
        ImageEntity imageEntity = JfreeUtil.pieChart("视力不良统计",datas, 500, 200);
        map.put("picture", imageEntity);
        
        //模拟饼状图数据
        HashMap<String, Integer> datas1 = new HashMap<>();
        datas1.put("轻度视力不良",avgMild);
        datas1.put("中度视力不良",avgModerate);
        datas1.put("重度视力不良",avgSerious);
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
        datas4.put("未校正",allstudent-correct);
        ImageEntity imageEntity4 = JfreeUtil.pieChart("视力矫正情况",datas4, 500, 200);
        map.put("picture4", imageEntity4);
        
        //模拟饼状图数据
        HashMap<String, Integer> datas5 = new HashMap<>();
        datas5.put("适配",correct-screeningbad);
        datas5.put("不适配",screeningbad);
        ImageEntity imageEntity5 = JfreeUtil.pieChart("已矫正中戴镜适配率",datas5, 500, 200);
        map.put("picture5", imageEntity5);
 
        map.put("remark1", schoolName+"实际应检人数为:"+allstudent+"人   实检人数:"
        		+findBySchoolId.size()+ "人;\r\n"
        		+ "其中视力良好(1.0以上含)人数为:"+good+"人,占"+df.format(((float)good/((float)good+(float)bad))*100)+"%;\r\n"
        		+ "    视力不良(1.0以下)人数为:"+bad+"人,占"+df.format(((float)bad/((float)good+(float)bad))*100)+"%;\r\n"
        		+ "视力不良中:\r\n"
        		+ "    轻度视力不良人数为:"+avgMild+"人,占"+df.format(((float)avgMild/(float)allstudent)*100)+"%;\r\n"
        		+ "    中度视力不良人数为:"+avgModerate+"人,占"+df.format(((float)avgModerate/((float)allstudent))*100)+"%;\r\n"
        		+ "    重度视力不良人数为:"+avgSerious+"人,占"+df.format(((float)avgSerious/(float)allstudent)*100)+"%;\r\n");
        
        map.put("remark2", schoolName+"实际应检人数为:"+allstudent+"人   实检人数:"
        		+findBySchoolId.size()+ "人;\r\n"
        		+ "其中仅左眼不良(1.0以下)人数为:"+leftbad+"人,占"+df.format(((float)leftbad/((float)leftbad+(float)rightbad+(float)avgbad))*100)+"%;\r\n"
        		+ "    仅右眼不良(1.0以下)人数为:"+rightbad+"人,占"+df.format(((float)rightbad/((float)leftbad+(float)rightbad+(float)avgbad))*100)+"%;\r\n"
        		+ "    双眼不良(1.0以下)人数为:"+avgbad+"人,占"+df.format(((float)avgbad/((float)leftbad+(float)rightbad+(float)avgbad))*100)+"%;\r\n"
        		+ "    男生视力不良人数为:"+boy+"人,占"+df.format((float)boy/((float)allstudent)*100)+"%;\r\n"
        		+ "    女生视力不良人数为:"+girl+"人,占"+df.format((float)girl/((float)allstudent)*100)+"%;");
        
        map.put("remark3", schoolName+"实际应检人数为:"+allstudent+"人   实检人数:"
        		+findBySchoolId.size()+ "人;\r\n"
        		+ "其中视力矫正人数为:"+correct+"人,占"+df.format(((float)correct/((float)allstudent))*100)+"%;\r\n"
        		+ "    视力未校正人数为:"+(allstudent-correct)+"人,占"+df.format(((float)(allstudent-correct)/allstudent)*100)+"%;\r\n"
        		+ "视力矫正中:\r\n"
        		+ "    适配人数为:"+(correct-screeningbad)+"人,占"+df.format((float)(correct-screeningbad)/((float)correct)*100)+"%;\r\n"
        		+ "    不适配人数为:"+screeningbad+"人,占"+df.format((float)screeningbad/((float)correct)*100)+"%;");
        
        //word模板相对路径、word生成路径、word生成的文件名称、数据源
        WordUtil.exportWord("F:/模板.docx", "F:/", "生成文件.docx", map);
		
		
	}
	
	
}
