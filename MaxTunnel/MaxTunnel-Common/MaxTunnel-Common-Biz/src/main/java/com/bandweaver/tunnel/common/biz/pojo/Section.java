package com.bandweaver.tunnel.common.biz.pojo;

import java.io.Serializable;
import java.util.Date;

public class Section implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 648624988268979022L;

	private Integer id;

    private String name;

    private Integer tunnelId;
    
    private Integer storeId;

    private Integer areaId;
    
    private Integer totalCableNumber;
    
    private String camera;
    
    private String startPoint;
    private String endPoint;

    private Date crtTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getTunnelId() {
		return tunnelId;
	}

	public void setTunnelId(Integer tunnelId) {
		this.tunnelId = tunnelId;
	}

	public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Integer getTotalCableNumber() {
		return totalCableNumber;
	}

	public void setTotalCableNumber(Integer totalCableNumber) {
		this.totalCableNumber = totalCableNumber;
	}

	public String getCamera() {
		return camera;
	}

	public void setCamera(String camera) {
		this.camera = camera;
	}

	public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

	public String getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(String startPoint) {
		this.startPoint = startPoint;
	}

	public String getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}

	@Override
	public String toString() {
		return "Section [id=" + id + ", name=" + name + ", tunnelId=" + tunnelId + ", storeId=" + storeId + ", areaId="
				+ areaId + ", totalCableNumber=" + totalCableNumber + ", camera=" + camera + ", startPoint="
				+ startPoint + ", endPoint=" + endPoint + ", crtTime=" + crtTime + "]";
	}

    
    
}