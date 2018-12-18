package com.bandweaver.tunnel.common.biz.itf.common;

import java.util.List;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.dto.UserDTO;
import com.bandweaver.tunnel.common.biz.pojo.common.User;

public interface UserService {

	void updateByPrimaryKeySelective(User user);

	User selectByPrimaryKey(Integer id);

	UserDTO getUser(Integer id);

	List<JSONObject> getUsersByRole(String roleDesc);

	JSONObject getPermissions(String name);

	User getByUserName(String name);

	void deleteBatch(List<Integer> list);

}
