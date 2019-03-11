package com.bandweaver.tunnel.service.common;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.bandweaver.tunnel.dao.common.RolePermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.ls.LSInput;

import com.bandweaver.tunnel.common.biz.itf.common.PermissionService;
import com.bandweaver.tunnel.common.biz.pojo.common.Permission;
import com.bandweaver.tunnel.common.biz.vo.common.PermissionVo;
import com.bandweaver.tunnel.common.platform.util.DateUtil;
import com.bandweaver.tunnel.dao.common.PermissionMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
@Service
public class PermissionServiceImpl implements PermissionService {
	
	@Autowired
	private PermissionMapper permissionMapper;
	@Autowired
	private RolePermissionMapper rolePermissionMapper;

	@Override
	public void addPermission(Permission permission) {
		permission.setCrtTime(DateUtil.getCurrentDate());
		permissionMapper.insertSelective(permission);
	}

	@Override
	@Transactional
	public void deleteBatch(List<Integer> list) {
		if(list.isEmpty())
			return;
		permissionMapper.deleteBatch(list);
		//同时删除中间表数据
		rolePermissionMapper.deleteByPIds(list);
	}

	@Override
	public void updatePermission(Permission permission) {
		permissionMapper.updateByPrimaryKeySelective(permission);
	}

	@Override
	public Permission getPermission(Integer id) {
		return permissionMapper.selectByPrimaryKey(id);
	}

	@Override
	public PageInfo<Permission> dataGrid(PermissionVo vo) {
		PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
		List<Permission> list = getByCondition(vo);
		return new PageInfo<>(list);
	}

	public List<Permission> getByCondition(PermissionVo vo) {
		List<Permission> list = permissionMapper.getByCondition(vo);
		return list == null ? Collections.emptyList() : list;
	}

	@Override
	public Set<String> getAllMenuName() {
		return permissionMapper.getAllMenuName();
	}

	@Override
	public List<Permission> getPermissionsByMenu(String menuName) {
		return permissionMapper.getPermissionsByMenu(menuName);
	}

	@Override
	public List<Permission> getPermissionsByRole(Integer rid) {
		List<Permission> list = permissionMapper.getPermissionsByRole(rid);
		return list == null ? Collections.emptyList() : list;
	}

}
