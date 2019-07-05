package com.bandweaver.tunnel.common.biz.pojo.omm;

import java.io.Serializable;
import java.util.Date;

public class InspectionTemplate implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer Id;
	
	private Integer staffId;
	
	private String name;
	
	private String info;
	
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

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
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
		return "InspectionTemplate [Id=" + Id + ", staffId=" + staffId + ", info="
				+ ", name=" + name + info + ", crtTime=" + crtTime + "]";
	}
	
}
