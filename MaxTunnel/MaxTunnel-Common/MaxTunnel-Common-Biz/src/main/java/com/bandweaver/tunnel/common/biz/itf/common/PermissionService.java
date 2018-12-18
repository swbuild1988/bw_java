package com.bandweaver.tunnel.common.biz.itf.common;

import java.util.List;

import com.bandweaver.tunnel.common.biz.pojo.common.Permission;
import com.bandweaver.tunnel.common.biz.vo.common.PermissionVo;
import com.github.pagehelper.PageInfo;

public interface PermissionService {

	void addPermission(Permission permission);

	void deleteBatch(List<Integer> list);

	void updatePermission(Permission permission);

	Permission getPermission(Integer id);

	PageInfo<Permission> dataGrid(PermissionVo vo);

}
