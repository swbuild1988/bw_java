package com.bandweaver.tunnel.common.biz.dto;

import java.io.Serializable;
import java.util.Date;

import com.bandweaver.tunnel.common.biz.constant.ProcessStatusEnum;
import com.bandweaver.tunnel.common.biz.constant.ProcessTypeEnum;
import com.bandweaver.tunnel.common.biz.pojo.Staff;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * ClassName: MyTask
 * 
 * @Description: 登录用户任务
 * @author shaosen
 * @date 2018年7月18日
 */
public class MyTaskDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String processDefinitionId;

	@JsonIgnore
	private Integer requestStaffId;

	private Staff staff;

	@JsonIgnore
	private Integer approverId;

	private Staff approver;
	
	private Integer status;

	private String statusName;

	private Integer processType;

	private String processTypeName;

	private Date crtTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId == null ? null : processDefinitionId.trim();
	}

	public Integer getRequestStaffId() {
		return requestStaffId;
	}

	public void setRequestStaffId(Integer requestStaffId) {
		this.requestStaffId = requestStaffId;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public Integer getApproverId() {
		return approverId;
	}

	public void setApproverId(Integer approverId) {
		this.approverId = approverId;
	}

	public Staff getApprover() {
		return approver;
	}

	public void setApprover(Staff approver) {
		this.approver = approver;
	}

	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getStatusName() {
		return this.status == null ? null : "";
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public Integer getProcessType() {
		return processType;
	}

	public void setProcessType(Integer processType) {
		this.processType = processType;
	}

	public String getProcessTypeName() {
		return ProcessTypeEnum.getEnum(this.processType).getName();
	}

	public void setProcessTypeName(String processTypeName) {
		this.processTypeName = processTypeName;
	}

	public Date getCrtTime() {
		return crtTime;
	}

	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}

	@Override
	public String toString() {
		return "MyTaskDto{" +
				"id=" + id +
				", processDefinitionId='" + processDefinitionId + '\'' +
				", requestStaffId=" + requestStaffId +
				", staff=" + staff +
				", approverId=" + approverId +
				", approver=" + approver +
				", status=" + status +
				", statusName='" + statusName + '\'' +
				", processType=" + processType +
				", processTypeName='" + processTypeName + '\'' +
				", crtTime=" + crtTime +
				'}';
	}
}