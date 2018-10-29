package com.bandweaver.tunnel.service.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandweaver.tunnel.common.biz.itf.PermissionService;
import com.bandweaver.tunnel.common.biz.pojo.Permission;
import com.bandweaver.tunnel.dao.common.PermissionMapper;
@Service
public class PermissionServiceImpl implements PermissionService{

	
	@Autowired
	private PermissionMapper permissionMapper;

	@Override
	public void add(Permission permission) {
		permissionMapper.insert(permission);
	}
	
	
}
