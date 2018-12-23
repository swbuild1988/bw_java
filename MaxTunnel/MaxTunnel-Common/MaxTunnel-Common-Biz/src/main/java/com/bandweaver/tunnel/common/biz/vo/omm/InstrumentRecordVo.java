package com.bandweaver.tunnel.common.biz.vo.omm;

import java.util.Date;

public class InstrumentRecordVo extends InstrumentVo {

	private static final long serialVersionUID = 1L;
	
	private Integer instrumentId;
	private Integer staffId;
	private Integer returnId;
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
	@Override
	public String toString() {
		return "InstrumentRecordVo [instrumentId=" + instrumentId
				+ ", staffId=" + staffId + ", retEndTime=" + retEndTime 
				+ ", retrunId=" + returnId + ", retStaTime="
				+ retStaTime + "]";
	}
}
