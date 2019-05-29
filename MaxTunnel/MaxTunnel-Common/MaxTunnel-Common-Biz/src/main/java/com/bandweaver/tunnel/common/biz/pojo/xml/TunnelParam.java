package com.bandweaver.tunnel.common.biz.pojo.xml;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "TunnelParam")
public class TunnelParam implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer tunnelNumber;
	private Integer tunnelLength;
	
	private Integer areaNumber;
	private Integer areaLength;
	
	private Integer storeNumber;
	private Integer storeLength;
	
	private Integer typeNumber;
	private Integer typeLength;
	
	public TunnelParam() {}
	
	public TunnelParam(Integer tunnelNumber, Integer tunnelLength, Integer areaNumber, Integer areaLength,
			Integer storeNumber, Integer storeLength, Integer typeNumber, Integer typeLength) {
		super();
		this.tunnelNumber = tunnelNumber;
		this.tunnelLength = tunnelLength;
		this.areaNumber = areaNumber;
		this.areaLength = areaLength;
		this.storeNumber = storeNumber;
		this.storeLength = storeLength;
		this.typeNumber = typeNumber;
		this.typeLength = typeLength;
	}



	public Integer getTunnelNumber() {
		return tunnelNumber;
	}
	public void setTunnelNumber(Integer tunnelNumber) {
		this.tunnelNumber = tunnelNumber;
	}
	public Integer getTunnelLength() {
		return tunnelLength;
	}
	public void setTunnelLength(Integer tunnelLength) {
		this.tunnelLength = tunnelLength;
	}
	public Integer getAreaNumber() {
		return areaNumber;
	}
	public void setAreaNumber(Integer areaNumber) {
		this.areaNumber = areaNumber;
	}
	public Integer getAreaLength() {
		return areaLength;
	}
	public void setAreaLength(Integer areaLength) {
		this.areaLength = areaLength;
	}
	public Integer getStoreNumber() {
		return storeNumber;
	}
	public void setStoreNumber(Integer storeNumber) {
		this.storeNumber = storeNumber;
	}
	public Integer getStoreLength() {
		return storeLength;
	}
	public void setStoreLength(Integer storeLength) {
		this.storeLength = storeLength;
	}
	public Integer getTypeNumber() {
		return typeNumber;
	}
	public void setTypeNumber(Integer typeNumber) {
		this.typeNumber = typeNumber;
	}
	public Integer getTypeLength() {
		return typeLength;
	}
	public void setTypeLength(Integer typeLength) {
		this.typeLength = typeLength;
	}
	
}
