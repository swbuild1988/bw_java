package com.bandweaver.tunnel.service.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandweaver.tunnel.common.biz.itf.common.RoleService;
import com.bandweaver.tunnel.common.biz.pojo.common.Role;
import com.bandweaver.tunnel.common.platform.util.DateUtil;
import com.bandweaver.tunnel.dao.common.RoleMapper;
@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleMapper roleMapper;
	
	@Override
	public void addRole(Role role) {
		role.setCrtTime(DateUtil.getCurrentDate());
		roleMapper.insertSelective(role);
	}

	@Override
	public void addRolePermissions(Integer roleId, List<Integer> permissionIds) {
		roleMapper.addRolePermissions(roleId,permissionIds);
	}

}
