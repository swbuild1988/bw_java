package com.bandweaver.tunnel.common.biz.dto;

import java.io.Serializable;

import com.bandweaver.tunnel.common.biz.constant.JobStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class ScheduleJobDto implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer jobId;

    private String jobName;

    private String jobGroup;

    @JsonIgnore
    private Integer jobStatus;
    private String jobStatusName;
    
    private String jobClass;
    
    private String jobMethod;

    private String cronExpression;

    private String description;

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName == null ? null : jobName.trim();
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup == null ? null : jobGroup.trim();
    }

    public Integer getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(Integer jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getJobClass() {
		return jobClass;
	}

	public void setJobClass(String jobClass) {
		this.jobClass = jobClass;
	}

	public String getJobMethod() {
		return jobMethod;
	}

	public void setJobMethod(String jobMethod) {
		this.jobMethod = jobMethod;
	}

	public String getJobStatusName() {
		return JobStatusEnum.getEnum(this.jobStatus).getName();
	}

	public void setJobStatusName(String jobStatusName) {
		this.jobStatusName = jobStatusName;
	}

	public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression == null ? null : cronExpression.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}