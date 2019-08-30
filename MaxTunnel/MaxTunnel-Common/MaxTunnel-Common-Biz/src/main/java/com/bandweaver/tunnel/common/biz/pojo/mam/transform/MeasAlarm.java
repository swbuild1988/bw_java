package com.bandweaver.tunnel.common.biz.pojo.mam.transform;

import java.io.Serializable;
import java.util.Date;

/**
 * 中间类
 * 
 * @author shaosen
 * @date 2018年10月19日
 */
public class MeasAlarm implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer alarmID;
	private String alarmName;
	private Integer objTypeID;
	private Integer alarmTypeID;
	private Date time;
	private Integer objectId;
	private String alarmSource;
	private Integer alarmSeverity;
	private String additionalText;
	private Double longitude;
	private Double latitude;
	private Boolean acked;
	private String ackUser;
	private Date ackTime;
	private Integer ackResult;
	private Boolean cleared;
	private String clearedUser;
	private Date clearedTime;

	public Integer getAlarmID() {
		return alarmID;
	}

	public void setAlarmID(Integer alarmID) {
		this.alarmID = alarmID;
	}

	public String getAlarmName() {
		return alarmName;
	}

	public void setAlarmName(String alarmName) {
		this.alarmName = alarmName;
	}

	public Integer getObjTypeID() {
		return objTypeID;
	}

	public void setObjTypeID(Integer objTypeID) {
		this.objTypeID = objTypeID;
	}

	public Integer getAlarmTypeID() {
		return alarmTypeID;
	}

	public void setAlarmTypeID(Integer alarmTypeID) {
		this.alarmTypeID = alarmTypeID;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Integer getObjectId() {
		return objectId;
	}

	public void setObjectId(Integer objectId) {
		this.objectId = objectId;
	}

	public String getAlarmSource() {
		return alarmSource;
	}

	public void setAlarmSource(String alarmSource) {
		this.alarmSource = alarmSource;
	}

	public Integer getAlarmSeverity() {
		return alarmSeverity;
	}

	public void setAlarmSeverity(Integer alarmSeverity) {
		this.alarmSeverity = alarmSeverity;
	}

	public String getAdditionalText() {
		return additionalText;
	}

	public void setAdditionalText(String additionalText) {
		this.additionalText = additionalText;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Boolean getAcked() {
		return acked;
	}

	public void setAcked(Boolean acked) {
		this.acked = acked;
	}

	public String getAckUser() {
		return ackUser;
	}

	public void setAckUser(String ackUser) {
		this.ackUser = ackUser;
	}

	public Date getAckTime() {
		return ackTime;
	}

	public void setAckTime(Date ackTime) {
		this.ackTime = ackTime;
	}

	public Boolean getCleared() {
		return cleared;
	}

	public void setCleared(Boolean cleared) {
		this.cleared = cleared;
	}

	public String getClearedUser() {
		return clearedUser;
	}

	public void setClearedUser(String clearedUser) {
		this.clearedUser = clearedUser;
	}

	public Date getClearedTime() {
		return clearedTime;
	}

	public void setClearedTime(Date clearedTime) {
		this.clearedTime = clearedTime;
	}

	public Integer getAckResult() {
		return ackResult;
	}

	public void setAckResult(Integer ackResult) {
		this.ackResult = ackResult;
	}

	@Override
	public String toString() {
		return "MeasAlarm [alarmID=" + alarmID + ", alarmName=" + alarmName + ", objTypeID=" + objTypeID
				+ ", alarmTypeID=" + alarmTypeID + ", time=" + time + ", objectId=" + objectId + ", alarmSource="
				+ alarmSource + ", alarmSeverity=" + alarmSeverity + ", additionalText=" + additionalText
				+ ", longitude=" + longitude + ", latitude=" + latitude + ", acked=" + acked + ", ackUser=" + ackUser
				+ ", ackTime=" + ackTime + ", ackResult=" + ackResult + ", cleared=" + cleared + ", clearedUser="
				+ clearedUser + ", clearedTime=" + clearedTime + "]";
	}

}
