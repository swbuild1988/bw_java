package com.bandweaver.tunnel.controller.common;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.dto.UserDTO;
import com.bandweaver.tunnel.common.biz.itf.common.UserService;
import com.bandweaver.tunnel.common.biz.pojo.common.User;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.exception.BandWeaverException;
import com.bandweaver.tunnel.common.platform.log.DescEnum;
import com.bandweaver.tunnel.common.platform.log.WriteLog;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.bandweaver.tunnel.common.platform.util.DataTypeUtil;
import com.bandweaver.tunnel.common.platform.util.PropertiesUtil;
import com.bandweaver.tunnel.common.platform.util.Sha256;

/**
 * ClassName: UserController
 * 
 * @Description: 用户管理
 * @author shaosen
 * @date 2018年6月20日
 */
@Controller
@ResponseBody
public class UserController {

	@Autowired
	private UserService userService;
	
	
	/**重置密码 
	 * @param id 账号/员工id
	 * @return   
	 * @author shaosen
	 * @Date 2018年12月1日
	 */
	@WriteLog(DescEnum.RESET_PW)
	@RequestMapping(value="users/init-pw/{id}",method=RequestMethod.GET)
	public JSONObject resetPw(@PathVariable Integer id) {
		
		String initPassword = (String) PropertiesUtil.getValue("cm.init.password");
		User user = new User();
		user.setId(id);
		user.setPassword(Sha256.getSHA256StrJava(initPassword));
		userService.updateByPrimaryKeySelective(user);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	
	
	/**修改登录密码 
	 * @param id 主键id
	 * @param oldPw 旧密码（sha256加密之后的密码）
	 * @param newPw 新密码（sha256加密之后的密码）
	 * @return   
	 * @author shaosen
	 * @Date 2018年12月1日
	 */
	@WriteLog(DescEnum.CHANGE_PW)
	@RequestMapping(value="users/pw",method=RequestMethod.POST)
	public JSONObject changePw(@RequestBody Map<String, String> map) {
		
		Integer id = DataTypeUtil.toInteger(map.get("id"));
		String oldPw = DataTypeUtil.toString(map.get("oldPw"));
		String newPw = DataTypeUtil.toString(map.get("newPw"));
//		String reptPw = DataTypeUtil.toString(map.get("reptPw"));//前端做校验
		
		User user = userService.selectByPrimaryKey(id);
		if(user == null)
//			return CommonUtil.returnStatusJson(StatusCodeEnum.E_20006);//用户名不存在
			throw new BandWeaverException("用户名不存在");
		
		if(!user.getPassword().equals(oldPw))
//			return CommonUtil.returnStatusJson(StatusCodeEnum.E_20007);//密码错误
			throw new BandWeaverException("原密码错误");
		
		User upt = new User();
		upt.setId(id);
		upt.setPassword(newPw);
		userService.updateByPrimaryKeySelective(upt);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	
	

	/**通过id获取用户账号信息
	 * @param id
	 * @return JSONObject
	 * @author shaosen
	 * @date 2018年6月20日
	 */
	@RequestMapping(value = "users/{id}", method = RequestMethod.GET)
	public JSONObject getById(@PathVariable("id") Integer id) {
		UserDTO user = userService.getUser(id);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, user);
	}
	
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "users/batch/{ids}",method = RequestMethod.DELETE)
	public JSONObject deleteBatch(@PathVariable String ids) {
		List<Integer> list = CommonUtil.convertStringToList(ids);
		userService.deleteBatch(list);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}

	/**
	 * 获取超级管理员信息
	 * @return
	 * @author ya.liu
	 * @Date 2018年11月17日
	 */
	@RequestMapping(value = "roles/users", method = RequestMethod.GET)
	public JSONObject getUsersByRoleDesc() {
		String roleName = "admin";
		List<JSONObject> list = userService.getUsersByRole(roleName);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
	}

}
