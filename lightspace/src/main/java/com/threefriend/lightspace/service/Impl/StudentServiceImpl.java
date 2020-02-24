package com.threefriend.lightspace.service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.threefriend.lightspace.enums.ResultEnum;
import com.threefriend.lightspace.mapper.StudentMapper;
import com.threefriend.lightspace.repository.ClassesRepository;
import com.threefriend.lightspace.repository.RecordRepository;
import com.threefriend.lightspace.repository.SchoolRepository;
import com.threefriend.lightspace.repository.StudentRepository;
import com.threefriend.lightspace.service.StudentService;
import com.threefriend.lightspace.util.DownloadFileUtil;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.vo.StudentVO;

/**
 *	学生实现类
 */
@Service
public class StudentServiceImpl implements StudentService{
	//文件名
    String FILENAME ="学生导入模板.xlsx";
    //下载展示的文件名
    String NEWNAME="学生导入模板";
    		
	@Autowired
	private StudentRepository student_dao;
	@Autowired
	private SchoolRepository school_dao;
	@Autowired
	private ClassesRepository classes_dao;
	@Autowired
	private RecordRepository record_dao;
	@Autowired
	private ReadStudentExcel readexcel;

	/* 
	 * 学生列表
	 */
	@Override
	public List<StudentMapper> studentList(Map<String, String> params) {
		String[] split = params.get("token").split("-");
		if(split[1].equals("3"))return student_dao.findBySchoolId(Integer.valueOf(split[2]));
		if(split[1].equals("4"))return student_dao.findByClassesId(Integer.valueOf(split[2]));
		return student_dao.findAll();
	}

	/* 
	 * 按照学校班级查学生
	 */
	@Override
	public List<StudentMapper> queryBySidCid(Integer sId, Integer cId) {
		return student_dao.findBySchoolIdAndClassesId(sId, cId);
	}

	/* 
	 * 新增学生信息
	 */
	@Override
	public List<StudentMapper> addStudent(Map<String, String> params) {
		StudentMapper student = new StudentMapper();
		student.setAge(Integer.valueOf(params.get("age")));
		student.setChairHeight(params.get("chairHeight"));
		student.setClassesId(Integer.valueOf(params.get("classId")));
		student.setClassesName(classes_dao.findById(Integer.valueOf(params.get("classId"))).get().getClassName());
		student.setCorrect(Integer.valueOf(params.get("correct")));
		student.setGender(Integer.valueOf(params.get("gender")));
		student.setHeight(params.get("height"));
		student.setName(params.get("name"));
		if(!StringUtils.isEmpty(params.get("nature")))student.setNature(params.get("nature"));
		student.setRegionId(1);
		student.setRegionName("唐山");
		student.setSchoolId(Integer.valueOf(params.get("schoolId")));
		student.setSchoolName(school_dao.findById(Integer.valueOf(params.get("schoolId"))).get().getName());
		student.setSittingHeight(params.get("sittingHeight"));
		student.setWeight(params.get("weight"));
		if(!StringUtils.isEmpty(params.get("description")))student.setDescription(params.get("description"));
		System.out.println(params.get("chairHeight")+"--"+params.get("height")+"--"+params.get("sittingHeight"));
		student_dao.save(student);
		String[] split = params.get("token").split("-");
		if(split[1].equals("3"))return student_dao.findBySchoolId(Integer.valueOf(split[2]));
		if(split[1].equals("4"))return student_dao.findByClassesId(Integer.valueOf(split[2]));
		return student_dao.findAll();
	}

	/* 
	 * 删除学生
	 */
	@Override
	public List<StudentMapper> deleteStudent(Integer id,String token) {
		record_dao.deleteByStudentId(id);
		student_dao.deleteById(id);
		String[] split = token.split("-");
		if(split[1].equals("3"))return student_dao.findBySchoolId(Integer.valueOf(split[2]));
		if(split[1].equals("4"))return student_dao.findByClassesId(Integer.valueOf(split[2]));
		return student_dao.findAll();
	}

	/* 
	 * 保存修改后信息
	 */
	@Override
	public List<StudentMapper> saveStudent(Map<String, String> params) {
		StudentMapper student = student_dao.findById(Integer.valueOf(params.get("id"))).get();
		if(!StringUtils.isEmpty(params.get("age")))student.setAge(Integer.valueOf(params.get("age")));
		if(!StringUtils.isEmpty(params.get("chairHeight")))student.setChairHeight(params.get("chairHeight"));
		if(!StringUtils.isEmpty(params.get("correct")))student.setCorrect(Integer.valueOf(params.get("correct")));
		if(!StringUtils.isEmpty(params.get("gender")))student.setGender(Integer.valueOf(params.get("gender")));
		if(!StringUtils.isEmpty(params.get("height")))student.setHeight(params.get("height"));
		if(!StringUtils.isEmpty(params.get("name")))student.setName(params.get("name"));
		if(!StringUtils.isEmpty(params.get("nature"))) {
			student.setNature(params.get("nature"));
		}else {
			student.setNature("");
		}
		if(!StringUtils.isEmpty(params.get("sittingHeight")))student.setSittingHeight(params.get("sittingHeight"));
		if(!StringUtils.isEmpty(params.get("weight")))student.setWeight(params.get("weight"));
		if(!StringUtils.isEmpty(params.get("description")))student.setDescription(params.get("description"));
		if(!StringUtils.isEmpty(params.get("schoolId"))) {
			student.setSchoolId(Integer.valueOf(params.get("schoolId")));
			student.setSchoolName(school_dao.findById(Integer.valueOf(params.get("schoolId"))).get().getName());
		}
		if(!StringUtils.isEmpty(params.get("classId"))) {
			student.setClassesId(Integer.valueOf(params.get("classId")));
			student.setClassesName(classes_dao.findById(Integer.valueOf(params.get("classId"))).get().getClassName());
		}
		System.out.println(params.get("chairHeight")+"--"+params.get("height")+"--"+params.get("sittingHeight"));
		student_dao.save(student);
		String[] split = params.get("token").split("-");
		if(split[1].equals("3"))return student_dao.findBySchoolId(Integer.valueOf(split[2]));
		if(split[1].equals("4"))return student_dao.findByClassesId(Integer.valueOf(split[2]));
		return student_dao.findAll();
	}

	/* 
	 * 按照id查学生
	 */
	@Override
	public StudentVO findById(Integer id) {
		StudentMapper studentMapper = student_dao.findById(id).get();
		StudentVO vo = new StudentVO();
		BeanUtils.copyProperties(studentMapper, vo);
		vo.getStu_cat().add(studentMapper.getSchoolId());
		vo.getStu_cat().add(studentMapper.getClassesId());
		return vo;
	}

	/* 
	 * 模糊查询
	 */
	@Override
	public ResultVO findByNameLike(String name) {
		List<StudentMapper> list = student_dao.findByNameLike("%"+name+"%");
		if(list==null||list.size()==0)return ResultVOUtil.error(ResultEnum.STUDENTSIZE_NULL.getStatus(), ResultEnum.STUDENTSIZE_NULL.getMessage());
		return ResultVOUtil.success(list);
	}

	/* 
	 * 按照学校班级姓名模糊
	 */
	@Override
	public List<StudentVO> findBySchoolIdAndClassesIdAndNameLike(Integer sId, Integer cId, String name) {
		List<StudentMapper> students = student_dao.findBySchoolIdAndClassesIdAndNameLike(sId, cId, "%"+name+"%");
		List<StudentVO> vo = new ArrayList<>();
		for (StudentMapper studentMapper : students) {
			vo.add(new StudentVO(studentMapper.getId(), studentMapper.getName()));
		}
		return vo;
	}

	@Override
	public ResultVO readStudentExcel(MultipartFile file,String token) {
		List<StudentMapper> studentInfo = readexcel.getStudentInfo(file);
		if(studentInfo==null) {
			System.out.println("读取excel数据返回值是空的");
		}
		
		new Thread(){//直接重写run方法   开启新线程 异步处理excel文件
            @Override 
            public synchronized void run(){ 
                for (StudentMapper studentMapper : studentInfo) {
                	student_dao.save(studentMapper);
                }
            } 
        }.start(); 
        
		String[] split = token.split("-");
		if(split[1].equals("3"))return ResultVOUtil.success(student_dao.findBySchoolId(Integer.valueOf(split[2])));
		if(split[1].equals("4"))return ResultVOUtil.success(student_dao.findByClassesId(Integer.valueOf(split[2])));
		return ResultVOUtil.success(student_dao.findAll());
	}

	@Override
	public ResponseEntity<InputStreamResource> download() {
		ResponseEntity<InputStreamResource> response = null;
        try {
            response = DownloadFileUtil.download(FILENAME, NEWNAME);
        } catch (Exception e) {
            System.out.println("下载模板失败");
        }
        return response;
	}

}
