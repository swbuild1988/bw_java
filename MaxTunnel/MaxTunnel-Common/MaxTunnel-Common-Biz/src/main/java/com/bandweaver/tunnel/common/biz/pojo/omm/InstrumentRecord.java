package com.bandweaver.tunnel.common.biz.pojo.omm;

import java.io.Serializable;
import java.util.Date;

public class InstrumentRecord implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer instrumentId; //仪表id
	private Integer staffId; //借用人id
	private Integer returnId; //归还人id
	private Date borrowTime; //借用时间
	private Date returnTime; //归还时间
	private String describe; //备注
	private String remark; //借出描述
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getInstrumentId() {
		return instrumentId;
	}
	public void setInstrumentId(Integer instrumentId) {
		this.instrumentId = instrumentId;
	}
	public Integer getStaffId() {
		return staffId;
	}
	public void setStaffId(Integer staffId) {
		this.staffId = staffId;
	}
	public Integer getReturnId() {
		return returnId;
	}
	public void setReturnId(Integer returnId) {
		this.returnId = returnId;
	}
	public Date getBorrowTime() {
		return borrowTime;
	}
	public void setBorrowTime(Date borrowTime) {
		this.borrowTime = borrowTime;
	}
	public Date getReturnTime() {
		return returnTime;
	}
	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "InstrumentRecord [id=" + id + ", instrumentId="
				+ instrumentId + ", staffId=" + staffId + ", borrowTime="
				+ borrowTime + ", returnTime=" + returnTime + ", describe="
				+ describe + ", remark=" + remark + ", returnId="
				+ returnId + "]";
	}
}
