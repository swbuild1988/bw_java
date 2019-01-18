package com.bandweaver.tunnel.dao.common;

import java.util.List;

import com.bandweaver.tunnel.common.biz.pojo.common.Role;
import com.bandweaver.tunnel.common.biz.pojo.common.UserRole;

public interface UserRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserRole record);

    int insertSelective(UserRole record);

    UserRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserRole record);

    int updateByPrimaryKey(UserRole record);

	void deleteByUserId(Integer userId);

}