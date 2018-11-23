package com.bandweaver.tunnel.controller.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.dto.StaffDto;
import com.bandweaver.tunnel.common.biz.dto.UserDTO;
import com.bandweaver.tunnel.common.biz.dto.oam.ReqHistoryDto;
import com.bandweaver.tunnel.common.biz.itf.StaffService;
import com.bandweaver.tunnel.common.biz.itf.UserService;
import com.bandweaver.tunnel.common.biz.itf.oam.ReqHistoryService;
import com.bandweaver.tunnel.common.biz.itf.omm.InspectionPlanService;
import com.bandweaver.tunnel.common.biz.pojo.User;
import com.bandweaver.tunnel.common.biz.pojo.omm.InspectionPlan;
import com.bandweaver.tunnel.common.biz.vo.StaffVo;
import com.bandweaver.tunnel.common.biz.vo.oam.ReqHistoryVo;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.log.DescEnum;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.log.WriteLog;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.bandweaver.tunnel.common.platform.util.ContextUtil;

/**
 * ClassName: UserController
 * 
 * @Description: 用户管理
 * @author shaosen
 * @date 2018年6月20日
 */
@Controller
@ResponseBody
public class UserController extends BaseController<User>{

	@Autowired
	private UserService userService;
 
	/**添加用户
	 * @param  user
	 * @return JSONObject
	 * @author shaosen
	 * @date 2018年6月20日
	 */
	@WriteLog(DescEnum.ADD_USER)
	@RequestMapping(value = "users", method = RequestMethod.POST)
	public JSONObject add(@RequestBody User user) {
		userService.addUser(user);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}

	/**通过id获取用户信息
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


	@Override
	public JSONObject delete(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject deleteBatch(String ids) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject update(User pojo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 获取超级管理员信息
	 * @return
	 * @author ya.liu
	 * @Date 2018年11月17日
	 */
	@RequestMapping(value = "roles/users", method = RequestMethod.GET)
	public JSONObject getUsersByRoleDesc() {
		String roleDesc = "超级管理员";
		List<User> list = userService.getUserByRoleDesc(roleDesc);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
	}


}
