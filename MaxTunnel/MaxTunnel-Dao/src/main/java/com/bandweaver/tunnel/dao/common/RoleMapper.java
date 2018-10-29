package com.bandweaver.tunnel.dao.common;

import java.util.List;

import com.bandweaver.tunnel.common.biz.pojo.Role;

public interface RoleMapper {
    int deleteByPrimaryKey(Short id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Short id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

	/**
	 * @Description: 查询所有角色列表
	 * @param @return   
	 * @return List<Role>  
	 * @throws
	 * @author shaosen
	 * @date 2018年5月22日
	 */
	List<Role> getAllRoles();

}