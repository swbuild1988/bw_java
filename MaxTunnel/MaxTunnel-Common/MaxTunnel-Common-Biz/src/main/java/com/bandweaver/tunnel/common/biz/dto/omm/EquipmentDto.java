package com.bandweaver.tunnel.common.biz.dto.omm;

import java.io.Serializable;
import java.util.Date;

import com.bandweaver.tunnel.common.biz.constant.omm.EquipmentStatusEnum;
import com.bandweaver.tunnel.common.biz.constant.omm.EquipmentTypeEnum;
import com.bandweaver.tunnel.common.biz.dto.TunnelSimpleDto;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObj;
import com.bandweaver.tunnel.common.biz.pojo.omm.EquipmentModel;
import com.bandweaver.tunnel.common.biz.pojo.omm.EquipmentVender;
import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * ClassName: EquipmentDto
 * 
 * @Description: 返回前端页面的数据
 * @author shaosen
 * @date 2018年5月31日
 */
public class EquipmentDto implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -2181426117543507255L;
	private Integer id;
	private String assetNo;
	private String name;
	/**设备类型*/
	private Integer type;
	private String typeName;
	private Date crtTime;
	private Integer serviceLife;
	/**设备状态*/
	private Integer status;
	private String statusName;
	private String imgUrl;
    
	/** 管廊名称 */
	@JsonIgnore
    private Integer tunnelId;
	private TunnelSimpleDto tunnel;
	/** 设备型号名称 */
	@JsonIgnore
	private Integer modelId;
	private EquipmentModel model;
	/** 供应商名称名称 */
	@JsonIgnore
	private Integer venderId;
	private EquipmentVender vender;
	
	/** 设备对应的对象名称 */
	private Integer objId;
	private MeasObj obj;
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getTunnelId() {
		return tunnelId;
	}
	public void setTunnelId(Integer tunnelId) {
		this.tunnelId = tunnelId;
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
	/**
	 * @Description: 获取设备类型名称
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author shaosen
	 * @date 2018年5月31日
	 */
	public String getTypeName() {
		EquipmentTypeEnum en = EquipmentTypeEnum.getEnum(String.valueOf(type));
		return en == null ? String.valueOf(type) : en.getName();
	}
	
	public Date getCrtTime() {
		return crtTime;
	}
	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}
	public Integer getServiceLife() {
		return serviceLife;
	}
	public void setServiceLife(Integer serviceLife) {
		this.serviceLife = serviceLife;
	}
	/**
	 * @Description: 获取设备状态
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author shaosen
	 * @date 2018年5月31日
	 */
	public String getStatusName() {
		EquipmentStatusEnum en = EquipmentStatusEnum.getEnum(String.valueOf(status));
		return en == null ? String.valueOf(status) : en.getName();
	}
	

	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public TunnelSimpleDto getTunnel() {
		return tunnel;
	}
	public void setTunnel(TunnelSimpleDto tunnel) {
		this.tunnel = tunnel;
	}
	public EquipmentModel getModel() {
		return model;
	}
	public void setModel(EquipmentModel model) {
		this.model = model;
	}
	public EquipmentVender getVender() {
		return vender;
	}
	public void setVender(EquipmentVender vender) {
		this.vender = vender;
	}
	public Integer getObjId() {
		return objId;
	}
	public void setObjId(Integer objId) {
		this.objId = objId;
	}
	public MeasObj getObj() {
		return obj;
	}
	public void setObj(MeasObj obj) {
		this.obj = obj;
	}
	
	
	
}
