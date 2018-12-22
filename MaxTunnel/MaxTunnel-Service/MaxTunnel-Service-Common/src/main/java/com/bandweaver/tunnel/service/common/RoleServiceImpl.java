package com.bandweaver.tunnel.service.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandweaver.tunnel.common.biz.itf.common.RoleService;
import com.bandweaver.tunnel.common.biz.pojo.common.Role;
import com.bandweaver.tunnel.common.biz.pojo.common.RolePermission;
import com.bandweaver.tunnel.common.platform.util.DateUtil;
import com.bandweaver.tunnel.dao.common.RoleMapper;
import com.bandweaver.tunnel.dao.common.RolePermissionMapper;
@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private RolePermissionMapper rolePermissionMapper;
	
	@Override
	public void addRole(Role role) {
		role.setCrtTime(DateUtil.getCurrentDate());
		roleMapper.insertSelective(role);
	}

	@Override
	public void addRolePermissions(Integer roleId, List<Integer> permissionIds) {
		for (Integer permissionId : permissionIds) {
			RolePermission rp = new RolePermission();
			rp.setrId(roleId);
			rp.setpId(permissionId);
			rp.setCrtTime(DateUtil.getCurrentDate());
			rolePermissionMapper.insertSelective(rp);
		}
	}

}
