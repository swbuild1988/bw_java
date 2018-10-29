package com.bandweaver.tunnel.common.biz.dto.message;

import com.bandweaver.tunnel.common.biz.constant.ProcessTypeEnum;

import java.util.Date;

/**
 * 流程消息
 */
public class ProcessMessageDto {
    /**
     * 对应流程的ID
     */
    private int id;
    /**
     * 流程类型
     */
    private int processType;
    /**
     * 流程类型名称
     */
    private String processTypeName;
    /**
     * 流程申请时间
     */
    private Date crtTime;
    /**
     * 流程状态
     */
    private String status;
    /**
     * 申请人
     */
    private String staff;
    /**
     * 具体流程实例ID
     */
    private String processInstanceId;
    /**
     * 任务的关键字
     */
    private String taskKey;
    /**
     * 流程是否结束
     */
    private Boolean isFinished;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProcessType() {
        return processType;
    }

    public void setProcessType(int processType) {
        this.processType = processType;
    }

    public String getProcessTypeName() {
        return processTypeName;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStaff() {
        return staff;
    }

    public void setStaff(String staff) {
        this.staff = staff;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getTaskKey() {
        return taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public Boolean getIsFinished() {
		return isFinished;
	}

	public void setIsFinished(Boolean isFinished) {
		this.isFinished = isFinished;
	}

	@Override
    public String toString() {
        return "ProcessMessageDto{" +
                "id=" + id +
                ", processType=" + processType +
                ", processTypeName='" + processTypeName + '\'' +
                ", crtTime=" + crtTime +
                ", status='" + status + '\'' +
                ", staff='" + staff + '\'' +
                ", processInstanceId='" + processInstanceId + '\'' +
                ", taskKey='" + taskKey + '\'' +
                ", isFinished='" + isFinished + '\'' +
                '}';
    }
}
