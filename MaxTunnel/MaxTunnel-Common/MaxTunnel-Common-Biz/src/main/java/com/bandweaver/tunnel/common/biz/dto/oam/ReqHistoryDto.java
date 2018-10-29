package com.bandweaver.tunnel.common.biz.dto.oam;

import java.io.Serializable;
import java.util.Date;

import com.bandweaver.tunnel.common.biz.constant.ActionEnum;
import com.bandweaver.tunnel.common.biz.constant.ProcessStatusEnum;
import com.bandweaver.tunnel.common.biz.dto.TunnelSimpleDto;
import com.bandweaver.tunnel.common.biz.pojo.Company;
import com.bandweaver.tunnel.common.biz.pojo.ProcessBase;
import com.bandweaver.tunnel.common.biz.pojo.Staff;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class ReqHistoryDto extends ProcessBase implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

	private Integer approverId;

	@JsonIgnore
	private Integer staffId;
	private Staff staff;

	@JsonIgnore
	private Integer tunnelId;
	private TunnelSimpleDto tunnel;

	private Integer action;
	private String actionName;

	private Date preTime;

	private Date enterTime;

	private Date exitTime;

	private Date crtTime;

	private Integer visitorNumber;
	@JsonIgnore
	private Integer visitorCompany;
	private Company Company;

	private String visitorInfo;
	
	private String comment;
	private String result;

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

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public TunnelSimpleDto getTunnel() {
		return tunnel;
	}

	public void setTunnel(TunnelSimpleDto tunnel) {
		this.tunnel = tunnel;
	}

	public String getActionName() {
		return ActionEnum.getEnum(this.action).getName();
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public Company getCompany() {
		return Company;
	}

	public void setCompany(Company company) {
		Company = company;
	}

	public Integer getApproverId() {
		return approverId;
	}

	public void setApproverId(Integer approverId) {
		this.approverId = approverId;
	}

	public Date getCrtTime() {
		return crtTime;
	}

	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}