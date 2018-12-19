package com.bandweaver.tunnel.common.biz.itf.common;

import java.util.List;

import com.bandweaver.tunnel.common.biz.pojo.common.Role;

public interface RoleService {

	void addRole(Role role);

	void addRolePermissions(Integer roleId, List<Integer> permissionIds);

}
