package com.threefriend.lightspace.service.Impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.threefriend.lightspace.enums.ResultEnum;
import com.threefriend.lightspace.mapper.DiopterMapper;
import com.threefriend.lightspace.mapper.StudentMapper;
import com.threefriend.lightspace.mapper.xcx.ScreeningMapper;
import com.threefriend.lightspace.mapper.xcx.ScreeningWearMapper;
import com.threefriend.lightspace.repository.DiopterRepository;
import com.threefriend.lightspace.repository.RecordRepository;
import com.threefriend.lightspace.repository.ScreeningRepository;
import com.threefriend.lightspace.repository.ScreeningWearRepository;
import com.threefriend.lightspace.repository.StudentRepository;
import com.threefriend.lightspace.service.ScreeningService;
import com.threefriend.lightspace.util.ExcelUtil;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ResultVO;

@Service
public class ScreeningServiceImpl implements ScreeningService{
	private final String[] strArray = { "学校名称", "班级名称", "学生姓名", "右眼裸眼视力", "左眼裸眼视力" };
	private final String[] strwearArray = { "学校名称", "班级名称", "学生姓名", "右眼戴镜视力", "左眼戴镜视力" };
	private final String[] diopterArray = { "学校名称", "班级名称", "学生姓名", "右眼屈光度", "左眼屈光度" };
	
	@Autowired
	private ScreeningRepository screening_dao;
	@Autowired
	private ScreeningWearRepository screening_wear_dao;
	@Autowired
	private StudentRepository student_dao;
	@Autowired
	private DiopterRepository diopter_dao;
	@Autowired
	private ReadDiopterExcel readexcel;
	
	
	@Override
	public ResultVO screeningList(Map<String, String> params) {
		Integer page = 0;
		if(!StringUtils.isEmpty(params.get("page")))page = (Integer.valueOf(params.get("page"))-1)*10;
		String type = params.get("type");
		Map<String, Object> end = new HashMap<>();
		end.put("size", 10);
		end.put("number", (StringUtils.isEmpty(params.get("page")))?0:Integer.valueOf(params.get("page"))-1);
		if("student".equals(type)) {
			List<ScreeningMapper> list = screening_dao.findByStudentIdOrderByGenTimeDesc(Integer.valueOf(params.get("id")),page, 10);
			int count = screening_dao.findcountByStudentId(Integer.valueOf(params.get("id")));
			end.put("totalElements", count);
			end.put("content", list);
			return (list.size()!=0)?ResultVOUtil.success(end):ResultVOUtil.error(ResultEnum.STUDENTSIZE_NULL.getStatus(), ResultEnum.STUDENTSIZE_NULL.getMessage());
		}
		if("class".equals(type)) {
			List<ScreeningMapper> list = screening_dao.findByClassIdOrderByGenTimeDesc(Integer.valueOf(params.get("id")),page, 10);
			int count = screening_dao.findcountByClassId(Integer.valueOf(params.get("id")));
			end.put("totalElements", count);
			end.put("content", list);
			return (list.size()!=0)?ResultVOUtil.success(end):ResultVOUtil.error(ResultEnum.STUDENTSIZE_NULL.getStatus(), ResultEnum.STUDENTSIZE_NULL.getMessage());
		}
		if("school".equals(type)) {
			List<ScreeningMapper> list = screening_dao.findBySchoolIdOrderByGenTimeDesc(Integer.valueOf(params.get("id")),page, 10);
			int count = screening_dao.findcountBySchoolId(Integer.valueOf(params.get("id")));
			end.put("totalElements", count);
			end.put("content", list);
			return (list.size()!=0)?ResultVOUtil.success(end):ResultVOUtil.error(ResultEnum.STUDENTSIZE_NULL.getStatus(), ResultEnum.STUDENTSIZE_NULL.getMessage());
		}
		List<ScreeningMapper> list = screening_dao.findAllByOrderByGenTimeDesc(page,10);
		int count = screening_dao.findcount();
		end.put("totalElements", count);
		end.put("content", list);
		return (list.size()!=0)?ResultVOUtil.success(end):ResultVOUtil.error(ResultEnum.STUDENTSIZE_NULL.getStatus(), ResultEnum.STUDENTSIZE_NULL.getMessage());
	}

	@Override
	public ResultVO deleteScreening(Map<String, String> params) {
		String[] split = params.get("token").split("-");
		if(!"1".equals(split[1]))return ResultVOUtil.error(ResultEnum.RIGHT_ERROR.getStatus(), ResultEnum.RIGHT_ERROR.getMessage());
		ScreeningMapper po = screening_dao.findById(Integer.valueOf(params.get("id"))).get();
		params.put("id", po.getStudentId()+"");
		screening_dao.delete(po);
		return screeningByStudent(params);
	}
	
	@Override
	public ResultVO screeningWearList(Map<String, String> params) {
		Integer page = 0;
		if(!StringUtils.isEmpty(params.get("page")))page = (Integer.valueOf(params.get("page"))-1)*10;
		String type = params.get("type");
		Map<String, Object> end = new HashMap<>();
		end.put("size", 10);
		end.put("number", (StringUtils.isEmpty(params.get("page")))?0:Integer.valueOf(params.get("page"))-1);
		if("student".equals(type)) {
			List<ScreeningWearMapper> list = screening_wear_dao.findByStudentIdOrderByGenTimeDesc(Integer.valueOf(params.get("id")),page, 10);
			int count = screening_wear_dao.findcountByStudentId(Integer.valueOf(params.get("id")));
			end.put("totalElements", count);
			end.put("content", list);
			return (list.size()!=0)?ResultVOUtil.success(end):ResultVOUtil.error(ResultEnum.STUDENTSIZE_NULL.getStatus(), ResultEnum.STUDENTSIZE_NULL.getMessage());
		}
		if("class".equals(type)) {
			List<ScreeningWearMapper> list = screening_wear_dao.findByClassIdOrderByGenTimeDesc(Integer.valueOf(params.get("id")),page, 10);
			int count = screening_wear_dao.findcountByClassId(Integer.valueOf(params.get("id")));
			end.put("totalElements", count);
			end.put("content", list);
			return (list.size()!=0)?ResultVOUtil.success(end):ResultVOUtil.error(ResultEnum.STUDENTSIZE_NULL.getStatus(), ResultEnum.STUDENTSIZE_NULL.getMessage());
		}
		if("school".equals(type)) {
			List<ScreeningWearMapper> list = screening_wear_dao.findBySchoolIdOrderByGenTimeDesc(Integer.valueOf(params.get("id")),page, 10);
			int count = screening_wear_dao.findcountBySchoolId(Integer.valueOf(params.get("id")));
			end.put("totalElements", count);
			end.put("content", list);
			return (list.size()!=0)?ResultVOUtil.success(end):ResultVOUtil.error(ResultEnum.STUDENTSIZE_NULL.getStatus(), ResultEnum.STUDENTSIZE_NULL.getMessage());
		}
		List<ScreeningWearMapper> list = screening_wear_dao.findAllByOrderByGenTimeDesc(page,10);
		int count = screening_wear_dao.findcount();
		end.put("totalElements", count);
		end.put("content", list);
		return (list.size()!=0)?ResultVOUtil.success(end):ResultVOUtil.error(ResultEnum.STUDENTSIZE_NULL.getStatus(), ResultEnum.STUDENTSIZE_NULL.getMessage());
	}

	@Override
	public ResultVO deleteScreeningWear(Map<String, String> params) {
		String[] split = params.get("token").split("-");
		if(!"1".equals(split[1]))return ResultVOUtil.error(ResultEnum.RIGHT_ERROR.getStatus(), ResultEnum.RIGHT_ERROR.getMessage());
		ScreeningWearMapper po = screening_wear_dao.findById(Integer.valueOf(params.get("id"))).get();
		params.put("id", po.getStudentId()+"");
		screening_wear_dao.delete(po);
		return screeningWearByStudent(params);
	}

	@Override
	public ResultVO screeningExcel(Map<String, String> params) {
		String type = params.get("type");
		Integer id = 0 ; 
		if(!StringUtils.isEmpty(params.get("id"))) id = Integer.valueOf(params.get("id"));
		List<ScreeningMapper> list = new ArrayList<>();
		List<StudentMapper> student = null;
		if("student".equals(type)) {
			list = screening_dao.findExcel(id);
		}else if("class".equals(type)) {
			list = screening_dao.findByClassIdOrderByGenTimeDesc(id);
			student=student_dao.findByClassesId(id);
		}else if("school".equals(type)) {
			list = screening_dao.findBySchoolIdOrderByGenTimeDesc(id);
			student=student_dao.findBySchoolId(id);
		}else {
			list = screening_dao.findAllByOrderByGenTimeDesc();
			student=student_dao.findAll();
		}
		ExcelUtil.createExcel(Excel(list,student), strArray);
		return ResultVOUtil.success();
	}

	@Override
	public ResultVO screeningWearExcel(Map<String, String> params) {
		String type = params.get("type");
		Integer id = 0 ; 
		if(!StringUtils.isEmpty(params.get("id"))) id = Integer.valueOf(params.get("id"));
		List<ScreeningWearMapper> list = new ArrayList<>();
		List<StudentMapper> student = null;
		if("student".equals(type)) {
			list = screening_wear_dao.findExcel(Integer.valueOf(params.get("id")));
		}else if("class".equals(type)) {
			list = screening_wear_dao.findByClassIdOrderByGenTimeDesc(Integer.valueOf(params.get("id")));
			student=student_dao.findByClassesId(id);
		}else if("school".equals(type)) {
			list = screening_wear_dao.findBySchoolIdOrderByGenTimeDesc(Integer.valueOf(params.get("id")));
			student=student_dao.findBySchoolId(id);
		}else {
			list = screening_wear_dao.findAllByOrderByGenTimeDesc();
			student=student_dao.findAll();
		}
		ExcelUtil.createExcel(WearExcel(list,student), strwearArray);
		return ResultVOUtil.success();
	}

	@Override
	public ResultVO screeningByStudent(Map<String, String> params) {
		int page = 0 ;
		if(!StringUtils.isEmpty(params.get("page")))page = Integer.valueOf(params.get("page"))-1;
		Page<ScreeningMapper> list = screening_dao.findByStudentIdOrderByGenTimeDesc(Integer.valueOf(params.get("id")),PageRequest.of(page, 10));
		return (list.getContent().size()!=0)?ResultVOUtil.success(list):ResultVOUtil.error(ResultEnum.STUDENTSIZE_NULL.getStatus(), ResultEnum.STUDENTSIZE_NULL.getMessage());
	}

	@Override
	public ResultVO screeningWearByStudent(Map<String, String> params) {
		int page = 0 ;
		if(!StringUtils.isEmpty(params.get("page")))page = Integer.valueOf(params.get("page"))-1;
		Page<ScreeningWearMapper> list = screening_wear_dao.findByStudentIdOrderByGenTimeDesc(Integer.valueOf(params.get("id")),PageRequest.of(page, 10));
		return (list.getContent().size()!=0)?ResultVOUtil.success(list):ResultVOUtil.error(ResultEnum.STUDENTSIZE_NULL.getStatus(), ResultEnum.STUDENTSIZE_NULL.getMessage());
	}

	@Override
	public ResultVO screeningStudentExcel(Map<String, String> params) {
		List<ScreeningMapper> list = screening_dao.findByStudentIdOrderByGenTimeDesc(Integer.valueOf(params.get("id")));
		ExcelUtil.createExcel(Excel(list,null), strArray);
		return ResultVOUtil.success();
	}

	@Override
	public ResultVO screeningWearStudentExcel(Map<String, String> params) {
		List<ScreeningWearMapper> list = screening_wear_dao.findByStudentIdOrderByGenTimeDesc(Integer.valueOf(params.get("id")));
		ExcelUtil.createExcel(WearExcel(list,null), strwearArray);
		return ResultVOUtil.success();
	}

	@Override
	public Map<String, List<String>> Excel(List<ScreeningMapper> list,List<StudentMapper> student) {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		if(student.size()!=0) {
			for (int i = 0; i < list.size(); i++) {
				ArrayList<String> members = new ArrayList<String>();
				members.add(list.get(i).getSchoolName() + "");
				members.add(list.get(i).getClassName());
				members.add(list.get(i).getStudentName());
				members.add(list.get(i).getVisionRight());
				members.add(list.get(i).getVisionLeft());
				map.put(list.get(i).getStudentId() + "", members);
			}
			for (StudentMapper studentMapper : student) {
				if(map.get(studentMapper.getId()+"")!=null)continue;
				ArrayList<String> members = new ArrayList<String>();
				members.add(studentMapper.getSchoolName() + "");
				members.add(studentMapper.getClassesName());
				members.add(studentMapper.getName());
				members.add("暂无数据");
				members.add("暂无数据");
				map.put(studentMapper.getId() + "", members);
			}
		}else {
			for (int i = 0; i < list.size(); i++) {
				ArrayList<String> members = new ArrayList<String>();
				members.add(list.get(i).getSchoolName() + "");
				members.add(list.get(i).getClassName());
				members.add(list.get(i).getStudentName());
				members.add(list.get(i).getVisionRight());
				members.add(list.get(i).getVisionLeft());
				map.put(list.get(i).getId() + "", members);
			}
		}
		return map;
	}

	@Override
	public Map<String, List<String>> WearExcel(List<ScreeningWearMapper> list,List<StudentMapper> student) {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		if(student.size()!=0) {
			for (int i = 0; i < list.size(); i++) {
				ArrayList<String> members = new ArrayList<String>();
				members.add(list.get(i).getSchoolName() + "");
				members.add(list.get(i).getClassName());
				members.add(list.get(i).getStudentName());
				members.add(list.get(i).getVisionRight());
				members.add(list.get(i).getVisionLeft());
				map.put(list.get(i).getStudentId() + "", members);
			}
			for (StudentMapper studentMapper : student) {
				if(map.get(studentMapper.getId()+"")!=null)continue;
				ArrayList<String> members = new ArrayList<String>();
				members.add(studentMapper.getSchoolName() + "");
				members.add(studentMapper.getClassesName());
				members.add(studentMapper.getName());
				members.add("暂无数据");
				members.add("暂无数据");
				map.put(studentMapper.getId() + "", members);
			}
		}else {
			for (int i = 0; i < list.size(); i++) {
				ArrayList<String> members = new ArrayList<String>();
				members.add(list.get(i).getSchoolName() + "");
				members.add(list.get(i).getClassName());
				members.add(list.get(i).getStudentName());
				members.add(list.get(i).getVisionRight());
				members.add(list.get(i).getVisionLeft());
				map.put(list.get(i).getId() + "", members);
			}
		}
		return map;
	}

	@Override
	public ResultVO diopterList(Map<String, String> params) {
		Integer page = 0,count=0;
		if(!StringUtils.isEmpty(params.get("page")))page = (Integer.valueOf(params.get("page"))-1)*10;
		String type = params.get("type");
		List<DiopterMapper> list = new ArrayList<>();
		Map<String, Object> end = new HashMap<>();
		end.put("size", 10);
		end.put("number", (StringUtils.isEmpty(params.get("page")))?0:Integer.valueOf(params.get("page"))-1);
		if("student".equals(type)) {
			StudentMapper student = student_dao.findById(Integer.valueOf(params.get("id"))).get();
			DiopterMapper po = diopter_dao.findTopByStudentIdOrderByIdDesc(student.getId());
			if(po==null)po = new DiopterMapper(student);
			list.add(po);
			count = 1;
			end.put("totalElements", count);
			end.put("content", list);
			return (list.size()!=0)?ResultVOUtil.success(end):ResultVOUtil.error(ResultEnum.STUDENTSIZE_NULL.getStatus(), ResultEnum.STUDENTSIZE_NULL.getMessage());
		}
		if("class".equals(type)) {
			List<Integer> ids = new ArrayList<>();
			if(!StringUtils.isEmpty(params.get("page")))page = Integer.valueOf(params.get("page"))-1;
			Page<StudentMapper> findByClassesId = student_dao.findByClassesId(Integer.valueOf(params.get("id")),PageRequest.of(page, 10));
			for (StudentMapper student : findByClassesId.getContent()) {
				DiopterMapper po = diopter_dao.findTopByStudentIdOrderByIdDesc(student.getId());
				if(po==null)po = new DiopterMapper(student);
				list.add(po);
			}
			count = student_dao.countByClassesId(Integer.valueOf(params.get("id")));
			end.put("totalElements", count);
			end.put("content", list);
			return (list.size()!=0)?ResultVOUtil.success(end):ResultVOUtil.error(ResultEnum.STUDENTSIZE_NULL.getStatus(), ResultEnum.STUDENTSIZE_NULL.getMessage());
		}
		if("school".equals(type)) {
			if(!StringUtils.isEmpty(params.get("page")))page = Integer.valueOf(params.get("page"))-1;
			Page<StudentMapper> findBySchoolId = student_dao.findBySchoolId(Integer.valueOf(params.get("id")),PageRequest.of(page, 10));
			for (StudentMapper student : findBySchoolId.getContent()) {
				DiopterMapper po = diopter_dao.findTopByStudentIdOrderByIdDesc(student.getId());
				if(po==null)po = new DiopterMapper(student);
				list.add(po);
			}
			count = student_dao.countBySchoolId(Integer.valueOf(params.get("id")));
			end.put("totalElements", findBySchoolId.getTotalElements());
			end.put("content", list);
			return (list.size()!=0)?ResultVOUtil.success(end):ResultVOUtil.error(ResultEnum.STUDENTSIZE_NULL.getStatus(), ResultEnum.STUDENTSIZE_NULL.getMessage());
		}
		list = diopter_dao.findAllByOrderById(page,10);
		count = diopter_dao.findcount();
		end.put("totalElements", count);
		end.put("content", list);
		return (list.size()!=0)?ResultVOUtil.success(end):ResultVOUtil.error(ResultEnum.STUDENTSIZE_NULL.getStatus(), ResultEnum.STUDENTSIZE_NULL.getMessage());
	
	}

	@Override
	public ResultVO deleteDiopter(Map<String, String> params) {
		diopter_dao.deleteById(Integer.valueOf(params.get("id")));
		return ResultVOUtil.success();
	}

	@Override
	public ResultVO diopterExcelOut(Map<String, String> params) {
		String type = params.get("type");
		Integer id = 0 ; 
		if(!StringUtils.isEmpty(params.get("id"))) id = Integer.valueOf(params.get("id"));
		List<StudentMapper> list = new ArrayList<>();
		if("student".equals(type)) {
			list.add(student_dao.findById(Integer.valueOf(params.get("id"))).get());
		}else if("class".equals(type)) {
			list = student_dao.findByClassesId(Integer.valueOf(params.get("id")));
		}else if("school".equals(type)) {
			list = student_dao.findBySchoolId(Integer.valueOf(params.get("id")));
		}
		if(list.size()<1)return ResultVOUtil.error(ResultEnum.STUDENTSIZE_NULL.getStatus(), ResultEnum.STUDENTSIZE_NULL.getMessage());
		for (StudentMapper studentMapper : list) {
			System.out.println(studentMapper.getName());
		}
		ExcelUtil.createExcel(diopterExcel(list), diopterArray);
		
		return ResultVOUtil.success();
	}

	@Override
	public Map<String, List<String>> diopterExcel(List<StudentMapper> student) {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		for (StudentMapper studentMapper : student) {
			if(map.get(studentMapper.getId()+"")!=null)continue;
			ArrayList<String> members = new ArrayList<String>();
			members.add(studentMapper.getSchoolName() + "");
			members.add(studentMapper.getClassesName());
			members.add(studentMapper.getName());
			members.add(StringUtils.isEmpty(studentMapper.getDiopterRight())?"暂无数据":studentMapper.getDiopterRight());
			members.add(StringUtils.isEmpty(studentMapper.getDiopterLeft())?"暂无数据":studentMapper.getDiopterLeft());
			map.put(studentMapper.getId() + "", members);
		}
		return map;
	}

	@Override
	public ResultVO diopterByStudent(Map<String, String> params) {
		int page = 0 ;
		if(!StringUtils.isEmpty(params.get("page")))page = Integer.valueOf(params.get("page"))-1;
		Page<DiopterMapper> list = diopter_dao.findByStudentIdOrderByIdDesc(Integer.valueOf(params.get("id")),PageRequest.of(page, 10));
		return (list.getContent().size()!=0)?ResultVOUtil.success(list):ResultVOUtil.error(ResultEnum.STUDENTSIZE_NULL.getStatus(), ResultEnum.STUDENTSIZE_NULL.getMessage());
	}
	
	/* 
	 * 批量导入学生数据
	 */
	@Override
	@Transactional
	public ResultVO readDiopterExcel(Integer classId,MultipartFile export,MultipartFile nameList) {
		System.out.println("批量操作开始");
		List<DiopterMapper> diopterInfo = readexcel.getDiopterInfo(export);
		List<StudentMapper> studentInfo = readexcel.getStudentInfo(classId, nameList);
		Collections.reverse(studentInfo);
		System.out.println(diopterInfo.size()+"----"+studentInfo.size());
		if(diopterInfo.size()!=studentInfo.size())return ResultVOUtil.error(ResultEnum.READEXCEL_ERROR.getStatus(), ResultEnum.READEXCEL_ERROR.getMessage());
		for (int i = 0; i < diopterInfo.size(); i++) {
			diopterInfo.get(i).setStudentMapper(studentInfo.get(i));
			studentInfo.get(i).setDiopterLeft(diopterInfo.get(i).getDiopterLeft());
			studentInfo.get(i).setDiopterRight(diopterInfo.get(i).getDiopterRight());
		}
		diopter_dao.saveAll(diopterInfo);
		student_dao.saveAll(studentInfo);
		return ResultVOUtil.success();
	}


	@Override
	public ResultVO saveDiopter(Map<String, String> params) {
		return null;
//		Integer type = Integer.valueOf(params.get("type"));
//		Integer id = Integer.valueOf(params.get("id"));
//		if(type==1) {
//			List<DiopterMapper> polist = diopter_dao.findByStudentIdOrderByIdDesc(id);
//			if(polist.size()==0||polist==null) {
//				StudentMapper student = student_dao.findById(id).get();
//				if(!StringUtils.isEmpty(params.get("diopterLeft"))) student.setDiopterLeft(params.get("diopterLeft"));
//				if(!StringUtils.isEmpty(params.get("diopterRight")))student.setDiopterRight(params.get("diopterRight"));
//				DiopterMapper po = new DiopterMapper(student);
//				diopter_dao.save(po);
//				student_dao.save(student);
//				return ResultVOUtil.success();
//			}else{
//				StudentMapper student = student_dao.findById(id).get();
//				DiopterMapper po = polist.get(0);
//				if(!StringUtils.isEmpty(params.get("diopterLeft"))) {
//					student.setDiopterLeft(params.get("diopterLeft"));
//					po.setDiopterLeft(params.get("diopterLeft"));
//				}
//				if(!StringUtils.isEmpty(params.get("diopterRight"))) {
//					student.setDiopterRight(params.get("diopterRight"));
//					po.setDiopterRight(params.get("diopterRight"));
//				}
//				diopter_dao.save(po);
//				student_dao.save(student);
//				return ResultVOUtil.success();
//			}
//		}else {
//			DiopterMapper po = diopter_dao.findById(id).get();
//			if(!StringUtils.isEmpty(params.get("diopterLeft"))) po.setDiopterLeft(params.get("diopterLeft"));
//			if(!StringUtils.isEmpty(params.get("diopterRight")))po.setDiopterRight(params.get("diopterRight"));
//			diopter_dao.save(po);
//			return ResultVOUtil.success();
//		}
	}

	
}
