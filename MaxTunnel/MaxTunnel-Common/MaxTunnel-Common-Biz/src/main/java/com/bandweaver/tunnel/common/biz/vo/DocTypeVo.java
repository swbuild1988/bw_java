package com.bandweaver.tunnel.common.biz.vo;

import java.util.Date;

public class DocTypeVo extends BaseVo {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private String name;
	
	private Integer parentId;
	
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
		this.name = name;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Date getCrtTime() {
		return crtTime;
	}

	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}

	@Override
	public String toString() {
		return "DocTypeVo [id=" + id + ", name=" + name + ", parentId=" + parentId
				+ ", crtTime=" + crtTime + "]";
	}
	
}
