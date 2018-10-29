package com.bandweaver.tunnel.controller.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.itf.RoleService;
import com.bandweaver.tunnel.common.biz.pojo.Permission;
import com.bandweaver.tunnel.common.biz.pojo.Role;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;

@Controller
@RequestMapping("/roles")
@ResponseBody
public class RoleController {

	@Autowired
	private RoleService roleService;
	
	/**
	 * @Description: 获取所有角色
	 * @param @return   
	 * @return JSONObject  
	 * @throws
	 * @author shaosen
	 * @date 2018年5月22日
	 */
	@RequestMapping(method=RequestMethod.GET)
	public JSONObject getAllRoles() {
		List<Role> list = roleService.getAllRoles();
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,list);
	}
	
	/**
	 * @Description: 添加角色
	 * @param @param role
	 * @param @return   
	 * @return JSONObject  
	 * @throws
	 * @author shaosen
	 * @date 2018年5月22日
	 */
	@RequestMapping(method=RequestMethod.POST)
	public JSONObject addRole(@RequestBody Role role) {
		roleService.add(role);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	
	/**
	 * @Description: 查询该角色对应的权限列表
	 * @param @param id
	 * @param @return   
	 * @return JSONObject  
	 * @throws
	 * @author shaosen
	 * @date 2018年5月22日
	 */
	@RequestMapping(value="/{id}/permissions",method=RequestMethod.GET)
	public JSONObject getPermissionsByRoleId(@PathVariable("id")int id) {
		List<Permission> list = roleService.getPermissionsByRoleId(id);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,list);
	}
}
