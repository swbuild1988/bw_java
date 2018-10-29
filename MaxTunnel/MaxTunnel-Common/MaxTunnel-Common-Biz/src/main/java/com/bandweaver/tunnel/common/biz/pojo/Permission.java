package com.bandweaver.tunnel.common.biz.pojo;

import java.io.Serializable;
import java.util.Date;

public class Permission implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7728026293262355935L;

	private Short id;

    private String code;

    private String desc;

    private Date crtTime;

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

	@Override
	public String toString() {
		return "Permission [id=" + id + ", code=" + code + ", desc=" + desc + ", crtTime=" + crtTime + "]";
	}
    
}