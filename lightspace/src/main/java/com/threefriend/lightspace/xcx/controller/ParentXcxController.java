package com.threefriend.lightspace.xcx.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.threefriend.lightspace.aspect.Mylog;
import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.xcx.service.Impl.ParentXcxServiceImpl;

/**
 *	家长控制器
 */
@RestController
@RequestMapping("/xcx")
public class ParentXcxController {

	@Autowired
	private ParentXcxServiceImpl parent_impl;
	
	
	@PostMapping("/loginXcx")
	public ResultVO loginXcx(@RequestParam Map<String, String> params) throws Exception {
		return parent_impl.loginXcx(params);
	}
	
	
	@PostMapping("/childrenList")
	public ResultVO childrenList(@RequestParam Map<String, String> params) {
		return parent_impl.childrenList(params);
	}
	
	/**
	 * 绑定孩子 
	 * @param params
	 * @return
	 */
	
	@PostMapping("/binding")
	public ResultVO insertStudent(@RequestParam Map<String, String> params) {
		return parent_impl.insertStudent(params);
	}
	
	/**
	 * 解除绑定孩子 
	 * @param params
	 * @return
	 */
	
	@PostMapping("/relieve")
	public ResultVO relieveStudent(@RequestParam Map<String, String> params) {
		return parent_impl.relieveStudent(params);
	}
	
	/**
	 * 查询孩子详细信息 
	 * @param params
	 * @return
	 */
	
	@PostMapping("/findStudent")
	public ResultVO findStudent(@RequestParam Map<String, String> params) {
		return parent_impl.findStudent(params);
	}
	
	/**
	 * 我的首页
	 * @param params
	 * @return
	 */
	@Mylog(value=("我的首页"))
	@PostMapping("/mine")
	public ResultVO mind(@RequestParam Map<String, String> params) {
		return parent_impl.mine(params);
	}
	
	@Mylog(value=("绑定手机"))
	@PostMapping("/bindingPhone")
	public ResultVO bindingPhone(@RequestParam Map<String, String> params) throws Exception {
		return parent_impl.bindingPhone(params);
	}
	
	/**
	 * 验证是否关注公众号
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/chkGzh")
	public ResultVO chkGzh(@RequestParam Map<String, String> params) throws Exception {
		return parent_impl.chkGzh(params);
	}
	
	/**
	 * 社会注册的孩子
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@Mylog(value=("社会注册"))
	@PostMapping("/registerStudent")
	public ResultVO registerStudent(@RequestParam Map<String, String> params) throws Exception {
		return parent_impl.registerStudent(params);
	}
	
	/**
	 * 移植孩子信息
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/transplantStudent")
	public ResultVO transplantStudent(@RequestParam Map<String, String> params) throws Exception {
		return parent_impl.transplantStudent(params);
	}
	
	/**
	 * 程序首页信息
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/firstPage")
	public ResultVO firstPage(@RequestParam Map<String, String> params) throws Exception {
		return parent_impl.firstPage(params);
	}
	
	/**
	 * 验证校验信息
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/chkCalibration")
	public ResultVO chkCalibration(@RequestParam Map<String, String> params){
		return parent_impl.chkCalibration(params);
	}
	
	/**
	 * 更改校验信息
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/editCalibration")
	public ResultVO editCalibration(@RequestParam Map<String, String> params){
		return parent_impl.editCalibration(params);
	}
	
	
	@PostMapping("/childrenIntegral")
	public ResultVO childrenIntegral(@RequestParam Map<String, String> params) {
		return parent_impl.childrenIntegral(params);
	}
}
