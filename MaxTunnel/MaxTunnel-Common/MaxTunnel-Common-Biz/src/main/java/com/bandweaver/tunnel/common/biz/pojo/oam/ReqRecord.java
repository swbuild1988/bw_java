package com.bandweaver.tunnel.common.biz.pojo.oam;

import java.io.Serializable;
import java.util.Date;

public class ReqRecord implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer staffId;
	private Integer equipmentId;
	private Date time;
	private String longitude;
    private String latitude;
    private String height;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getStaffId() {
		return staffId;
	}
	public void setStaffId(Integer staffId) {
		this.staffId = staffId;
	}
	public Integer getEquipmentId() {
		return equipmentId;
	}
	public void setEquipmentId(Integer equipmentId) {
		this.equipmentId = equipmentId;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	@Override
	public String toString() {
		return "ReqRecord [id=" + id + ", staffId=" + staffId
				+ ", equipmentId=" + equipmentId + ", time=" + time
				+ ", longitude=" + longitude + ", latitude=" + latitude
				+ ", height=" + height + "]";
	}
    
}
