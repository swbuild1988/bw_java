package com.bandweaver.tunnel.common.biz.vo;

import java.io.Serializable;
import java.util.Date;

public class CompanyVo extends BaseVo {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2091227829335125696L;

	private Integer id;

    private String name;

    private Date crtTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }
}