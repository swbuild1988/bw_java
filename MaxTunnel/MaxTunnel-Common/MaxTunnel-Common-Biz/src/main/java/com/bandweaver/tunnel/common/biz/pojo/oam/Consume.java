package com.bandweaver.tunnel.common.biz.pojo.oam;

import java.io.Serializable;

/**
 * @author ya.liu
 * @Date 2018年11月13日
 */
public class Consume implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/**
	 * 能耗id
	 */
	private Integer id;
	/**
	 * 管廊id
	 */
	private Integer tunnelId;
	/**
	 * 管舱id
	 */
	private Integer storeId;
	/**
	 * 区段id
	 */
	private Integer areaId;
	/**
	 * 对象类型
	 */
	private Integer objectType;
	/**
	 * 能耗类型
	 */
	private Integer energyType;
	/**
	 * 对象id
	 */
	private Integer objectId;
	/**
	 * 计算式
	 */
	private String compute;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getTunnelId() {
		return tunnelId;
	}
	public void setTunnelId(Integer tunnelId) {
		this.tunnelId = tunnelId;
	}
	public Integer getStoreId() {
		return storeId;
	}
	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}
	public Integer getAreaId() {
		return areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	public Integer getObjectType() {
		return objectType;
	}
	public void setObjectType(Integer objectType) {
		this.objectType = objectType;
	}
	public Integer getEnergyType() {
		return energyType;
	}
	public void setEnergyType(Integer energyType) {
		this.energyType = energyType;
	}
	public Integer getObjectId() {
		return objectId;
	}
	public void setObjectId(Integer objectId) {
		this.objectId = objectId;
	}
	public String getCompute() {
		return compute;
	}
	public void setCompute(String compute) {
		this.compute = compute;
	}
	@Override
	public String toString() {
		return "Meter [id=" + id + ", tunnelId=" + tunnelId 
				+ ", storeId=" + storeId + ", areaId=" + areaId
				+ ", objectType=" + objectType + ", energyType=" 
				+ energyType + ", objectId=" + objectId + ", compute="
				+ compute + "]";
	}
	
	
}
