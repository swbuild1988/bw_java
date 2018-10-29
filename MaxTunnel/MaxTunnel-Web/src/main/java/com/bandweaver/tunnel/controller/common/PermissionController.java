package com.bandweaver.tunnel.controller.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.itf.PermissionService;
import com.bandweaver.tunnel.common.biz.pojo.Permission;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;

@Controller
@RequestMapping("/permissions")
@ResponseBody
public class PermissionController {

	
	@Autowired
	private PermissionService permissionService;
	
	/**
	 * @Description: 添加权限
	 * @param @param permission
	 * @param @return   
	 * @return JSONObject  
	 * @throws
	 * @author shaosen
	 * @date 2018年5月23日
	 */
	@RequestMapping(method=RequestMethod.POST)
	public JSONObject add(@RequestBody Permission permission) {
		permissionService.add(permission);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
}
