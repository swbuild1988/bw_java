package com.bandweaver.tunnel.common.biz.pojo.em;

import java.io.Serializable;
import java.util.Date;

public class EmPlanGroup implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

	private Integer planId;

	private String name;

	private Integer staffId;

	private Date time;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPlanId() {
		return planId;
	}

	public void setPlanId(Integer planId) {
		this.planId = planId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStaffId() {
		return staffId;
	}

	public void setStaffId(Integer staffId) {
		this.staffId = staffId;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "EmPlanGroup [id=" + id + ", planId=" + planId + ", name=" + name
				+ ", staffId=" + staffId + ", time=" + time + "]";
	}

	
}