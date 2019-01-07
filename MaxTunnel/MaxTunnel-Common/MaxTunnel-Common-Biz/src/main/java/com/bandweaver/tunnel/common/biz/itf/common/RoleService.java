package com.bandweaver.tunnel.common.biz.itf.common;

import java.util.List;

import com.bandweaver.tunnel.common.biz.dto.common.RoleDto;
import com.bandweaver.tunnel.common.biz.pojo.common.Role;
import com.bandweaver.tunnel.common.biz.vo.common.RoleVo;
import com.github.pagehelper.PageInfo;

public interface RoleService {

	void addRole(Role role);

	void addRolePermissions(Integer roleId, List<Integer> permissionIds);

	List<Role> getAllRoles();

	void addUserRole(Integer userId, List<Integer> roleIds);

	void deleteRoleBatch(List<Integer> list);

	void updateRole(Role role);

	PageInfo<RoleDto> dataGrid(RoleVo roleVo);

	Role getRole(Integer id);

	List<Role> getRolesByUser(Integer id);

}
