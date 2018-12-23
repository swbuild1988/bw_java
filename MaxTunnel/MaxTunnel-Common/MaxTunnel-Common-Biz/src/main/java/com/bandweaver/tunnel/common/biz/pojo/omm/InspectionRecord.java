package com.bandweaver.tunnel.common.biz.pojo.omm;

import java.io.Serializable;
import java.util.Date;

public class InspectionRecord implements Serializable {

    private Integer id;
    /**
     * 属于哪次任务
     */
    private Integer taskId;
    /**
     * 管舱段id
     */
    private Integer sectionId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 记录时间
     */
    private Date recordTime;
    /**
     * 内容
     */
    private String content;
    /**
     * 缺陷ID，如果没有则为空
     */
    private Integer defectId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }
    
    

    public Integer getSectionId() {
		return sectionId;
	}

	public void setSectionId(Integer sectionId) {
		this.sectionId = sectionId;
	}

	public Date getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getDefectId() {
        return defectId;
    }

    public void setDefectId(Integer defectId) {
        this.defectId = defectId;
    }

    @Override
    public String toString() {
        return "InspectionRecord{" +
                "id=" + id +
                ", taskId=" + taskId +
                ", createTime='" + createTime + '\'' +
                ", content='" + content + '\'' +
                ", defectId=" + defectId +
                '}';
    }
}
