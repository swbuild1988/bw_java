package com.bandweaver.tunnel.common.biz.pojo.omm;

import java.io.Serializable;
import java.util.Date;

public class SpareOut implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 8103815110685756611L;
	/** 备品id */
	private Integer id;
	/** 借用人id */
    private Integer staffId;
    /** 操作人id */
    private Integer userId;
    /** 出库时间 */
    private Date outTime;
    /** 备品描述 */
    private String describe;
    /** 备品去向 */
    private Integer whither;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getStaffId() {
		return staffId;
	}
	public void setStaffId(Integer staffId) {
		this.staffId = staffId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Date getOutTime() {
		return outTime;
	}
	public void setOutTime(Date outTime) {
		this.outTime = outTime;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	
	public Integer getWhither() {
		return whither;
	}
	public void setWhither(Integer whither) {
		this.whither = whither;
	}
	@Override
	public String toString() {
		return "SpareOut [id=" + id + ", staffId=" + staffId 
				+ ", userId=" + userId + ", outTime=" + outTime
				+ ", describe=" + describe +", whither=" + whither + "]";
	}
    
}
