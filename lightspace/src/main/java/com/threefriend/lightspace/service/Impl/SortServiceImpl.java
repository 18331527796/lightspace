package com.threefriend.lightspace.service.Impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.threefriend.lightspace.Exception.SortException;
import com.threefriend.lightspace.enums.SortEnums;
import com.threefriend.lightspace.mapper.RecordMapper;
import com.threefriend.lightspace.mapper.ScreeningMapper;
import com.threefriend.lightspace.mapper.SortMapper;
import com.threefriend.lightspace.mapper.StudentMapper;
import com.threefriend.lightspace.repository.ClassesRepository;
import com.threefriend.lightspace.repository.RecordRepository;
import com.threefriend.lightspace.repository.ScreeningRepository;
import com.threefriend.lightspace.repository.SortRepository;
import com.threefriend.lightspace.repository.StudentRepository;
import com.threefriend.lightspace.service.SortService;
import com.threefriend.lightspace.util.ListUtils;
import com.threefriend.lightspace.vo.SortVO;

/**
 * 
 * 排座的实现类
 *
 */
@Service
public class SortServiceImpl implements SortService {

	@Autowired
	private RecordRepository record_dao;
	@Autowired
	private StudentRepository student_dao;
	@Autowired
	private SortRepository sort_dao;
	@Autowired
	private ClassesRepository class_dao;
	@Autowired
	private ScreeningRepository screening_dao;
	/*
	 * 学生排座
	 * 通过传输的班级id 排座类型 保存时间来返回排座的数据
	 * 按照身高查询学生的顺序 再按照学生的平均裸眼视力排序 封装数据返回前台
	 * 时间单位 传输过来单位为（秒）
	 */
	@Override
	public List<List<SortVO>> studentSort(Integer classId, Integer type,Integer time) {
		time=time*1000;
		Date nowtime=new Date();
			Integer number = 0;
			// 根据不同的type来确定一行多少人 在进行下列的数据处理
			if (type == SortEnums.TYPEONE.getType())
				number = SortEnums.TYPEONE.getNumber();
			if (type == SortEnums.TYPETWO.getType())
				number = SortEnums.TYPETWO.getNumber();
			if (type == SortEnums.TYPETHREE.getType())
				number = SortEnums.TYPETHREE.getNumber();
			if (type == SortEnums.TYPEFOUR.getType())
				number = SortEnums.TYPEFOUR.getNumber();
			// 接收按照坐姿高度排序后的
			List<SortVO> sort = new ArrayList<>();
			List<SortVO> temporary = new ArrayList<>();
			// 排座完成后的
			List<List<SortVO>> end = new ArrayList<>();
			//用来存放数据空的学生姓名返回前台 告知老师哪个学生都需要检测
			List<String> nullStudent= new ArrayList<>();
			List<StudentMapper> allStudent = student_dao.findByClassesIdOrderBySittingHeight(classId);
			//循环每个学生的数据做封装
			for (StudentMapper student : allStudent) {
				Integer id=student.getId();
				SortVO vo = new SortVO();
				vo.setStudentId(student.getId());
				vo.setStudentName(student.getName());
				vo.setGender(student.getGender());
				vo.setCorrect(student.getCorrect());
				vo.setSittingHeight(Double.valueOf(student.getSittingHeight())-Double.valueOf(student.getChairHeight()));
				//通过判断学生是否有最新的视力记录
				if(student.getVisionLeftStr()!=null&&student.getVisionRightStr()!=null) {
					vo.setAvgRecord((student.getVisionLeftStr()+student.getVisionRightStr())/2);
				}else {
					nullStudent.add(student.getName());
				}
				//存放集合
				sort.add(vo);
				
				/*// 拿出来每个人的最新数据
				RecordMapper top = record_dao.findTopByStudentIdOrderByGenTimeDesc(id);
				// 拿出筛查的最新数据
				ScreeningMapper screening = screening_dao.findTopByStudentIdOrderByGenTimeDesc(id);
				if (top != null) {
					// 筛查数据不是空的 并且 比检测数据更新 那就取筛查数据
					if(screening != null&& screening.getGenTime().getTime()>top.getGenTime().getTime()) {
						vo.setAvgRecord((screening.getVisionLeftStr() + screening.getVisionRightStr()) / 2);
					}else {
						vo.setAvgRecord((top.getVisionLeftStr() + top.getVisionRightStr()) / 2);
					}
					sort.add(vo);
				}else {
					//检测数据是空的 但是 筛查记录不是空的 就用筛查记录
					if(screening != null) {
						vo.setAvgRecord((screening.getVisionLeftStr() + screening.getVisionRightStr()) / 2);
						sort.add(vo);
					}else {
						nullStudent.add(student.getName());
					}
				}*/
			}
			//两个数据都是空的 就抛异常
			if(nullStudent!=null&&!nullStudent.isEmpty())throw new SortException(nullStudent);
			int size = sort.size();
			ListUtils.sort(sort, true,"sittingHeight", "avgRecord");
			StringBuilder sortMark = new StringBuilder("");
			//遍历身高的集合 添加进临时行的集合
			for (int i = 1; i < size + 1; i++) {
				// 临时行的排序
				temporary.add(sort.get(i - 1));
				//判断是不是一行已经完成添加
				if ((i % number == 0 && i != 0) || (((size - i) < number) && i == (size - 1))) {
					// lamdba表达式排序 按照裸眼视力的平均值
					//Collections.sort(temporary,
						//	Comparator.comparing(SortVO::getAvgRecord).thenComparing(SortVO::getAvgRecord));
					// 组合最后的list
					end.add(temporary);
					//遍历这一行的孩子 append 记录座位顺序
					for (int j = 0; j < temporary.size(); j++) {
						if (j == 0) {
							sortMark.append("-" + temporary.get(j).getStudentId());
						} else {
							sortMark.append("," + temporary.get(j).getStudentId());
						}
					}
					// 清除临时的list 进入下一行存储
					temporary = new ArrayList<>();
				}
			}
			Integer count = sort_dao.countByClassId(classId);
			 SortMapper po = new SortMapper(); 
			 po.setName("第"+(count+1)+"次排座");
			 po.setClassId(classId); 
			 po.setType(type);
			 po.setGenTime(nowtime); 
			 po.setEndTime(new Date(nowtime.getTime()+time));
			 po.setSort(sortMark.toString());
			 sort_dao.save(po);
			 Collections.reverse(end);
			return end;
		}

	@Override
	public List<List<SortVO>> sortShow(Integer Id) {
		SortMapper sortmapper = sort_dao.findById(Id).get();
		List<List<SortVO>> end = new ArrayList<>();
			// 排座完成后的
			String[] split = sortmapper.getSort().split("-");
			for (String string : split) {
				if(StringUtils.isEmpty(string))continue;
				List<SortVO> temporary=new ArrayList<>();
				String[] split2 = string.split(",");
				for (String string2 : split2) {
					if(StringUtils.isEmpty(string2))continue;
					SortVO vo=new SortVO();
					StudentMapper studentMapper = student_dao.findById(Integer.valueOf(string2)).get();
					vo.setStudentId(Integer.valueOf(string2));
					vo.setStudentName(studentMapper.getName());
					vo.setGender(studentMapper.getGender());
					vo.setCorrect(studentMapper.getCorrect());
					temporary.add(vo);
				}
				end.add(temporary);
			}
			Collections.reverse(end);
		return end;
	}

	@Override
	public List<SortMapper> byClassId(Integer classId) {
		return sort_dao.findByClassIdOrderByGenTimeDesc(classId);
	}
}
