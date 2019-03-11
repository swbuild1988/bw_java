package com.bandweaver.tunnel.dao.common;

import com.bandweaver.tunnel.common.biz.pojo.common.RolePermission;

import java.util.List;

public interface RolePermissionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RolePermission record);

    int insertSelective(RolePermission record);

    RolePermission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RolePermission record);

    int updateByPrimaryKey(RolePermission record);

	void deleteByRoleId(Integer roleId);

    void deleteByPIds(List<Integer> list);

    void deleteByRIds(List<Integer> list);
}