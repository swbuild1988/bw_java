package com.bandweaver.tunnel.common.biz.dto.em;

import java.io.Serializable;
import java.util.Date;

import com.bandweaver.tunnel.common.biz.constant.em.ActionEnum;
import com.bandweaver.tunnel.common.biz.constant.em.FinishEnum;
import com.bandweaver.tunnel.common.biz.constant.em.TargetEnum;

public class EmPlanDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String processKey;

	private String taskKey;

	private String taskName;

	private Integer targetKey;
	
	private String targetName;

	private String targetValue;

	private Integer actionKey;
	
	private String actionName;

	private String actionValue;

	private Integer finishKey;
	
	private String finishName;

	private Integer finishValue;

	private Boolean isFinished;

	private Date crtTime;
	

	public String getTargetName() {
		return TargetEnum.getEnum(this.targetKey).getName();
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public String getActionName() {
		return ActionEnum.getEnum(this.actionKey).getName();
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getFinishName() {
		return FinishEnum.getEnum(this.finishKey).getName();
	}

	public void setFinishName(String finishName) {
		this.finishName = finishName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProcessKey() {
		return processKey;
	}

	public void setProcessKey(String processKey) {
		this.processKey = processKey == null ? null : processKey.trim();
	}

	public String getTaskKey() {
		return taskKey;
	}

	public void setTaskKey(String taskKey) {
		this.taskKey = taskKey == null ? null : taskKey.trim();
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Integer getTargetKey() {
		return targetKey;
	}

	public void setTargetKey(Integer targetKey) {
		this.targetKey = targetKey;
	}

	public String getTargetValue() {
		return targetValue;
	}

	public void setTargetValue(String targetValue) {
		this.targetValue = targetValue;
	}

	public Integer getActionKey() {
		return actionKey;
	}

	public void setActionKey(Integer actionKey) {
		this.actionKey = actionKey;
	}

	public String getActionValue() {
		return actionValue;
	}

	public void setActionValue(String actionValue) {
		this.actionValue = actionValue;
	}

	public Integer getFinishKey() {
		return finishKey;
	}

	public void setFinishKey(Integer finishKey) {
		this.finishKey = finishKey;
	}

	public Integer getFinishValue() {
		return finishValue;
	}

	public void setFinishValue(Integer finishValue) {
		this.finishValue = finishValue;
	}

	public Boolean getIsFinished() {
		return isFinished;
	}

	public void setIsFinished(Boolean isFinished) {
		this.isFinished = isFinished;
	}

	public Date getCrtTime() {
		return crtTime;
	}

	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}

	@Override
	public String toString() {
		return "EmPlanDto [id=" + id + ", processKey=" + processKey + ", taskKey=" + taskKey + ", taskName=" + taskName
				+ ", targetKey=" + targetKey + ", targetName=" + targetName + ", targetValue=" + targetValue
				+ ", actionKey=" + actionKey + ", actionName=" + actionName + ", actionValue=" + actionValue
				+ ", finishKey=" + finishKey + ", finishName=" + finishName + ", finishValue=" + finishValue
				+ ", isFinished=" + isFinished + ", crtTime=" + crtTime + "]";
	}

}