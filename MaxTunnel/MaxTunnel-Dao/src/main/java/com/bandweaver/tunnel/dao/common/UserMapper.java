package com.bandweaver.tunnel.dao.common;

import java.util.List;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.dto.UserDTO;
import com.bandweaver.tunnel.common.biz.pojo.common.Permission;
import com.bandweaver.tunnel.common.biz.pojo.common.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

	UserDTO getUserDtoById(Integer id);

	List<JSONObject> getUsersByRole(String roleName);

	User getByName(String name);

	void deleteBatch(List<Integer> list);

	JSONObject getPermissions(String name);

    Set<String> getUserPermissions(String name);
}