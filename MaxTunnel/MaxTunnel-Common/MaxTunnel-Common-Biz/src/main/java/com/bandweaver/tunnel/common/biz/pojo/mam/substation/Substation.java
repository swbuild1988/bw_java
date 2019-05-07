package com.bandweaver.tunnel.common.biz.pojo.mam.substation;

import java.io.Serializable;
import java.util.Date;

public class Substation implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer tunnelId;
	private Integer invId; // 进线电压id
	private Integer inaId; // 进线电流id
	private Integer outvId; // 出线电压id
	private Integer outaId; // 出线电流id
	private Integer powerId; // 实时功率id
	
	private Double eleRadius; // 供电半径
	private Double powerFactor; // 功率因数
	private Double compensationFactor; // 功率补偿因数
	private String position; // 位置信息
	private Date time; // 创建时间
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
	public Integer getInvId() {
		return invId;
	}
	public void setInvId(Integer invId) {
		this.invId = invId;
	}
	public Integer getInaId() {
		return inaId;
	}
	public void setInaId(Integer inaId) {
		this.inaId = inaId;
	}
	public Integer getOutvId() {
		return outvId;
	}
	public void setOutvId(Integer outvId) {
		this.outvId = outvId;
	}
	public Integer getOutaId() {
		return outaId;
	}
	public void setOutaId(Integer outaId) {
		this.outaId = outaId;
	}
	public Integer getPowerId() {
		return powerId;
	}
	public void setPowerId(Integer powerId) {
		this.powerId = powerId;
	}
	public Double getEleRadius() {
		return eleRadius;
	}
	public void setEleRadius(Double eleRadius) {
		this.eleRadius = eleRadius;
	}
	public Double getPowerFactor() {
		return powerFactor;
	}
	public void setPowerFactor(Double powerFactor) {
		this.powerFactor = powerFactor;
	}
	public Double getCompensationFactor() {
		return compensationFactor;
	}
	public void setCompensationFactor(Double compensationFactor) {
		this.compensationFactor = compensationFactor;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
}
