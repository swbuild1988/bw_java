package com.bandweaver.tunnel.common.biz.pojo.omm;

import com.bandweaver.tunnel.common.biz.pojo.ProcessBase;

import java.io.Serializable;
import java.util.Date;

public class InspectionTask extends ProcessBase implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7973258709489297279L;

    /**
     * 所属计划id
     */
    private String planId;
    /**
     * 任务原定时间
     */
    private Date taskTime;
    /**
     * 巡检时间
     */
    private Date inspectTime;
    /**
     * 巡检开始时间
     */
    private Date startTime;
    /**
     * 巡检结束时间
     */
    private Date endTime;
    /**
     * 巡检描述
     */
    private String describe;
    /**
     * 巡检人ID
     */
    private Integer inspectManId;

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public Date getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(Date taskTime) {
        this.taskTime = taskTime;
    }

    public Date getInspectTime() {
        return inspectTime;
    }

    public void setInspectTime(Date inspectTime) {
        this.inspectTime = inspectTime;
    }

    public Integer getInspectManId() {
        return inspectManId;
    }

    public void setInspectManId(Integer inspectManId) {
        this.inspectManId = inspectManId;
    }

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	@Override
	public String toString() {
		return "InspectionTask [planId=" + planId + ", taskTime=" 
				+ taskTime + ", inspectTime=" + inspectTime
				+ ", startTime=" + startTime + ", endTime=" + endTime 
				+ ", describe=" + describe + ", inspectManId="
				+ inspectManId + "]";
	}

   
}
