package com.bandweaver.tunnel.common.biz.vo.oam;

import com.bandweaver.tunnel.common.biz.vo.BaseVo;

/**
 * 查询能耗数据vo
 * @author ya.liu
 * @Date 2018年11月15日
 */
public class ConsumeDataVo extends BaseVo {

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
	
}
