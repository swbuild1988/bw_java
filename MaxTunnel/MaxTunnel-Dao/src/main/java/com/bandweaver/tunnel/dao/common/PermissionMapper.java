package com.bandweaver.tunnel.dao.common;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bandweaver.tunnel.common.biz.pojo.Permission;

public interface PermissionMapper {
    int deleteByPrimaryKey(Short id);

    int insert(Permission record);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(Short id);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);

	/**
	 * @Description: 查询角色对应的权限列表
	 * @param @param id
	 * @param @return   
	 * @return List<Permission>  
	 * @throws
	 * @author shaosen
	 * @date 2018年5月22日
	 */
	List<Permission> getPermissionsByRoleId(@Param("id")int id);
}