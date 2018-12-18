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
import com.bandweaver.tunnel.common.biz.itf.common.PermissionService;
import com.bandweaver.tunnel.common.biz.pojo.common.Permission;
import com.bandweaver.tunnel.common.biz.vo.common.PermissionVo;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.github.pagehelper.PageInfo;

/**权限管理
 * @author shaosen
 * @date 2018年12月17日
 */
@Controller
@ResponseBody
public class PermissionController {
	
	
	@Autowired
	private PermissionService permissionService;
	
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
	
	
	/**分页查询 
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
	
	
	
}
