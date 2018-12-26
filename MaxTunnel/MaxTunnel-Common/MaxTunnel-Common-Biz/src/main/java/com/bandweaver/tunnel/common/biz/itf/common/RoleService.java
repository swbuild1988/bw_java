package com.bandweaver.tunnel.common.biz.itf.common;

import java.util.List;

import com.bandweaver.tunnel.common.biz.pojo.common.Role;

public interface RoleService {

	void addRole(Role role);

	void addRolePermissions(Integer roleId, List<Integer> permissionIds);

	List<Role> getAllRoles();

	void addUserRole(Integer userId, List<Integer> roleIds);

	void deleteRoleBatch(List<Integer> list);

	void updateRole(Role role);

}
