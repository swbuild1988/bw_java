package com.bandweaver.tunnel.service.common;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.itf.Loginservice;
import com.bandweaver.tunnel.common.biz.itf.common.UserService;
@Service
public class LoginserviceImpl implements Loginservice {
	
	@Autowired
	private UserService userService;

	@Override
	public JSONObject getUserPermission(String name) {
		return userService.getPermissions(name);
	}

}
