package com.bandweaver.tunnel.service.common;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.bandweaver.tunnel.common.biz.constant.ShiroResourceType;
import com.bandweaver.tunnel.common.biz.pojo.common.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.dto.UserDTO;
import com.bandweaver.tunnel.common.biz.itf.common.RoleService;
import com.bandweaver.tunnel.common.biz.itf.common.UserService;
import com.bandweaver.tunnel.common.biz.pojo.common.User;
import com.bandweaver.tunnel.common.platform.util.StringTools;
import com.bandweaver.tunnel.dao.common.PermissionMapper;
import com.bandweaver.tunnel.dao.common.UserMapper;
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private PermissionMapper permissionMapper;
	@Autowired
	private RoleService roleService;
	

	@Override
	public void updateByPrimaryKeySelective(User user) {
		userMapper.updateByPrimaryKeySelective(user);
	}

	@Override
	public User selectByPrimaryKey(Integer id) {
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public UserDTO getUser(Integer id) {
		return userMapper.getUserDtoById(id);
	}

	@Override
	public List<JSONObject> getUsersByRole(String roleName) {
		return userMapper.getUsersByRole(roleName);
	}


	@Override
	public JSONObject getPermissions(String name) {
		JSONObject permissions = userMapper.getPermissions(name);
		String roleName = permissions.getString("roleName");
		if("admin".equals(roleName)) {
			// get allMenu
			Set<String> menuList = permissionMapper.getAllMenu();
			// getAllPermissions
			Set<String> permissionList =permissionMapper.getAllPermission();
			permissions.put("menuList", menuList);
			permissions.put("permissionList", permissionList);
		}
		permissions.put("name", name);
		
		return permissions;
	}

	@Override
	public Set<String> getUserPermissions(String name) {

		Set<String> userPermissions = new HashSet<>();
		// 首先查询该用户是否具有admin角色，如果有的话，则具有所有权限
		List<Role> roleList = roleService.getRoleByUseName(name);
		if (roleList != null && roleList.size() > 0) {
			List<String> collect = roleList.stream().map(Role::getRoleName).collect(Collectors.toList());
			if (collect.contains("admin")) {
				for (ShiroResourceType shiroResourceType : ShiroResourceType.values()) {
					userPermissions.add(shiroResourceType.getValue() + ":*:*");
				}
			} else {
				// 查询数据库
				userPermissions = userMapper.getUserPermissions(name);
			}
		}
		return userPermissions;
	}

	@Override
	public User getByUserName(String name) {
		return userMapper.getByName(name);
	}

	@Override
	public void deleteBatch(List<Integer> list) {
		userMapper.deleteBatch(list);
		
	}

	@Override
	public JSONObject getUserDet(Integer id) {
		UserDTO user = getUser(id);
		JSONObject returnData = new JSONObject();
		if(!StringTools.isNullOrEmpty(user)) {
			returnData.put("id", user.getId());
			returnData.put("name", user.getName());
			returnData.put("roles", roleService.getRolesByUser(user.getId()));
		}
		return returnData;
	}


}
