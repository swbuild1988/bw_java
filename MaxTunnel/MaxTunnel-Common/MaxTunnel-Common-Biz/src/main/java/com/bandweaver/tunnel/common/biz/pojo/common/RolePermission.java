package com.bandweaver.tunnel.common.biz.pojo.common;

import java.io.Serializable;
import java.util.Date;

public class RolePermission implements Serializable{
	private static final long serialVersionUID = 1L;

	private Integer id;

    private Integer rId;

    private Integer pId;

    private Date crtTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getrId() {
        return rId;
    }

    public void setrId(Integer rId) {
        this.rId = rId;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }
}