package com.bandweaver.tunnel.common.biz.pojo.em;

import java.io.Serializable;
import java.util.Date;

public class EmPlanMember implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

	private Integer groupId;

	private String tel;
	
	private String tel2;

	private Integer staffId;

	private Date time;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getTel2() {
		return tel2;
	}

	public void setTel2(String tel2) {
		this.tel2 = tel2;
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
		return "EmPlanMember [id=" + id + ", groupId=" + groupId + ", tel=" + tel
				+ ", tel2=" + tel2 + ", staffId=" + staffId + ", time=" + time + "]";
	}

}