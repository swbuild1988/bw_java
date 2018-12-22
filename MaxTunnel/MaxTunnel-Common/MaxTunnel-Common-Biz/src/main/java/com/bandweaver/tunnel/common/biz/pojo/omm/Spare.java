package com.bandweaver.tunnel.common.biz.pojo.omm;

import java.io.Serializable;
import java.util.Date;

public class Spare implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 8103815110685756611L;
	private Integer id;
	private String name;
    private Integer modelId;
    private Integer typeId;
    private Integer venderId;
    private Boolean status;
    private Date inTime;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getModelId() {
		return modelId;
	}
	public void setModelId(Integer modelId) {
		this.modelId = modelId;
	}
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public Date getInTime() {
		return inTime;
	}
	public void setInTime(Date inTime) {
		this.inTime = inTime;
	}
	public Integer getVenderId() {
		return venderId;
	}
	public void setVenderId(Integer venderId) {
		this.venderId = venderId;
	}
	
	@Override
	public String toString() {
		return "Spare [id=" + id + ", name=" + name + ", modelId=" + modelId
				+ ", typeId=" + typeId + ", status=" + status
				+ ", inTime=" + inTime + ", venderId=" + venderId + "]";
	}

}
