package com.bandweaver.tunnel.common.biz.pojo.em;

import java.io.Serializable;
import java.util.Date;

public class EmPlan implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String processKey;

	private String taskKey;

	private String taskName;

	private Integer targetKey;

	private String targetValue;

	private Integer actionKey;

	private String actionValue;

	private Integer finishKey;

	private Integer finishValue;

	private Boolean isFinished;

	private Date crtTime;

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
		return "EmPlan [id=" + id + ", processKey=" + processKey + ", taskKey=" + taskKey + ", taskName=" + taskName
				+ ", targetKey=" + targetKey + ", targetValue=" + targetValue + ", actionKey=" + actionKey
				+ ", actionValue=" + actionValue + ", finishKey=" + finishKey + ", finishValue=" + finishValue
				+ ", isFinished=" + isFinished + ", crtTime=" + crtTime + "]";
	}

}