package com.bandweaver.tunnel.common.biz.pojo.oam;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName: Energy
 * @Description: 能耗实体类
 * @author shaosen
 * @date 2018年5月31日
 */
public class Energy implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1088560896635573146L;
	/**自增id*/
    private Integer id;
    /**管廊id*/
    private Integer tunnelId;
    
    private Integer objectType;
    
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
	@Override
	public String toString() {
		return "Energy [id=" + id + ", tunnelId=" + tunnelId + ", objectType=" + objectType + ", value=" + value
				+ ", unitPrice=" + unitPrice + ", crtTime=" + crtTime + "]";
	}

    
}