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
