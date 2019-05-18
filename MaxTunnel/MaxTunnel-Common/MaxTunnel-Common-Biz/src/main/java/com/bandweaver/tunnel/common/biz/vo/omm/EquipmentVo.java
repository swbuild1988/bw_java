package com.bandweaver.tunnel.common.biz.vo.omm;

import java.util.Date;

import com.bandweaver.tunnel.common.biz.vo.BaseVo;


/**
 * ClassName: EquipmentVo
 * 
 * @Description:接收前段数据
 * @author shaosen
 * @date 2018年5月31日
 */
public class EquipmentVo extends BaseVo{

	private static final long serialVersionUID = -7268420534954351487L;

	private Integer id;
	private String assetNo;
	private String name;
	/**设备类型*/
	private Integer type;
	private Date crtTime;
	private Date runTime;
	/** 告警次数 */
	private Integer alarmNo;
	/**设备状态*/
	private Integer status;
    private Integer tunnelId;
    private Integer sectionId;
	private Integer modelId;
	private Integer venderId;
	private Integer objId;
	
	private String qaTerm;
    private String ratedVoltage;
    private String range;
    private String factory;
    private String brand;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAssetNo() {
		return assetNo;
	}
	public void setAssetNo(String assetNo) {
		this.assetNo = assetNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Date getCrtTime() {
		return crtTime;
	}
	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getSectionId() {
		return sectionId;
	}
	public void setSectionId(Integer sectionId) {
		this.sectionId = sectionId;
	}
	public Integer getModelId() {
		return modelId;
	}
	public void setModelId(Integer modelId) {
		this.modelId = modelId;
	}
	public Integer getVenderId() {
		return venderId;
	}
	public void setVenderId(Integer venderId) {
		this.venderId = venderId;
	}
	public Date getRunTime() {
		return runTime;
	}
	public void setRunTime(Date runTime) {
		this.runTime = runTime;
	}
	public Integer getAlarmNo() {
		return alarmNo;
	}
	public void setAlarmNo(Integer alarmNo) {
		this.alarmNo = alarmNo;
	}
	public Integer getTunnelId() {
		return tunnelId;
	}
	public void setTunnelId(Integer tunnelId) {
		this.tunnelId = tunnelId;
	}
	public String getQaTerm() {
		return qaTerm;
	}
	public void setQaTerm(String qaTerm) {
		this.qaTerm = qaTerm;
	}
	public String getRatedVoltage() {
		return ratedVoltage;
	}
	public void setRatedVoltage(String ratedVoltage) {
		this.ratedVoltage = ratedVoltage;
	}
	public String getRange() {
		return range;
	}
	public void setRange(String range) {
		this.range = range;
	}
	public String getFactory() {
		return factory;
	}
	public void setFactory(String factory) {
		this.factory = factory;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public Integer getObjId() {
		return objId;
	}
	public void setObjId(Integer objId) {
		this.objId = objId;
	}
	
}
