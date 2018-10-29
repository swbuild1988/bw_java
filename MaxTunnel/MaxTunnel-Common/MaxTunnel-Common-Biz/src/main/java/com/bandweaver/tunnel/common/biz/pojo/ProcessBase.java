package com.bandweaver.tunnel.common.biz.pojo;

import com.bandweaver.tunnel.common.biz.constant.ProcessTypeEnum;

import java.io.Serializable;
import java.util.Date;

public class ProcessBase implements Serializable {

    private Integer id;
    /**
     * 创建时间
     */
    private Date  createTime;
    /**
     * 流程类型
     */
    private Integer processType;
    private String processTypeName;
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

    public  ProcessBase(){
        this.createTime = new Date();
        this.processType = ProcessTypeEnum.NONE.getValue();
        this.processStatus = "未开始";
        this.processInstanceId = "";
        this.isFinished = false;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

	public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }

    public Boolean getIsFinished() {
		return isFinished;
	}

	public void setIsFinished(Boolean isFinished) {
		this.isFinished = isFinished;
	}

	@Override
    public String toString() {
        return "ProcessBase{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", processType=" + processType +
                ", processStatus='" + processStatus + '\'' +
                ", processInstanceId='" + processInstanceId + '\'' +
                ", isFinished=" + isFinished +
                '}';
    }
}
