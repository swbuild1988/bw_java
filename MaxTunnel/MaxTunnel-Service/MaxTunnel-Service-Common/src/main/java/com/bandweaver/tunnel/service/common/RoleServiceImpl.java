package com.bandweaver.tunnel.service.common;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bandweaver.tunnel.common.biz.dto.common.RoleDto;
import com.bandweaver.tunnel.common.biz.itf.common.RoleService;
import com.bandweaver.tunnel.common.biz.pojo.common.Role;
import com.bandweaver.tunnel.common.biz.pojo.common.RolePermission;
import com.bandweaver.tunnel.common.biz.pojo.common.UserRole;
import com.bandweaver.tunnel.common.biz.vo.common.RoleVo;
import com.bandweaver.tunnel.common.platform.util.DateUtil;
import com.bandweaver.tunnel.dao.common.RoleMapper;
import com.bandweaver.tunnel.dao.common.RolePermissionMapper;
import com.bandweaver.tunnel.dao.common.UserRoleMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private RolePermissionMapper rolePermissionMapper;
	@Autowired
	private UserRoleMapper userRoleMapper;
	
	@Override
	public void addRole(Role role) {
		role.setCrtTime(DateUtil.getCurrentDate());
		roleMapper.insertSelective(role);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addRolePermissions(Integer roleId, List<Integer> permissionIds) {
		//删除旧权限
		rolePermissionMapper.deleteByRoleId(roleId);
		//赋予新权限
		for (Integer permissionId : permissionIds) {
			RolePermission rp = new RolePermission();
			rp.setrId(roleId);
			rp.setpId(permissionId);
			rp.setCrtTime(DateUtil.getCurrentDate());
			rolePermissionMapper.insertSelective(rp);
		}
	}

	@Override
	public List<Role> getAllRoles() {
		List<Role> list = roleMapper.getAllRoles();
		return list == null ? Collections.emptyList() : list;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addUserRole(Integer userId, List<Integer> roleIds) {
		//删除旧角色
		userRoleMapper.deleteByUserId(userId);
		//赋予新角色
		roleIds.forEach(r -> {
			UserRole ur = new UserRole();
			ur.setuId(userId);
			ur.setrId(r.intValue());
			ur.setCrtTime(DateUtil.getCurrentDate());
			userRoleMapper.insertSelective(ur);
		});
		
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteRoleBatch(List<Integer> list) {
		roleMapper.deleteRoleBatch(list);
		rolePermissionMapper.deleteByRIds(list);
		userRoleMapper.deleteByRIds(list);

	}

	@Override
	public void updateRole(Role role) {
		roleMapper.updateByPrimaryKeySelective(role);
	}

	@Override
	public PageInfo<RoleDto> dataGrid(RoleVo vo) {
		PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
		List<RoleDto> list = roleMapper.getByCondition(vo);
		return new PageInfo<>(list);
	}

	@Override
	public Role getRole(Integer id) {
		return roleMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Role> getRolesByUser(Integer id) {
		List<Role> list = roleMapper.getRolesByUser(id);
		return list == null ? Collections.emptyList() : list;
	}

	@Override
	public List<Role> getRoleByUseName(String username) {
		return roleMapper.getRoleByUseName(username);
	}

}
