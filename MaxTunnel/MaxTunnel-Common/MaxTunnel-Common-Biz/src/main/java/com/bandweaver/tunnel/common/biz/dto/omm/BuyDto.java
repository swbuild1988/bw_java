package com.bandweaver.tunnel.common.biz.dto.omm;

import com.bandweaver.tunnel.common.biz.pojo.omm.Buy;

public class BuyDto extends Buy{

	private static final long serialVersionUID = 1L;
	
	private String typeName;
	private String workerName;
	
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getWorkerName() {
		return workerName;
	}
	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}
	@Override
	public String toString() {
		return "BuyDto ["+ super.toString() +"typeName=" + typeName + ", workerName="
				+ workerName + "]";
	}
	
}
