package com.bandweaver.tunnel.common.biz.vo.em;

import java.util.Date;

import com.bandweaver.tunnel.common.biz.vo.BaseVo;

public class EmPlanMemberVo extends BaseVo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

	private Integer groupId;

	private String tel;
	
	private String tel2;

	private Integer staffId;
	
	private Integer role;
	
	private Integer planId;

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

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	public Integer getPlanId() {
		return planId;
	}

	public void setPlanId(Integer planId) {
		this.planId = planId;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "EmPlanMemberVo [id=" + id + ", groupId=" + groupId + ", tel=" + tel
				+ ", tel2=" + tel2 + ", staffId=" + staffId + ", role=" + role
				+ ", planId=" + planId + ", time=" + time + "]";
	}

}