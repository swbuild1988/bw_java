package com.bandweaver.tunnel.common.biz.vo.omm;

import java.util.Date;

import com.bandweaver.tunnel.common.biz.constant.omm.EquipmentStatusEnum;
import com.bandweaver.tunnel.common.biz.constant.omm.EquipmentTypeEnum;
import com.bandweaver.tunnel.common.biz.vo.BaseVo;


/**
 * ClassName: EquipmentVo
 * 
 * @Description:接收前段数据
 * @author shaosen
 * @date 2018年5月31日
 */
public class EquipmentVo extends BaseVo{
	

	private Integer id;
	private String assetNo;
	private String name;
	/**设备类型*/
	private Integer type;
	private Date crtTime;
	private Integer serviceLife;
	/**设备状态*/
	private Integer status;
	private String imgUrl;
    
    private Integer tunnelId;
	private Integer modelId;
	private Integer venderId;
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
	public Integer getServiceLife() {
		return serviceLife;
	}
	public void setServiceLife(Integer serviceLife) {
		this.serviceLife = serviceLife;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
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
	
	
	

	
}
