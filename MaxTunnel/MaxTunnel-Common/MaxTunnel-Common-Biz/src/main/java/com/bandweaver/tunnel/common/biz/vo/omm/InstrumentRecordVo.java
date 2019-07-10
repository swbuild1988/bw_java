package com.bandweaver.tunnel.common.biz.vo.omm;

import java.util.Date;

public class InstrumentRecordVo extends InstrumentVo {

	private static final long serialVersionUID = 1L;
	
	private Integer instrumentId;
	private Integer staffId;
	private String staffName;
	private Integer returnId;
	private String returnName;
	private Date retStaTime;
	private Date retEndTime;
	
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
	public Date getRetEndTime() {
		return retEndTime;
	}
	public void setRetEndTime(Date retEndTime) {
		this.retEndTime = retEndTime;
	}
	public Date getRetStaTime() {
		return retStaTime;
	}
	public void setRetStaTime(Date retStaTime) {
		this.retStaTime = retStaTime;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public String getReturnName() {
		return returnName;
	}
	public void setReturnName(String returnName) {
		this.returnName = returnName;
	}
	@Override
	public String toString() {
		return "InstrumentRecordVo [instrumentId=" + instrumentId + ", staffId="
				+ staffId + ", staffName=" + staffName + ", returnId=" + returnId
				+ ", returnName=" + returnName + ", retStaTime=" + retStaTime
				+ ", retEndTime=" + retEndTime + "]";
	}
	
}
