package com.bandweaver.tunnel.common.biz.itf;

import java.util.List;

import com.bandweaver.tunnel.common.biz.pojo.Permission;
import com.bandweaver.tunnel.common.biz.pojo.Role;

public interface RoleService {

	/**
	 * @Description: 查询所有角色列表
	 * @param @return   
	 * @return List<Role>  
	 * @throws
	 * @author shaosen
	 * @date 2018年5月22日
	 */
	List<Role> getAllRoles();

	/**
	 * @Description: 添加角色
	 * @param @param role   
	 * @return void  
	 * @throws
	 * @author shaosen
	 * @date 2018年5月22日
	 */
	void add(Role role);

	/**
	 * @Description: 查询该角色对应的权限列表
	 * @param @param id
	 * @param @return   
	 * @return List<Permission>  
	 * @throws
	 * @author shaosen
	 * @date 2018年5月22日
	 */
	List<Permission> getPermissionsByRoleId(int id);

}
