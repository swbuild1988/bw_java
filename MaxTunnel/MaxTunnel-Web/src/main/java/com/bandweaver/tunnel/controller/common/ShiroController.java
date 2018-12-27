package com.bandweaver.tunnel.controller.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.itf.common.PermissionService;
import com.bandweaver.tunnel.common.biz.itf.common.RoleService;
import com.bandweaver.tunnel.common.biz.pojo.common.Permission;
import com.bandweaver.tunnel.common.biz.pojo.common.Role;
import com.bandweaver.tunnel.common.biz.vo.common.PermissionVo;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
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
	
	/**添加权限
	 * @param menuCode user
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
	 * @param ids
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
		CommonUtil.hasAllRequired(reqJson, "id,name");
		Role role = CommonUtil.parse2Obj(reqJson, Role.class);
		roleService.updateRole(role);
		return CommonUtil.success();
	}
	
	//角色分页查询，并显示对应的权限列表
	
	
	
	/**给角色授予权限（admin角色不需要赋予权限，因为具有所有权限）
	 * @param reqJson
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
	
	
	/**获取所有角色，给用户分配角色时使用 
	 * @return   
	 * @author shaosen
	 * @Date 2018年12月26日
	 */
	@RequestMapping(value="roles/list",method=RequestMethod.GET)
	public JSONObject getAllRoles() {
		List<Role> roleList = roleService.getAllRoles();
		return CommonUtil.success(roleList);
	}
	
	
	/**给用户分配角色 
	 * @param userId
	 * @param roleIds
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
	
	
}
