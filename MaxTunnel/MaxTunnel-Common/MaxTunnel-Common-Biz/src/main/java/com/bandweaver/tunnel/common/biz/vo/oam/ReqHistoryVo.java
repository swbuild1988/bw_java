package com.bandweaver.tunnel.common.biz.vo.oam;

import java.io.Serializable;
import java.util.Date;

import com.bandweaver.tunnel.common.biz.itf.ProcessBaseService;
import com.bandweaver.tunnel.common.biz.vo.BaseVo;

public class ReqHistoryVo extends BaseVo {


	private Integer id;

	private Integer staffId;

	private Integer approverId;

	private Integer tunnelId;

	private String value; // agree disagree

	private Integer action;

	private Date preTime;

	private Date enterTime;

	private Date exitTime;
	private Date crtTime;

	private Integer visitorNumber;

	private Integer visitorCompany;

	private String visitorInfo;

	
	 /**
     * 流程类型
     */
    private Integer processType;
    /**
     * 流程当前状态
     */
    private String processStatus;
    /**
     * 绑定的流程
     */
    private String processInstanceId;
    /**
     * 流程是否结束
     */
    private Boolean isFinished;
    
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getProcessType() {
		return processType;
	}

	public void setProcessType(Integer processType) {
		this.processType = processType;
	}

	public String getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public Boolean getIsFinished() {
		return isFinished;
	}

	public void setIsFinished(Boolean isFinished) {
		this.isFinished = isFinished;
	}
	
	

}