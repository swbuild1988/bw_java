package com.bandweaver.tunnel.common.biz.pojo.oam;

import java.io.Serializable;
import java.util.Date;

import com.bandweaver.tunnel.common.biz.constant.ProcessTypeEnum;
import com.bandweaver.tunnel.common.biz.pojo.ProcessBase;

public class ReqHistory extends ProcessBase implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

	private Integer staffId;

	private Integer approverId;

	private Integer tunnelId;

	private Integer action;

	private Date preTime;

	private Date enterTime;

	private Date exitTime;

	private Integer visitorNumber;

	private Integer visitorCompany;

	private String visitorInfo;

	private String remark;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStaffId() {
		return staffId;
	}

	public void setStaffId(Integer staffId) {
		this.staffId = staffId;
	}

	public Integer getApproverId() {
		return approverId;
	}

	public void setApproverId(Integer approverId) {
		this.approverId = approverId;
	}

	public Integer getTunnelId() {
		return tunnelId;
	}

	public void setTunnelId(Integer tunnelId) {
		this.tunnelId = tunnelId;
	}

	public Integer getAction() {
		return action;
	}

	public void setAction(Integer action) {
		this.action = action;
	}

	public Date getPreTime() {
		return preTime;
	}

	public void setPreTime(Date preTime) {
		this.preTime = preTime;
	}

	public Date getEnterTime() {
		return enterTime;
	}

	public void setEnterTime(Date enterTime) {
		this.enterTime = enterTime;
	}

	public Date getExitTime() {
		return exitTime;
	}

	public void setExitTime(Date exitTime) {
		this.exitTime = exitTime;
	}

	public Integer getVisitorNumber() {
		return visitorNumber;
	}

	public void setVisitorNumber(Integer visitorNumber) {
		this.visitorNumber = visitorNumber;
	}

	public Integer getVisitorCompany() {
		return visitorCompany;
	}

	public void setVisitorCompany(Integer visitorCompany) {
		this.visitorCompany = visitorCompany;
	}

	public String getVisitorInfo() {
		return visitorInfo;
	}

	public void setVisitorInfo(String visitorInfo) {
		this.visitorInfo = visitorInfo == null ? null : visitorInfo.trim();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	
	public ReqHistory() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "ReqHistory{" +
				"id=" + id +
				", staffId=" + staffId +
				", approverId=" + approverId +
				", tunnelId=" + tunnelId +
				", action=" + action +
				", preTime=" + preTime +
				", enterTime=" + enterTime +
				", exitTime=" + exitTime +
				", visitorNumber=" + visitorNumber +
				", visitorCompany=" + visitorCompany +
				", visitorInfo='" + visitorInfo + '\'' +
				", remark='" + remark + '\'' +
				"} " + super.toString();
	}


}