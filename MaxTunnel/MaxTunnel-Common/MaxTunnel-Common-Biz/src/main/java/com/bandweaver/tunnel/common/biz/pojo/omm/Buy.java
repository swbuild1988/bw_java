package com.bandweaver.tunnel.common.biz.pojo.omm;

import java.io.Serializable;
import java.util.Date;

public class Buy implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer typeId;
	private Integer worker;
	private String description;
	private Date buyTime;
	private Date crtTime;
	private Boolean isFinished;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	public Integer getWorker() {
		return worker;
	}
	public void setWorker(Integer worker) {
		this.worker = worker;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getBuyTime() {
		return buyTime;
	}
	public void setBuyTime(Date buyTime) {
		this.buyTime = buyTime;
	}
	public Date getCrtTime() {
		return crtTime;
	}
	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}
	public Boolean getIsFinished() {
		return isFinished;
	}
	public void setIsFinished(Boolean isFinished) {
		this.isFinished = isFinished;
	}
	@Override
	public String toString() {
		return "Buy [id=" + id + ", typeId=" + typeId + ", worker=" + worker
				+ ", description=" + description + ", buyTime=" + buyTime
				+ ", crtTime=" + crtTime + ", isFinished=" + isFinished + "]";
	}
	
}
