package com.bandweaver.tunnel.common.biz.pojo.omm;

import java.io.Serializable;
import java.util.Date;
/**
 * 仪表工具
 * @author ya.liu
 * @Date 2018年11月30日
 */
public class Instrument implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 8103815110685756611L;
	
	private Integer id;
	private Integer spareId;
	private String name;
    private Integer modelId;
    private Integer typeId;
    private Integer venderId;
    private Integer useStatus;
    private Boolean status;
    private Date inTime;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getSpareId() {
		return spareId;
	}
	public void setSpareId(Integer spareId) {
		this.spareId = spareId;
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
	public Integer getVenderId() {
		return venderId;
	}
	public void setVenderId(Integer venderId) {
		this.venderId = venderId;
	}
	public Integer getUseStatus() {
		return useStatus;
	}
	public void setUseStatus(Integer useStatus) {
		this.useStatus = useStatus;
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
	@Override
	public String toString() {
		return "Instrument [id=" + id + ", spareId=" + spareId + ", name=" 
				+ name + ", modelId=" + modelId + ", typeId=" + typeId 
				+ ", venderId=" + venderId + ", useStatus=" + useStatus 
				+ ", status=" + status + ", inTime=" + inTime + "]";
	}
    
}
