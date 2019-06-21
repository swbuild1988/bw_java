package com.bandweaver.tunnel.common.biz.pojo.common;

import java.util.Date;

public class TunnelLight {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer tunnelId;
    private Integer areaId;
    private Integer storeId;
    private Integer sectionId;
    private Double lon;
    private Double lat;
    private Double height;
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

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getSectionId() {
        return sectionId;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

    @Override
    public String toString() {
        return "TunnelLight{" +
                "id=" + id +
                ", tunnelId=" + tunnelId +
                ", areaId=" + areaId +
                ", storeId=" + storeId +
                ", sectionId=" + sectionId +
                ", lon=" + lon +
                ", lat=" + lat +
                ", height=" + height +
                ", crtTime=" + crtTime +
                '}';
    }
}
