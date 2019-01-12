package com.bandweaver.tunnel.common.biz.dto.common;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.bandweaver.tunnel.common.biz.pojo.common.Permission;

public class RoleDto implements Serializable{
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String roleName;
    
    private List<Permission> permissionList;

    private Date crtTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

	public List<Permission> getPermissionList() {
		return permissionList;
	}

	public void setPermissionList(List<Permission> permissionList) {
		this.permissionList = permissionList;
	}
    
    
}