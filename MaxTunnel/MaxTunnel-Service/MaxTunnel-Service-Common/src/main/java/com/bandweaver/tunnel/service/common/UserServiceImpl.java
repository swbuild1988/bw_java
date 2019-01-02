package com.bandweaver.tunnel.service.common;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.dto.UserDTO;
import com.bandweaver.tunnel.common.biz.itf.common.UserService;
import com.bandweaver.tunnel.common.biz.pojo.common.User;
import com.bandweaver.tunnel.dao.common.PermissionMapper;
import com.bandweaver.tunnel.dao.common.UserMapper;
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private PermissionMapper permissionMapper;
	

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
			//get allMenu
			Set<String> menuList = permissionMapper.getAllMenu();
			//getAllPermissions
			Set<String> permissionList =permissionMapper.getAllPermission();
			permissions.put("menuList", menuList);
			permissions.put("permissionList", permissionList);
		}
		permissions.put("name", name);
		
		return permissions;
	}

	@Override
	public User getByUserName(String name) {
		return userMapper.getByName(name);
	}

	@Override
	public void deleteBatch(List<Integer> list) {
		userMapper.deleteBatch(list);
		
	}

}
