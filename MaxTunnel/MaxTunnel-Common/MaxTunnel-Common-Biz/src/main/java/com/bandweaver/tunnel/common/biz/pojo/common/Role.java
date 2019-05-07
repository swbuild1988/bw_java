package com.bandweaver.tunnel.common.biz.pojo.common;

import java.io.Serializable;
import java.util.Date;

public class Role implements Serializable{
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String roleName;

    private String routList;

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

    public String getRoutList() {
        return routList;
    }

    public void setRoutList(String routList) {
        this.routList = routList;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                ", routList='" + routList + '\'' +
                ", crtTime=" + crtTime +
                '}';
    }
}