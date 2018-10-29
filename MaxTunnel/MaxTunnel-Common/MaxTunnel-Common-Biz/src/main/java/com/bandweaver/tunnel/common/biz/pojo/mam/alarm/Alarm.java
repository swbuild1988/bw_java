package com.bandweaver.tunnel.common.biz.pojo.mam.alarm;

import java.io.Serializable;
import java.util.Date;

/**告警
 * @author shaosen
 * @date 2018年8月14日
 */
public class Alarm implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private Date alarmDate;
    
    private String alarmName;

    private Integer alarmLevel;

    private Integer tunnelId;

    private Integer objectId;
    
    private String objectName;
    
    private Boolean isDistribute;

    private String description;

    private Boolean cleaned;

    private Date cleanedDate;
    
    private String alarmSource;
    private String longitude;
    private String latitude;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAlarmName() {
		return alarmName;
	}

	public void setAlarmName(String alarmName) {
		this.alarmName = alarmName;
	}

	public Date getAlarmDate() {
        return alarmDate;
    }

    public void setAlarmDate(Date alarmDate) {
        this.alarmDate = alarmDate;
    }

    public Integer getAlarmLevel() {
        return alarmLevel;
    }

    public void setAlarmLevel(Integer alarmLevel) {
        this.alarmLevel = alarmLevel;
    }

    public Integer getTunnelId() {
        return tunnelId;
    }

    public void setTunnelId(Integer tunnelId) {
        this.tunnelId = tunnelId;
    }

    public Integer getObjectId() {
        return objectId;
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }

    public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public Boolean getIsDistribute() {
		return isDistribute;
	}

	public void setIsDistribute(Boolean isDistribute) {
		this.isDistribute = isDistribute;
	}

	public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }



	public Boolean getCleaned() {
		return cleaned;
	}

	public void setCleaned(Boolean cleaned) {
		this.cleaned = cleaned;
	}

	public Date getCleanedDate() {
		return cleanedDate;
	}

	public void setCleanedDate(Date cleanedDate) {
		this.cleanedDate = cleanedDate;
	}

	public String getAlarmSource() {
		return alarmSource;
	}

	public void setAlarmSource(String alarmSource) {
		this.alarmSource = alarmSource;
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

	@Override
	public String toString() {
		return "Alarm [id=" + id + ", alarmDate=" + alarmDate + ", alarmName=" + alarmName + ", alarmLevel="
				+ alarmLevel + ", tunnelId=" + tunnelId + ", objectId=" + objectId + ", objectName=" + objectName
				+ ", isDistribute=" + isDistribute + ", description=" + description + ", cleaned=" + cleaned
				+ ", cleanedDate=" + cleanedDate + ", alarmSource=" + alarmSource + ", longitude=" + longitude
				+ ", latitude=" + latitude + "]";
	}

	
    
}