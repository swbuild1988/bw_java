package com.bandweaver.tunnel.common.biz.pojo.omm;

import java.io.Serializable;

public class InspectionStep implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -3019798736904824136L;
	private Integer id;
    private String name;
    private Integer taskId;
    private Boolean isFinished;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public Boolean getIsFinished() {
		return isFinished;
	}

	public void setIsFinished(Boolean isFinished) {
		this.isFinished = isFinished;
	}

	@Override
	public String toString() {
		return "InspectionStap [id=" + id + ", name=" + name + ", taskId=" + taskId
				+ ", isFinished=" + isFinished + "]";
	}

}
