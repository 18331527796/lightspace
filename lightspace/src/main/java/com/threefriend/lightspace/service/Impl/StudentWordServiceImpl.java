package com.threefriend.lightspace.service.Impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.threefriend.lightspace.Exception.ReadWordException;
import com.threefriend.lightspace.Exception.SendMessageException;
import com.threefriend.lightspace.enums.AccountEnums;
import com.threefriend.lightspace.mapper.MsgTempMapper;
import com.threefriend.lightspace.mapper.ParentMapper;
import com.threefriend.lightspace.mapper.StudentMapper;
import com.threefriend.lightspace.mapper.StudentWordMapper;
import com.threefriend.lightspace.mapper.xcx.GzhUserMapper;
import com.threefriend.lightspace.mapper.xcx.ParentStudentRelation;
import com.threefriend.lightspace.repository.GzhUserRepository;
import com.threefriend.lightspace.repository.MsgTempRepository;
import com.threefriend.lightspace.repository.ParentRepository;
import com.threefriend.lightspace.repository.ParentStudentRepository;
import com.threefriend.lightspace.repository.StudentRepository;
import com.threefriend.lightspace.repository.StudentWordRepository;
import com.threefriend.lightspace.service.StudentWordService;
import com.threefriend.lightspace.util.RedisUtils;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.util.xcx.SendMessageUtils;
import com.threefriend.lightspace.util.xcx.WeChatUtils;
import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.vo.StudentWordVO;

/**
 * 学生word逻辑实现类
 */
@Service
public class StudentWordServiceImpl implements StudentWordService{
	@Resource
	private RedisUtils redisUtil;
	@Autowired
	private GzhUserRepository gzh_dao;	
	@Autowired
	private StudentRepository student_dao;
	@Autowired
	private ReadStudentWord readword;
	@Autowired
	private ParentStudentRepository parent_student_dao;
	@Autowired
	private MsgTempRepository msg_temp_dao;
	@Autowired
	private ParentRepository parent_dao;
	@Autowired
	private StudentWordRepository studentword_dao;
	
	/* 
	 * 解析word文件
	 */
	@Override
	public ResultVO readStudentWord(MultipartFile[] file) {
		SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd");
			for (MultipartFile multipartFile : file) {
				// 获取文件名
	            String name = multipartFile.getOriginalFilename();
	            String[] split2 = name.split("\\.")[0].split("\\+");
	            System.out.println(split2[0]+"---"+split2[1]+"---"+split2[2]);
	            StudentMapper student = student_dao.findBySchoolNameAndClassesNameAndName(split2[0],split2[1], split2[2]);
				if(student==null)throw new ReadWordException(name);
				try {
					readword.readStudentWord(multipartFile, student.getId(),name);
				} catch (Exception e1) {
					System.out.println("读取word出错");
					throw new ReadWordException(name);
				}
				//通过学生id找到所有家长
				List<ParentStudentRelation> findByStudentId = parent_student_dao.findByStudentId(student.getId());
				//遍历所有家长 查看unionid 有的话就发送消息
			try {
				//获取access——token 准备发送消息
				String ACCESS_TOKEN = redisUtil.get("GZHTOKEN"); 
				if(ACCESS_TOKEN==null||"".equals(ACCESS_TOKEN)) {
					ACCESS_TOKEN = WeChatUtils.findAccessToken(AccountEnums.GZHAPPID.getUrl(), AccountEnums.GZHSECRET.getUrl());
					redisUtil.setValueTime("GZHTOKEN", ACCESS_TOKEN, 7000);
				} 
				//查看选中模板
				MsgTempMapper msgtemp = msg_temp_dao.findByTypeAndSelected("word", 1);
				for (ParentStudentRelation parentStudent : findByStudentId) {
					//找到这个家长
					Optional<ParentMapper> findById = parent_dao.findById(parentStudent.getParentId());
					//如果没有就抛异常
					if(!findById.isPresent())throw new Exception();
					ParentMapper parent = findById.get();
					//家长的unionid不能是空的
					if(parent.getUnionId()!=null&&!"".equals(parent.getUnionId())) {
						//通过unionid查找这个家长的公众号openId
						GzhUserMapper findByUnionid = gzh_dao.findByUnionid(parent.getUnionId());
						StudentWordMapper word = studentword_dao.findTopByStudentIdOrderByGenTimeDesc(student.getId());
						if(findByUnionid!=null)SendMessageUtils.wordMessage(msgtemp,findByUnionid.getOpenid(),ACCESS_TOKEN,word.getFarLeft(),word.getFarRight(),word.getName(),DateFormat.format(word.getGenTime()));
					}
				}
			} catch (Exception e) {
				throw new SendMessageException();
			}
			}
			
		return wordList();
	}

	/* 
	 * word列表
	 */
	@Override
	public ResultVO wordList() {
		List<StudentWordMapper> findAll = studentword_dao.findByOrderByGenTimeDesc();
		List<StudentWordVO> end = new ArrayList<StudentWordVO>();
		for (StudentWordMapper studentWordMapper : findAll) {
			StudentWordVO vo = new StudentWordVO();
			BeanUtils.copyProperties(studentWordMapper, vo);
			vo.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(studentWordMapper.getGenTime()));
			vo.setGender(studentWordMapper.getGender());
			end.add(vo);
		}
		return ResultVOUtil.success(end);
	}

	
}
