package com.bandweaver.tunnel.common.biz.dto.mam;

import java.io.Serializable;
import java.util.Date;

public class MeasObjDistributeDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Date refreshTime;
	private Double spacePrecision;
	private String dcv;
	private MeasObjDto obj;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getRefreshTime() {
		return refreshTime;
	}
	public void setRefreshTime(Date refreshTime) {
		this.refreshTime = refreshTime;
	}
	public Double getSpacePrecision() {
		return spacePrecision;
	}
	public void setSpacePrecision(Double spacePrecision) {
		this.spacePrecision = spacePrecision;
	}
	public String getDcv() {
		return dcv;
	}
	public void setDcv(String dcv) {
		this.dcv = dcv;
	}
	public MeasObjDto getObj() {
		return obj;
	}
	public void setObj(MeasObjDto obj) {
		this.obj = obj;
	}
	@Override
	public String toString() {
		return "MeasObjDistributeDto [id=" + id + ", refreshTime=" + refreshTime + ", spacePrecision=" + spacePrecision
				+ ", dcv=" + dcv + ", obj=" + obj + "]";
	}
	
	

}
