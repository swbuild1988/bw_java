package com.bandweaver.tunnel.common.biz.dto.oam;

import java.io.Serializable;
import java.util.Date;

import com.bandweaver.tunnel.common.biz.constant.mam.ObjectType;

/**
 * ClassName: EnergyDto
 * @Description: 能耗dto
 * @author shaosen
 * @date 2018年5月31日
 */
public class EnergyDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6489445152670735438L;
	/**自增id*/
    private Integer id;
    /**管廊id*/
    private Integer tunnelId;
    /**管廊名称*/
    private String tunnelName;
    
    /**能耗 单位：千瓦时*/
    private Double value;
    /**总能耗 单位：千瓦时*/
    private Double totalValue;
    /**耗电单价*/
    private Double unitPrice;
    /**平均耗电单价 单位：元/千瓦时*/
    private Double avgUnitPrice;
    /**耗电成本 单位：元*/
    private Double totalPrice;
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
	
	public String getTunnelName() {
		return tunnelName;
	}
	public void setTunnelName(String tunnelName) {
		this.tunnelName = tunnelName;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	public Double getTotalValue() {
		return totalValue == null ? 0.00 : totalValue;
	}
	public void setTotalValue(Double totalValue) {
		this.totalValue = totalValue;
	}
	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public Double getAvgUnitPrice() {
		return avgUnitPrice==null ? 0.00 : avgUnitPrice ;
	}
	public void setAvgUnitPrice(Double avgUnitPrice) {
		this.avgUnitPrice = avgUnitPrice;
	}
	public Double getTotalPrice() {
		return avgUnitPrice * totalValue;
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Date getCrtTime() {
		return crtTime;
	}
	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}
	@Override
	public String toString() {
		return "EnergyDto [id=" + id + ", tunnelId=" + tunnelId + ", tunnelName=" + tunnelName + ", value=" + value
				+ ", totalValue=" + totalValue + ", unitPrice=" + unitPrice + ", avgUnitPrice=" + avgUnitPrice
				+ ", totalPrice=" + totalPrice + ", crtTime=" + crtTime + "]";
	}
    
}