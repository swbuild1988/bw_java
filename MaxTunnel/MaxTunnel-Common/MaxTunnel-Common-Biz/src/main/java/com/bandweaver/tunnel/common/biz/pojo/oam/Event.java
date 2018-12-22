package com.bandweaver.tunnel.common.biz.pojo.oam;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName: Event
 * @Description: 活动事件
 * @author ya.liu
 * @date 2018年10月16日
 */
public class Event implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	/**
	 * 责任人id
	 */
	private Integer staffId;
	/**
	 * 事件名称
	 */
    private String name;
    /**
	 * 事件范围/半径
	 */
    private Double range;
    /**
	 * 事件级别
	 */
    private Integer level;
    /**
	 * 经度
	 */
    private String longitude;
    /**
	 * 纬度
	 */
    private String latitude;
    /**
	 * 事件开始时间
	 */
    private Date startTime;
    /**
	 * 事件结束时间
	 */
    private Date endTime;
    /**
	 * 创建时间
	 */
    private Date crtTime;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getRange() {
		return range;
	}

	public void setRange(Double range) {
		this.range = range;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
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

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getCrtTime() {
		return crtTime;
	}

	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}

	@Override
	public String toString() {
		return "Event [id=" + id + ", staffId=" + staffId + ", name=" 
				+ name + ", range=" + range + ", level=" + level
				+ ", longitude=" + longitude + ", latitude=" + latitude 
				+ ", startTime=" + startTime + ", endTime="
				+ endTime + ", crtTime=" + crtTime + "]";
	}

    
}