package com.bandweaver.tunnel.common.biz.itf;

import java.util.List;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.pojo.common.Role;

public interface Loginservice {

	JSONObject getUserPermission(String name);

    JSONObject getPermissionListByUserName(String username);

    List<Role> getUserRoles(String username);
}
