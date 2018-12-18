package com.bandweaver.tunnel.common.biz.vo.mam;

import com.bandweaver.tunnel.common.biz.vo.BaseVo;

import java.util.Date;
import java.util.List;

public class MeasObjDistributeVo extends BaseVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private List<Integer> ids;
	private Date refreshTime;
	private String dcv;
	private Double spacePrecision; 

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}

	public Date getRefreshTime() {
		return refreshTime;
	}

	public void setRefreshTime(Date refreshTime) {
		this.refreshTime = refreshTime;
	}

	public String getDcv() {
		return dcv;
	}

	public void setDcv(String dcv) {
		this.dcv = dcv;
	}

	public Double getSpacePrecision() {
		return spacePrecision;
	}

	public void setSpacePrecision(Double spacePrecision) {
		this.spacePrecision = spacePrecision;
	}

	
}
