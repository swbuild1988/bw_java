package com.bandweaver.tunnel.common.biz.vo.omm;

import java.util.Date;

import com.bandweaver.tunnel.common.biz.vo.BaseVo;

public class InspectionTemplateVo extends BaseVo{

private static final long serialVersionUID = 1L;
	
	private Integer Id;
	
	private Integer staffId;
	
	private String name;
	
	private Date crtTime;

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public Integer getStaffId() {
		return staffId;
	}

	public void setStaffId(Integer staffId) {
		this.staffId = staffId;
	}

	public Date getCrtTime() {
		return crtTime;
	}

	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "InspectionTemplate [Id=" + Id + ", staffId=" + staffId
				+ ", crtTime=" + crtTime + ", name=" + name + "]";
	}
	
}
