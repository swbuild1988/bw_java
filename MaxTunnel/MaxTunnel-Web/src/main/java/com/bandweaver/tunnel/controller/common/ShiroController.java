package com.bandweaver.tunnel.controller.common;

import java.util.*;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.util.TypeUtils;
import com.bandweaver.tunnel.common.biz.constant.ShiroOperateType;
import com.bandweaver.tunnel.common.biz.constant.ShiroResourceType;
import com.bandweaver.tunnel.common.biz.dto.TunnelSimpleDto;
import com.bandweaver.tunnel.common.biz.itf.TunnelService;
import com.bandweaver.tunnel.common.biz.pojo.common.User;
import com.bandweaver.tunnel.common.platform.constant.Constants;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.ContextUtil;
import com.bandweaver.tunnel.common.platform.util.RedisUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.dto.common.RoleDto;
import com.bandweaver.tunnel.common.biz.itf.common.PermissionService;
import com.bandweaver.tunnel.common.biz.itf.common.RoleService;
import com.bandweaver.tunnel.common.biz.itf.common.UserService;
import com.bandweaver.tunnel.common.biz.pojo.common.Permission;
import com.bandweaver.tunnel.common.biz.pojo.common.Role;
import com.bandweaver.tunnel.common.biz.vo.common.PermissionVo;
import com.bandweaver.tunnel.common.biz.vo.common.RoleVo;
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
	@Autowired
	private TunnelService tunnelService;
	@Autowired
	private RedisUtil redisUtil;


	private static final String SORT_KEY_NAME = "sort";
	private static final String KEY_SHIRO_RESOURCE = "shiroResourceType";
	private static final String KEY_SHIRO_OPERATE = "shiroOperateType";
	private static final String KEY_SHIRO_ID = "shiroResourceId";

	/**
	 *添加权限
	 * @param reqJson
	 * shiroResourceType 操作的资源领域，管廊，区域，用户等,格式：tunnel
	 * shiroOperateType 对资源的操作类型，增删改查等,格式 add,list,update,delete或者*
	 * shiroResourceId 操作资源id，默认是*代表某种资源的所有数据,格式 1,2,3或者*
	 * 权限表达式由以上三个部分拼接而成，拼接之后的格式为：tunnel:add,list,delete:1,2
	 * @return
	 */
	@RequiresPermissions("permission:add")
	@RequestMapping(value = "permissions",method = RequestMethod.POST)
	public JSONObject addPermission(@RequestBody JSONObject reqJson) {
		CommonUtil.hasAllRequired(reqJson, "shiroResourceType,shiroOperateType,shiroResourceId");

		String shiroResourceType = reqJson.getString("shiroResourceType");
		// 格式：add,delete,list,update 或 *
		String shiroOperateType = reqJson.getString("shiroOperateType");
		// 格式：1,2,3 或 *
		String shiroResourceId = reqJson.getString("shiroResourceId");
		// 只针对管廊做资源访问限制
		if (!Objects.equals(shiroResourceType,ShiroResourceType.TUNNEL.getValue())) {
			shiroResourceId = "*";
		}
		// 格式：tunnel:add,delete:1,2
		String permissionCode = shiroResourceType + ":" + shiroOperateType + ":" + shiroResourceId;
		reqJson.put("permissionCode", permissionCode);

		Permission permission = CommonUtil.parse2Obj(reqJson, Permission.class);
		permissionService.addPermission(permission);
		// 如果添加新的权限，则删除redis缓冲中的数据
		deleteRedisCache();
		return CommonUtil.success();
	}


	/**批量删除权限
	 * @param ids
	 * @return
	 * @author shaosen
	 * @Date 2018年12月18日
	 */
	@RequiresPermissions("permission:delete")
	@RequestMapping(value="permissions/batch/{ids}",method=RequestMethod.DELETE)
	public JSONObject deletePermisssion(@PathVariable String ids) {
		List<Integer> idList = CommonUtil.convertStringToList(ids);
		permissionService.deleteBatch(idList);
		deleteRedisCache();
		return CommonUtil.success();
	}


	/**修改权限
	 * @param reqJson
	 * @return
	 * @author shaosen
	 * @Date 2018年12月18日
	 */
	@RequiresPermissions("permission:update")
	@RequestMapping(value="permissions",method=RequestMethod.PUT)
	public JSONObject updatePermission(@RequestBody JSONObject reqJson) {
		CommonUtil.hasAllRequired(reqJson, "id,shiroResourceType,shiroOperateType,shiroResourceId");

		String shiroResourceType = reqJson.getString("shiroResourceType");
		// 格式：add,delete,list,update 或 *
		String shiroOperateType = reqJson.getString("shiroOperateType");
		// 格式：1,2,3 或 *
		String shiroResourceId = reqJson.getString("shiroResourceId");
		// 只针对管廊做资源访问限制
		if (!Objects.equals(shiroResourceType,ShiroResourceType.TUNNEL.getValue())) {
			shiroResourceId = "*";
		}
		// 格式：tunnel:add,delete:1,2
		String permissionCode = shiroResourceType + ":" + shiroOperateType + ":" + shiroResourceId;
		reqJson.put("permissionCode", permissionCode);

		Permission permission = CommonUtil.parse2Obj(reqJson, Permission.class);
		permissionService.updatePermission(permission);
		deleteRedisCache();
		return CommonUtil.success();
	}


	/**获取权限信息
	 * @param id
	 * @return
	 * @author shaosen
	 * @Date 2018年12月18日
	 */
	@RequestMapping(value="permissions/{id}",method=RequestMethod.GET)
	public JSONObject getPermission(@PathVariable Integer id) {
		Permission permission = permissionService.getPermission(id);
		if (permission == null) { return CommonUtil.success(); }

		JSONObject json = convertPermissionCodeStrToJSONObject(permission);
		permission.setEtc(json);
		return CommonUtil.success(permission);
	}


	private void deleteRedisCache() {
		try {
			String loginUser = ContextUtil.getLoginUser().getName();
			String REDIS_KEY = loginUser + "." + Constants.SHIRO_PERMISSION_KEY;
			if (redisUtil.hasKey(REDIS_KEY)) {
                redisUtil.delete(REDIS_KEY);
                LogUtil.info("删除用户" + loginUser + "的redis缓存成功！");
            }
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.error(e.getMessage());
		}
	}

	private JSONObject convertPermissionCodeStrToJSONObject(Permission permission) {
		String permissionCode = permission.getPermissionCode();
		// 把tunnel字符串转变为数字方便前端回显
		int shiroResourceType = ShiroResourceType.getEnum(permissionCode.substring(0, permissionCode.indexOf(":")))
				.getKey();

		// 把add,delete,list格式转化成[1,2,3]返回给前端，方便回显
		List<Object> optKeyList = new ArrayList<>(10);
		String optStr = permissionCode.substring(permissionCode.indexOf(":") + 1, permissionCode.lastIndexOf(":"));
		if (Objects.equals("*", optStr)) {
			for (ShiroOperateType type : ShiroOperateType.values()) {
				optKeyList.add(type.getKey());
			}
		} else {
			String[] arr = optStr.split(",");
			for (String value : arr) {
				ShiroOperateType anEnum = ShiroOperateType.getEnum(value);
				if (anEnum != null) {
					optKeyList.add(anEnum.getKey());
				}
			}
		}

		List<Integer> idList = new ArrayList<>(10);
		// 获取管廊id集合，方便前端回显示
		if (ShiroResourceType.getEnum(shiroResourceType) == ShiroResourceType.TUNNEL) {
            String idStr = permissionCode.substring(permissionCode.lastIndexOf(":") + 1, permissionCode.length());
            if (Objects.equals("*", idStr)) {
                // get all tunnel list
                List<TunnelSimpleDto> ls = tunnelService.getListWithoutPermission();
                if (ls != null) {
                    idList = ls.stream().map(TunnelSimpleDto::getId).collect(Collectors.toList());
                }
            } else {
                String[] arr = idStr.split(",");
                for (String tunnelId : arr) {
                    TunnelSimpleDto t = tunnelService.getSimpleDtoByIdWithoutPermissioin(TypeUtils.castToInt(tunnelId));
                    if (t != null) {
                        idList.add(t.getId());
                    }
                }
            }
        }

		JSONObject json = new JSONObject();
		json.put(KEY_SHIRO_RESOURCE, shiroResourceType);
		json.put(KEY_SHIRO_OPERATE, optKeyList);
		json.put(KEY_SHIRO_ID, idList);
		return json;
	}


	/**获取所有权限，给角色分配权限时使用
	 * @return
	 * @author shaosen
	 * @Date 2018年12月19日
	 */
	@RequiresPermissions("permission:list")
	@RequestMapping(value="permissions/list",method=RequestMethod.GET)
	public JSONObject getAllPermissons() {
		List<JSONObject> ls = new ArrayList<>();
		List<Permission> list = permissionService.getPermissionIdAndPermissionCode();
		if (list != null && list.size() > 0) {
			for (Permission permission : list) {

				JSONObject js = convertPermissionCodeStrToJSONObject(permission);
				JSONObject descJson = getDescription(js);

				JSONObject json = new JSONObject();
				json.put("id", permission.getId());
				json.put("etc", descJson);
				json.put(SORT_KEY_NAME, js.getIntValue(KEY_SHIRO_RESOURCE));
				ls.add(json);
			}
		}

		// 为了使相同资源的权限能紧挨在一起，对list进行重排序
		Collections.sort(ls, new Comparator<JSONObject>() {
			@Override
			public int compare(JSONObject o1, JSONObject o2) {
				int val1 = o1.getIntValue(SORT_KEY_NAME);
				int val2 = o2.getIntValue(SORT_KEY_NAME);
				return (val1 - val2);
			}
		});
		return CommonUtil.success(ls);

	}

	private JSONObject getDescription(JSONObject js) {
		String resourceName = ShiroResourceType.getEnum(js.getIntValue(KEY_SHIRO_RESOURCE)).getName();

		List<String> nameList = new ArrayList<>();
		List<Integer> optIdList = js.getObject(KEY_SHIRO_OPERATE, List.class);
		for (Integer optId : optIdList) {
			String optName = ShiroOperateType.getEnum(optId).getName();
			nameList.add(optName);
		}

		List<String> tunnelNameList = new ArrayList<>();
		List<Integer> resourceIdList = js.getObject(KEY_SHIRO_ID, List.class);
		if (resourceIdList != null && resourceIdList.size() > 0) {
			for (Integer tunnelId : resourceIdList) {
				String tunnelName = tunnelService.getSimpleDtoByIdWithoutPermissioin(tunnelId).getName();
				tunnelNameList.add(tunnelName);
			}
		}
		JSONObject result = new JSONObject();
		result.put("c1", resourceName);
		result.put("c2", nameList);
		result.put("c3", tunnelNameList);

		return result;
	}

	/**权限分页查询
	 * @return
	 * @author shaosen
	 * @Date 2018年12月18日
	 */
	@RequiresPermissions("permission:list")
	@RequestMapping(value="permissions/datagrid",method=RequestMethod.POST)
	public JSONObject dataGrid(@RequestBody JSONObject reqJson) {
		CommonUtil.hasAllRequired(reqJson, "pageNum,pageSize");
		PermissionVo vo = CommonUtil.parse2Obj(reqJson, PermissionVo.class);
		PageInfo<Permission> pageInfo = permissionService.dataGrid(vo);
		List<Permission> list = pageInfo.getList();
		for (Permission permission : list) {
			final JSONObject json = convertPermissionCodeStrToJSONObject(permission);
			permission.setEtc(getDescription(json));
		}

		Collections.sort(list, new Comparator<Permission>() {
			@Override
			public int compare(Permission o1, Permission o2) {
				String resource1 = o1.getPermissionCode().substring(0, o1.getPermissionCode().indexOf(":"));
				String resource2 = o2.getPermissionCode().substring(0, o2.getPermissionCode().indexOf(":"));
				return resource1.compareTo(resource2);
			}
		});

		return CommonUtil.success(pageInfo);
	}




	/**添加角色
	 * @param reqJson roleName:角色名称；routList:路由列表
	 * @return
	 * @author shaosen
	 * @Date 2018年12月19日
	 */
	@RequiresPermissions("role:add")
	@RequestMapping(value="roles",method=RequestMethod.POST)
	public JSONObject addRole(@RequestBody JSONObject reqJson) {
		CommonUtil.hasAllRequired(reqJson, "roleName,routList");
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
	@RequiresPermissions("role:delete")
	@RequestMapping(value="roles/batch/{ids}",method=RequestMethod.DELETE)
	public JSONObject deleteRoleBatch(@PathVariable String ids) {
		List<Integer> list = CommonUtil.convertStringToList(ids);
		roleService.deleteRoleBatch(list);
		deleteRedisCache();
		return CommonUtil.success();
	}

	/**修改角色
	 * @param reqJson
	 * @return
	 * @author shaosen
	 * @Date 2018年12月26日
	 */
	@RequiresPermissions("role:update")
	@RequestMapping(value="roles",method=RequestMethod.PUT)
	public JSONObject updateRole(@RequestBody JSONObject reqJson) {
		CommonUtil.hasAllRequired(reqJson, "id");
		Role role = CommonUtil.parse2Obj(reqJson, Role.class);
		roleService.updateRole(role);
		return CommonUtil.success();
	}


	/**角色分页查询，并显示对应的权限列表
	 * 说明：当角色为admin时，显示对应的权限信息为字符串“具有所有权限”
	 * @param reqJson
	 * @return
	 * @author shaosen
	 * @Date 2019年1月2日
	 */
	@RequiresPermissions("role:list")
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
				List<JSONObject> ls = new ArrayList<>();
				for (Permission permission : pList) {
					JSONObject description = getDescription(convertPermissionCodeStrToJSONObject(permission));
					ls.add(description);
				}
				role.setPermissionList(ls);
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
			roleDto.setRoutList(role.getRoutList());
			List<Permission> pList = permissionService.getPermissionsByRole(role.getId());
			List<Integer> pidList = pList.stream().map(Permission::getId).collect(Collectors.toList());
			List<JSONObject> ls = new ArrayList<>();
			for (Integer integer : pidList) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("id", integer);
				ls.add(jsonObject);
			}
			roleDto.setPermissionList(ls);
		}
		return CommonUtil.success(roleDto);
	}


	/**给角色授予权限（admin角色不需要赋予权限，因为具有所有权限）
	 * @param  reqJson roleId --角色id ;permissionIds--权限id数组
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
		deleteRedisCache();
		return CommonUtil.success();
	}


	/**获取所有角色，给账号分配角色时使用
	 * @return   {"msg":"请求成功","code":"200","data":[{"id":1,"roleName":"admin","crtTime":1546358400000},{"id":3,"roleName":"test","crtTime":1546358400000},{"id":2,"roleName":"manager","crtTime":1546358400000}]}
	 * @author shaosen
	 * @Date 2018年12月26日
	 */
	@RequiresPermissions("role:list")
	@RequestMapping(value="roles/list",method=RequestMethod.GET)
	public JSONObject getAllRoles() {
		List<Role> roleList = roleService.getAllRoles();
		return CommonUtil.success(roleList);
	}


	/**给账号分配角色
	 * @param reqJson userId 账号id;roleIds 角色id数组
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
		deleteRedisCache();
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
