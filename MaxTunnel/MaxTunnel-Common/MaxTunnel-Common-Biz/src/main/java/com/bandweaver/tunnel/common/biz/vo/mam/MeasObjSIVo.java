package com.bandweaver.tunnel.common.biz.vo.mam;

import com.bandweaver.tunnel.common.biz.vo.BaseVo;

import java.util.Date;
import java.util.List;

public class MeasObjSIVo extends BaseVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private List<Integer> ids;
	private Date refreshTime;
	private Integer cv;

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

	public Integer getCv() {
		return cv;
	}

	public void setCv(Integer CV) {
		this.cv = CV;
	}

	public Date getRefreshTime() {
		return refreshTime;
	}

	public void setRefreshTime(Date refreshTime) {
		this.refreshTime = refreshTime;
	}

	@Override
	public String toString() {
		return "MeasObjAIVo [id=" + id + ", ids=" + ids + ", refreshTime=" + refreshTime + ", CV=" + cv + "]";
	}

}
