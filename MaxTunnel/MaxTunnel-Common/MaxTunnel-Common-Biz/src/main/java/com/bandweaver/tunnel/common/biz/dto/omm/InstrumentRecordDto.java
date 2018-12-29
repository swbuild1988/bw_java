package com.bandweaver.tunnel.common.biz.dto.omm;

import java.util.Date;

public class InstrumentRecordDto extends InstrumentDto {

	private static final long serialVersionUID = 1L;
	
	private Integer instrumentId;
	private Integer staffId;
	private String staffName;
	private Integer returnId;
	private String returnName;
	private String describe;
	private String remark;
	private Date borrowTime;
	private Date returnTime;
	
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
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public Integer getReturnId() {
		return returnId;
	}
	public void setReturnId(Integer returnId) {
		this.returnId = returnId;
	}
	public String getReturnName() {
		return returnName;
	}
	public void setReturnName(String returnName) {
		this.returnName = returnName;
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
	@Override
	public String toString() {
		return "InstrumentRecordDto [instrumentId=" + instrumentId
				+ ", staffId=" + staffId + ", staffName=" + staffName
				+ ", returnId=" + returnId + ", returnName=" + returnName
				+ ", describe=" + describe + ", borrowTime=" + borrowTime
				+ ", remark=" + remark + ", returnTime="
				+ returnTime + "]";
	}
}
