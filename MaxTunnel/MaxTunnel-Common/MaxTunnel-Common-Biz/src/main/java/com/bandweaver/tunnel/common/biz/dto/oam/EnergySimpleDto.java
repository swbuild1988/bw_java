package com.bandweaver.tunnel.common.biz.dto.oam;

import java.io.Serializable;
import java.util.Date;

import com.bandweaver.tunnel.common.biz.constant.mam.ObjectType;

/**
 * ClassName: EnergySimpleDto
 * @Description: 
 * @author shaosen
 * @date 2018年5月31日
 */
public class EnergySimpleDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9194337091833133918L;
	/**自增id*/
    private Integer id;
    /**管廊id*/
    private Integer tunnelId;
    private String tunnelName;
    
    private Integer objectType;
    private String objectTypeName;
    
    /**能耗 单位：千瓦时*/
    private Double value;
    /**耗电单价*/
    private Double unitPrice;
    /**日期*/
    private Date crtTime;
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
	
	public Integer getObjectType() {
		return objectType;
	}
	public void setObjectType(Integer objectType) {
		this.objectType = objectType;
	}
	public String getObjectTypeName() {
		return ObjectType.getEnum(this.objectType).getName();
	}
	public void setObjectTypeName(String objectTypeName) {
		this.objectTypeName = objectTypeName;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public Date getCrtTime() {
		return crtTime;
	}
	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}
	
	public String getTunnelName() {
		return tunnelName;
	}
	public void setTunnelName(String tunnelName) {
		this.tunnelName = tunnelName;
	}
	@Override
	public String toString() {
		return "EnergySimpleDto [id=" + id + ", tunnelId=" + tunnelId + ", tunnelName=" + tunnelName + ", value="
				+ value + ", unitPrice=" + unitPrice + ", crtTime=" + crtTime + "]";
	}

    
}