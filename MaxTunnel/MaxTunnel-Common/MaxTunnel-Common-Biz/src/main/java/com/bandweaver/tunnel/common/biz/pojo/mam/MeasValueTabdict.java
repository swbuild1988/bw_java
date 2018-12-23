package com.bandweaver.tunnel.common.biz.pojo.mam;

import java.io.Serializable;
import java.util.Date;

public class MeasValueTabdict implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 6142415567272413149L;

	private int id;

    private int dataType;

    private int seqNO;

    private String histabName;

    private int days;

    private Date startDate;

    private Date endDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDataType() {
		return dataType;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

	

	public int getSeqNO() {
		return seqNO;
	}

	public void setSeqNO(int seqNO) {
		this.seqNO = seqNO;
	}

	public String getHistabName() {
		return histabName;
	}

	public void setHistabName(String histabName) {
		this.histabName = histabName;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

   
}