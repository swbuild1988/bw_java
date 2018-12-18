package com.bandweaver.tunnel.dao.common;

import java.util.List;
import java.util.Set;

import com.bandweaver.tunnel.common.biz.pojo.common.Permission;
import com.bandweaver.tunnel.common.biz.vo.common.PermissionVo;

public interface PermissionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Permission record);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);

	Set<String> getAllMenu();

	Set<String> getAllPermission();

	void deleteBatch(List<Integer> list);

	List<Permission> getByCondition(PermissionVo vo);
}