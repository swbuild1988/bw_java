package com.bandweaver.tunnel.common.biz.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName: MyTask
 * @Description: 登录用户任务
 * @author shaosen
 * @date 2018年7月18日
 */
public class MyTask implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String processDefinitionId;

    private Integer requestStaffId;

    private Integer approverId;
    
    private Integer status;

    private Integer processType;

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

    public Integer getApproverId() {
        return approverId;
    }

    public void setApproverId(Integer approverId) {
        this.approverId = approverId;
    }

    public Integer getProcessType() {
        return processType;
    }

    public void setProcessType(Integer processType) {
        this.processType = processType;
    }
    

    public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }
}