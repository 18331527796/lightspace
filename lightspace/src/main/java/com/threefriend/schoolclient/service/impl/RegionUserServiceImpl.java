package com.threefriend.schoolclient.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import com.threefriend.lightspace.enums.ResultEnum;
import com.threefriend.lightspace.mapper.ClassesMapper;
import com.threefriend.lightspace.mapper.RegionMapper;
import com.threefriend.lightspace.mapper.SchoolMapper;
import com.threefriend.lightspace.mapper.UserMapper;
import com.threefriend.lightspace.mapper.UserRoleRelation;
import com.threefriend.lightspace.mapper.schoolclient.UserSchoolsMapper;
import com.threefriend.lightspace.repository.RegionRepository;
import com.threefriend.lightspace.repository.RoleRepository;
import com.threefriend.lightspace.repository.SchoolRepository;
import com.threefriend.lightspace.repository.UserRepository;
import com.threefriend.lightspace.repository.UserRoleRepository;
import com.threefriend.lightspace.repository.schoolclient.UserSchoolsRepository;
import com.threefriend.lightspace.service.UserService;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.vo.UserVO;

@Service
public class RegionUserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository user_dao;
	@Autowired
	private SchoolRepository school_dao;
	@Autowired
	private UserRoleRepository user_role_dao;
	@Autowired
	private RoleRepository role_dao;
	@Autowired
	private UserSchoolsRepository user_school_dao;

	@Override
	public ResultVO insertUser(Map<String, String> params) {
		List<UserMapper> list = user_dao.findByLoginName(params.get("loginName"));
		if(list.size()>=1)return ResultVOUtil.error(ResultEnum.LOGINNAME_REPEAT.getStatus(),ResultEnum.LOGINNAME_REPEAT.getMessage());
		Integer roleId = 2;
		SchoolMapper school = school_dao.findById(Integer.valueOf(params.get("schoolId"))).get();
		UserMapper user = new UserMapper();
		user.setGenTime(new Date());
		user.setName(params.get("name"));
		user.setLoginName(params.get("loginName"));
		user.setRoleName(role_dao.findById(roleId).get().getRoleName());
		user.setPassword(DigestUtils.md5DigestAsHex(params.get("password").getBytes()));
		user.setSchoolId(school.getId());
		user.setRegionId(school.getRegionId());
		user.setRegionName(school.getRegionName());
		user_dao.save(user);
		
		UserSchoolsMapper u_s = new UserSchoolsMapper();
		u_s.setSchoolId(school.getId());
		u_s.setUserId(user.getId());
		user_school_dao.save(u_s);
		
		UserRoleRelation userRole= new UserRoleRelation();
		userRole.setUserId(user.getId());
		userRole.setRoleId(roleId);
		user_role_dao.save(userRole);
		return ResultVOUtil.success();
	}

	@Override
	public ResultVO findAll(Map<String, String> params, HttpSession session) {
		Integer regionId = Integer.valueOf(session.getAttribute("regionId").toString());
		int page = 0 ; 
		if(!StringUtils.isEmpty(params.get("page"))) page = Integer.valueOf(params.get("page")) - 1 ;
		List<UserVO> endList = new ArrayList<>();
		List<Integer> ids = school_dao.findIdByRegionId(regionId);
		Page<UserMapper> pageList = user_dao.findBySchoolIdIn(ids,PageRequest.of(page, 10));
		for (UserMapper userMapper : pageList.getContent()) {
			UserVO vo =new UserVO();
			BeanUtils.copyProperties(userMapper, vo);
			List<UserSchoolsMapper> u_s_list = user_school_dao.findByUserId(userMapper.getId());
			List<SchoolMapper> schoolList = new ArrayList<>();
			for (UserSchoolsMapper userSchoolsMapper : u_s_list) {
				if(userSchoolsMapper.getSchoolId()==0)continue;
				schoolList.add(school_dao.findById(userSchoolsMapper.getSchoolId()).get());
			}
			if(schoolList.size()!=0)vo.setChildren(schoolList);
			endList.add(vo);
		}
		Page<UserVO> end = new PageImpl<>(endList, pageList.getPageable(), pageList.getTotalElements());
		return ResultVOUtil.success(end);
	}

	@Override
	public ResultVO deleteUser(Integer id) {
		user_dao.deleteById(id);
		user_role_dao.deleteByUserId(id);
		user_school_dao.deleteByUserId(id);
		return ResultVOUtil.success();
	}

	@Override
	public ResultVO addUserSchool(Map<String, String> params) {
		Integer userId = Integer.valueOf(params.get("userId"));
		Integer schoolId = Integer.valueOf(params.get("schoolId"));
		UserSchoolsMapper chk = user_school_dao.findBySchoolIdAndUserId(schoolId,userId);
		if(chk!=null) return ResultVOUtil.error(ResultEnum.SCHOOLNAME_REPEAT.getStatus(), ResultEnum.SCHOOLNAME_REPEAT.getMessage());
		
		UserSchoolsMapper po = new UserSchoolsMapper();
		po.setSchoolId(schoolId);
		po.setUserId(userId);
		user_school_dao.save(po);
		return ResultVOUtil.success();
	}

	@Override
	public ResultVO deleteUserSchool(Map<String, String> params) {
		System.out.println("删除用户和学校的关联，用户id:"+params.get("userId")+"--学校id:"+params.get("schoolId"));
		Integer userId = Integer.valueOf(params.get("userId"));
		Integer schoolId = Integer.valueOf(params.get("schoolId"));
		user_school_dao.deleteBySchoolIdAndUserId(schoolId,userId);
		return ResultVOUtil.success();
	}


	//咩用
	@Override
	public ResultVO findByNameLike(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	//咩用
	@Override
	public Object login(String loginname, String password, HttpSession session) {
		return null;
	}
	//咩用
	@Override
	public Object getUserRight(String token) {
		// TODO Auto-generated method stub
		return null;
	}
}
