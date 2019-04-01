package com.bandweaver.tunnel.controller.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.bandweaver.tunnel.common.biz.pojo.common.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.dto.UserDTO;
import com.bandweaver.tunnel.common.biz.dto.common.RoleDto;
import com.bandweaver.tunnel.common.biz.itf.common.PermissionService;
import com.bandweaver.tunnel.common.biz.itf.common.RoleService;
import com.bandweaver.tunnel.common.biz.itf.common.UserService;
import com.bandweaver.tunnel.common.biz.pojo.common.Permission;
import com.bandweaver.tunnel.common.biz.pojo.common.Role;
import com.bandweaver.tunnel.common.biz.pojo.common.RolePermission;
import com.bandweaver.tunnel.common.biz.vo.common.PermissionVo;
import com.bandweaver.tunnel.common.biz.vo.common.RoleVo;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.bandweaver.tunnel.common.platform.util.StringTools;
import com.github.pagehelper.PageInfo;

/**权限管理
 * @author shaosen
 * @date 2018年12月17日
 */
@Controller
@ResponseBody
public class ShiroController {
	
	
	@Autowired
	private PermissionService permissionService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private UserService userService;
	
	/**添加权限
	 * @param menuCode um/user/...
	 * @param menuName 用户
	 * @param permissionCode user:list
	 * @param permissionName 用户列表
	 * @return   
	 * @author shaosen
	 * @Date 2018年12月18日
	 */
	@RequestMapping(value="permissions",method=RequestMethod.POST)
	public JSONObject addPermission(@RequestBody JSONObject reqJson) {
		CommonUtil.hasAllRequired(reqJson, "menuCode,menuName,permissionCode,permissionName");
		Permission permission = CommonUtil.parse2Obj(reqJson, Permission.class);
		permissionService.addPermission(permission);
		return CommonUtil.success();
	}
	
	
	/**批量删除权限 
	 * @param ids
	 * @return   
	 * @author shaosen
	 * @Date 2018年12月18日
	 */
	@RequestMapping(value="permissions/batch/{ids}",method=RequestMethod.DELETE)
	public JSONObject deletePermisssion(@PathVariable String ids) {
		List<Integer> idList = CommonUtil.convertStringToList(ids);
		permissionService.deleteBatch(idList);
		return CommonUtil.success();
	}

	
	/**修改权限 
	 * @param reqJson
	 * @return   
	 * @author shaosen
	 * @Date 2018年12月18日
	 */
	@RequestMapping(value="permissions",method=RequestMethod.PUT)
	public JSONObject updatePermission(@RequestBody JSONObject reqJson) {
		CommonUtil.hasAllRequired(reqJson, "id,menuCode,menuName,permissionCode,permissionName");
		Permission permission = CommonUtil.parse2Obj(reqJson, Permission.class);
		permissionService.updatePermission(permission);
		return CommonUtil.success();
	}
	
	
	/**获取权限信息 
	 * @param id
	 * @return {"msg":"请求成功","code":"200","data":{"id":1000,"menuCode":"customer","menuName":"客户","permissionCode":"customer:list","permissionName":"列表","crtTime":1545096724000}}  
	 * @author shaosen
	 * @Date 2018年12月18日
	 */
	@RequestMapping(value="permissions/{id}",method=RequestMethod.GET)
	public JSONObject getPermission(@PathVariable Integer id) {
		Permission permission = permissionService.getPermission(id);
		return CommonUtil.success(permission);
	}
	
	
	/**获取所有权限，给角色分配权限时使用 
	 * @return   {"msg":"请求成功","code":"200","data":[{"permissionList":[{"id":1,"menuCode":"user","menuName":"用户","permissionCode":"user:list","permissionName":"用户列表","crtTime":1544716800000},{"id":2,"menuCode":"user","menuName":"用户","permissionCode":"user:add","permissionName":"用户添加","crtTime":1544716800000}],"menuName":"用户"},{"permissionList":[{"id":3,"menuCode":"tunnel","menuName":"管廊","permissionCode":"tunnel:list","permissionName":"管廊列表","crtTime":1544716800000},{"id":4,"menuCode":"tunnel","menuName":"管廊","permissionCode":"tunnel:add","permissionName":"管廊添加","crtTime":1544716800000}],"menuName":"管廊"},{"permissionList":[{"id":1001,"menuCode":"customer","menuName":"客户","permissionCode":"customer:add","permissionName":"添加","crtTime":1545097203000}],"menuName":"客户"}]}
	 * @author shaosen
	 * @Date 2018年12月19日
	 */
	@RequestMapping(value="permissions/list",method=RequestMethod.GET)
	public JSONObject getAllPermissons() {
		List<JSONObject> returnData = new ArrayList<>();
		Set<String> menuList = permissionService.getAllMenuName();
		for (String menuName : menuList) {
			JSONObject js = new JSONObject();
			js.put("menuName", menuName);
			
			List<Permission> permissionList = permissionService.getPermissionsByMenu(menuName);
			js.put("permissionList", permissionList);
			returnData.add(js);
		}
		return CommonUtil.success(returnData);
	}
	
	
	/**权限分页查询 
	 * @param pageNum
	 * @param pageSize
	 * @param menuCode
	 * @param permissionCode
	 * @return   {"msg":"请求成功","code":"200","data":{"total":5,"list":[{"id":1001,"menuCode":"customer","menuName":"客户","permissionCode":"customer:add","permissionName":"添加","crtTime":1545097203000},{"id":1,"menuCode":"user","menuName":"用户","permissionCode":"user:list","permissionName":"用户列表","crtTime":1544716800000},{"id":2,"menuCode":"user","menuName":"用户","permissionCode":"user:add","permissionName":"用户添加","crtTime":1544716800000},{"id":3,"menuCode":"tunnel","menuName":"管廊","permissionCode":"tunnel:list","permissionName":"管廊列表","crtTime":1544716800000},{"id":4,"menuCode":"tunnel","menuName":"管廊","permissionCode":"tunnel:add","permissionName":"管廊添加","crtTime":1544716800000}],"pageNum":1,"pageSize":10,"size":5,"startRow":1,"endRow":5,"pages":1,"prePage":0,"nextPage":0,"isFirstPage":true,"isLastPage":true,"hasPreviousPage":false,"hasNextPage":false,"navigatePages":8,"navigatepageNums":[1],"navigateFirstPage":1,"navigateLastPage":1,"lastPage":1,"firstPage":1}}
	 * @author shaosen
	 * @Date 2018年12月18日
	 */
	@RequestMapping(value="permissions/datagrid",method=RequestMethod.POST)
	public JSONObject dataGrid(@RequestBody JSONObject reqJson) {
		CommonUtil.hasAllRequired(reqJson, "pageNum,pageSize");
		PermissionVo vo = CommonUtil.parse2Obj(reqJson, PermissionVo.class);
		PageInfo<Permission> pageInfo = permissionService.dataGrid(vo);
		return CommonUtil.success(pageInfo);
	}
	
	
	/**添加角色 
	 * @param roleName
	 * @return   
	 * @author shaosen
	 * @Date 2018年12月19日
	 */
	@RequestMapping(value="roles",method=RequestMethod.POST)
	public JSONObject addRole(@RequestBody JSONObject reqJson) {
		CommonUtil.hasAllRequired(reqJson, "roleName");
		Role role = CommonUtil.parse2Obj(reqJson, Role.class);
		roleService.addRole(role);
		return CommonUtil.success();
	}
	
	
	/**批量删除角色 
	 * @param ids 逗号分隔
	 * @return   
	 * @author shaosen
	 * @Date 2018年12月26日
	 */
	@RequestMapping(value="roles/batch/{ids}",method=RequestMethod.DELETE)
	public JSONObject deleteRoleBatch(@PathVariable String ids) {
		List<Integer> list = CommonUtil.convertStringToList(ids);
		roleService.deleteRoleBatch(list);
		return CommonUtil.success();
	}
	
	
	/**修改角色 
	 * @param id
	 * @param name
	 * @return   
	 * @author shaosen
	 * @Date 2018年12月26日
	 */
	@RequestMapping(value="roles",method=RequestMethod.PUT)
	public JSONObject updateRole(@RequestBody JSONObject reqJson) {
		CommonUtil.hasAllRequired(reqJson, "id,roleName");
		Role role = CommonUtil.parse2Obj(reqJson, Role.class);
		roleService.updateRole(role);
		return CommonUtil.success();
	}
	
	
	/**角色分页查询，并显示对应的权限列表 
	 * 说明：当角色为admin时，显示对应的权限信息为字符串“具有所有权限”
	 * @param roleName 角色名称
	 * @param pageNum
	 * @param pageSize
	 * @param startTime
	 * @param endTime
	 * @return   {"msg":"请求成功","code":"200","data":{"total":3,"list":[{"id":1,"roleName":"admin","permissionList":[],"crtTime":1546358400000},{"id":2,"roleName":"manager","permissionList":[{"id":1000,"menuCode":"tunnel","menuName":"管廊","permissionCode":"tunnel:list","permissionName":"列表","crtTime":1546358400000},{"id":1002,"menuCode":"tunnel","menuName":"管廊","permissionCode":"tunnel:update","permissionName":"更新","crtTime":1546358400000},{"id":1001,"menuCode":"tunnel","menuName":"管廊","permissionCode":"tunnel:add","permissionName":"添加","crtTime":1546358400000}],"crtTime":1546358400000},{"id":3,"roleName":"test","permissionList":[{"id":1003,"menuCode":"tunnel","menuName":"管廊","permissionCode":"tunnel:delete","permissionName":"删除","crtTime":1546358400000}],"crtTime":1546358400000}],"pageNum":1,"pageSize":10,"size":3,"startRow":1,"endRow":3,"pages":1,"prePage":0,"nextPage":0,"isFirstPage":true,"isLastPage":true,"hasPreviousPage":false,"hasNextPage":false,"navigatePages":8,"navigatepageNums":[1],"navigateFirstPage":1,"navigateLastPage":1,"lastPage":1,"firstPage":1}}
	 * @author shaosen
	 * @Date 2019年1月2日
	 */
	@RequestMapping(value="roles/datagrid",method=RequestMethod.POST)
	public JSONObject rolesDataGrid(@RequestBody JSONObject reqJson) {
		CommonUtil.hasAllRequired(reqJson, "pageNum,pageSize");
		RoleVo roleVo = CommonUtil.parse2Obj(reqJson, RoleVo.class);
		PageInfo<RoleDto> result = roleService.dataGrid(roleVo);
		
		List<RoleDto> roleList = result.getList();
		for (RoleDto role : roleList) {
			if("admin".equals(role.getRoleName())) {
				role.setPermissionList(new ArrayList<>());
			}else {
				List<Permission> pList = permissionService.getPermissionsByRole(role.getId());
				role.setPermissionList(pList);
			}
		}
		return CommonUtil.success(result);
	}
	
	
	/**根据id查询角色信息(包含具有的权限信息)
	 * @param id 角色id
	 * @return  {"msg":"请求成功","code":"200","data":{"id":2,"roleName":"manager","permissionList":[{"id":1000,"menuCode":"tunnel","menuName":"管廊","permissionCode":"tunnel:list","permissionName":"列表","crtTime":1546358400000},{"id":1002,"menuCode":"tunnel","menuName":"管廊","permissionCode":"tunnel:update","permissionName":"更新","crtTime":1546358400000},{"id":1001,"menuCode":"tunnel","menuName":"管廊","permissionCode":"tunnel:add","permissionName":"添加","crtTime":1546358400000}],"crtTime":1546358400000}} 
	 * @author shaosen
	 * @Date 2019年1月7日
	 */
	@RequestMapping(value="roles/{id}",method=RequestMethod.GET)
	public JSONObject getRoleInfo(@PathVariable Integer id) {
		Role role = roleService.getRole(id);
		RoleDto roleDto = new RoleDto();
		if(!StringTools.isNullOrEmpty(role)) {
			roleDto.setId(role.getId());
			roleDto.setRoleName(role.getRoleName());
			roleDto.setCrtTime(role.getCrtTime());
			roleDto.setPermissionList(permissionService.getPermissionsByRole(role.getId()));
		}
		return CommonUtil.success(roleDto);
	}
	
	
	/**给角色授予权限（admin角色不需要赋予权限，因为具有所有权限）
	 * @param roleId 角色id
	 * @param permissionIds 权限id数组
	 * @return   
	 * @author shaosen
	 * @Date 2018年12月19日
	 */
	@RequestMapping(value="roles/associate",method=RequestMethod.POST)
	public JSONObject associatePermission2Role(@RequestBody JSONObject reqJson) {
		CommonUtil.hasAllRequired(reqJson, "roleId,permissionIds");
		Integer roleId = reqJson.getInteger("roleId");
		List<Integer> permissionIds = (List<Integer>) reqJson.get("permissionIds");
		roleService.addRolePermissions(roleId,permissionIds);
		return CommonUtil.success();
	}
	
	
	/**获取所有角色，给账号分配角色时使用 
	 * @return   {"msg":"请求成功","code":"200","data":[{"id":1,"roleName":"admin","crtTime":1546358400000},{"id":3,"roleName":"test","crtTime":1546358400000},{"id":2,"roleName":"manager","crtTime":1546358400000}]}
	 * @author shaosen
	 * @Date 2018年12月26日
	 */
	@RequestMapping(value="roles/list",method=RequestMethod.GET)
	public JSONObject getAllRoles() {
		List<Role> roleList = roleService.getAllRoles();
		return CommonUtil.success(roleList);
	}
	
	
	/**给账号分配角色 
	 * @param userId 账号id
	 * @param roleIds 角色id数组
	 * @return   
	 * @author shaosen
	 * @Date 2018年12月26日
	 */
	@RequestMapping(value="users/associate",method=RequestMethod.POST)
	public JSONObject associateRole2User(@RequestBody JSONObject reqJson) {
		CommonUtil.hasAllRequired(reqJson, "userId,roleIds");
		Integer userId = reqJson.getInteger("userId");
		List<Integer> roleIds = (List<Integer>) reqJson.get("roleIds");
		roleService.addUserRole(userId,roleIds);
		return CommonUtil.success();
	}

	
	
	/**通过id获取账号信息(并获取对应的角色信息)
	 * @param id 账号id
	 * @return {"msg":"请求成功","code":"200","data":{"roles":[{"id":1,"roleName":"admin","crtTime":1546358400000}],"name":"admin","id":1000}}
	 * @author shaosen
	 * @date 2018年6月20日
	 */
	@RequestMapping(value = "users/{id}", method = RequestMethod.GET)
	public JSONObject getById(@PathVariable("id") Integer id) {

		JSONObject userDet = userService.getUserDet(id);
		return CommonUtil.success(userDet);
	}

	
}
