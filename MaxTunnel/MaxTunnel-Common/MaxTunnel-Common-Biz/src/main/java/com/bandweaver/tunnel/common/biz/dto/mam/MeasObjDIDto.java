package com.bandweaver.tunnel.common.biz.dto.mam;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObj;
import com.bandweaver.tunnel.common.biz.vo.BaseVo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class MeasObjDIDto implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Date refreshTime;
	private boolean cv;
	private MeasObjDto obj;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean getCv() {
		return cv;
	}

	public void setCv(boolean CV) {
		this.cv = CV;
	}

	public Date getRefreshTime() {
		return refreshTime;
	}

	public void setRefreshTime(Date refreshTime) {
		this.refreshTime = refreshTime;
	}

	public MeasObjDto getObj() {
		return obj;
	}

	public void setObj(MeasObjDto obj) {
		this.obj = obj;
	}

	@Override
	public String toString() {
		return "MeasObjAIDto [id=" + id + ", refreshTime=" + refreshTime + ", CV=" + cv + ", obj=" + obj + "]";
	}

}
