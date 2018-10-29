package com.bandweaver.tunnel.service.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandweaver.tunnel.common.biz.itf.RoleService;
import com.bandweaver.tunnel.common.biz.pojo.Permission;
import com.bandweaver.tunnel.common.biz.pojo.Role;
import com.bandweaver.tunnel.dao.common.PermissionMapper;
import com.bandweaver.tunnel.dao.common.RoleMapper;

@Service
public class RoleServiceImpl implements RoleService{

	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private PermissionMapper permissionMapper;
	
	
	@Override
	public List<Role> getAllRoles() {
		return roleMapper.getAllRoles();
	}


	@Override
	public void add(Role role) {
		roleMapper.insert(role);
	}


	@Override
	public List<Permission> getPermissionsByRoleId(int id) {
		return permissionMapper.getPermissionsByRoleId(id);
	}

}
