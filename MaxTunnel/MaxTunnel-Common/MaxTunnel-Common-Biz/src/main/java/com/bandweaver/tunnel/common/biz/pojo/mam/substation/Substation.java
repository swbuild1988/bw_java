package com.bandweaver.tunnel.common.biz.pojo.mam.substation;

import java.io.Serializable;
import java.util.Date;

public class Substation implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer tunnelId;
	private Integer voltageId; // 电压id
	private Integer currentId; // 电流id
	
	private String name; // 回路名称
	private String voltageLevel; // 电压等级
	private Double magnification; // 倍率
	private Integer powerId; // 有功功率id
	private Integer unpowerId; // 无功功率id
	private Integer powerFactorId; // 功率因数id
	private Integer powerDayId; // 有功日电量id
	private Integer unpowerDayId; // 无功日电量id
	private Integer powerEleId; // 有功电量示值id
	private Integer unpowerEleId; // 无功电量示值id
	private Boolean type; // 类型   高压进线:true  低压出线:false
	
	private Double eleRadius; // 供电半径
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
	public Integer getVoltageId() {
		return voltageId;
	}
	public void setVoltageId(Integer voltageId) {
		this.voltageId = voltageId;
	}
	public Integer getCurrentId() {
		return currentId;
	}
	public void setCurrentId(Integer currentId) {
		this.currentId = currentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVoltageLevel() {
		return voltageLevel;
	}
	public void setVoltageLevel(String voltageLevel) {
		this.voltageLevel = voltageLevel;
	}
	public Double getMagnification() {
		return magnification;
	}
	public void setMagnification(Double magnification) {
		this.magnification = magnification;
	}
	public Integer getPowerId() {
		return powerId;
	}
	public void setPowerId(Integer powerId) {
		this.powerId = powerId;
	}
	public Integer getUnpowerId() {
		return unpowerId;
	}
	public void setUnpowerId(Integer unpowerId) {
		this.unpowerId = unpowerId;
	}
	public Integer getPowerFactorId() {
		return powerFactorId;
	}
	public void setPowerFactorId(Integer powerFactorId) {
		this.powerFactorId = powerFactorId;
	}
	public Integer getPowerDayId() {
		return powerDayId;
	}
	public void setPowerDayId(Integer powerDayId) {
		this.powerDayId = powerDayId;
	}
	public Integer getUnpowerDayId() {
		return unpowerDayId;
	}
	public void setUnpowerDayId(Integer unpowerDayId) {
		this.unpowerDayId = unpowerDayId;
	}
	public Integer getPowerEleId() {
		return powerEleId;
	}
	public void setPowerEleId(Integer powerEleId) {
		this.powerEleId = powerEleId;
	}
	public Integer getUnpowerEleId() {
		return unpowerEleId;
	}
	public void setUnpowerEleId(Integer unpowerEleId) {
		this.unpowerEleId = unpowerEleId;
	}
	public Double getEleRadius() {
		return eleRadius;
	}
	public void setEleRadius(Double eleRadius) {
		this.eleRadius = eleRadius;
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
	public Boolean getType() {
		return type;
	}
	public void setType(Boolean type) {
		this.type = type;
	}
	
}
