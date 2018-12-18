package com.bandweaver.tunnel.common.biz.pojo.common;

import java.io.Serializable;
import java.util.Date;

public class UserRole implements Serializable{
	private static final long serialVersionUID = 1L;

	private Integer id;

    private Integer uId;

    private Integer rId;

    private Date crtTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public Integer getrId() {
        return rId;
    }

    public void setrId(Integer rId) {
        this.rId = rId;
    }

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }
}