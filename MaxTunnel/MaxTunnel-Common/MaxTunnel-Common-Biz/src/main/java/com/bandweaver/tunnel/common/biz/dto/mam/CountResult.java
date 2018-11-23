package com.bandweaver.tunnel.common.biz.dto.mam;

import java.io.Serializable;

public class CountResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer objectId;
	
	private Integer countNum;

	public Integer getObjectId() {
		return objectId;
	}

	public void setObjectId(Integer objectId) {
		this.objectId = objectId;
	}

	public Integer getCountNum() {
		return countNum;
	}

	public void setCountNum(Integer countNum) {
		this.countNum = countNum;
	}

	@Override
	public String toString() {
		return "CountResult [objectId=" + objectId + ", countNum=" + countNum + "]";
	}
	
	

}
