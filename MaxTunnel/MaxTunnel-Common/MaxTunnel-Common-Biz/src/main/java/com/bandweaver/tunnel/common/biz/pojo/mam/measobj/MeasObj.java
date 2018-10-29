package com.bandweaver.tunnel.common.biz.pojo.mam.measobj;

import java.io.Serializable;
import java.util.Date;

public class MeasObj implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
    private Integer tunnelId;
    private Integer storeId;
    private Integer areaId;
    private Integer sectionId;
    private String name;
    private Integer datatypeId;
    private Integer objtypeId;
    private boolean actived;
    private String description;
    /** 经度 */
    private String longitude;
    /** 维度 */
    private String latitude;
    /** 高度 */
    private String height;
    /** 偏差 */
    private Double deviation;

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

	public Integer getSectionId() {
        return sectionId;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDatatypeId() {
        return datatypeId;
    }

    public void setDatatypeId(Integer datatypeId) {
        this.datatypeId = datatypeId;
    }

    public Integer getObjtypeId() {
        return objtypeId;
    }

    public void setObjtypeId(Integer objtypeId) {
        this.objtypeId = objtypeId;
    }

    public boolean isActived() {
        return actived;
    }

    public void setActived(boolean actived) {
        this.actived = actived;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Double getDeviation() {
        return deviation;
    }

    public void setDeviation(Double deviation) {
        this.deviation = deviation;
    }

    @Override
	public String toString() {
		return "MeasObj [id=" + id + ", tunnelId=" + tunnelId + ", storeId=" + storeId + ", areaId=" + areaId
				+ ", sectionId=" + sectionId + ", name=" + name + ", datatypeId=" + datatypeId + ", objtypeId="
				+ objtypeId + ", actived=" + actived + ", description=" + description + ", longitude=" + longitude
				+ ", latitude=" + latitude + ", height=" + height + ", deviation=" + deviation + "]";
	}
}
