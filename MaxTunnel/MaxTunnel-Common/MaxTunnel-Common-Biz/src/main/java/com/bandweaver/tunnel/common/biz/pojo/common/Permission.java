package com.bandweaver.tunnel.common.biz.pojo.common;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.Date;

public class Permission implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String menuCode;

    private String menuName;

    private String permissionCode;

    private String permissionName;

    private JSONObject etc;

    private Date crtTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode == null ? null : menuCode.trim();
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName == null ? null : menuName.trim();
    }

    public String getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode == null ? null : permissionCode.trim();
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName == null ? null : permissionName.trim();
    }

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

    public JSONObject getEtc() {
        return etc;
    }

    public void setEtc(JSONObject etc) {
        this.etc = etc;
    }
}