package com.bandweaver.tunnel.common.biz.vo.omm;

import java.util.Date;

import com.bandweaver.tunnel.common.biz.vo.BaseVo;

public class BuyVo extends BaseVo {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer typeId;
	private Integer worker;
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
		return "BuyVo [id=" + id + ", typeId=" + typeId + ", worker=" + worker
				+ ", crtTime=" + crtTime + ", isFinished=" + isFinished + "]";
	}
	
}
