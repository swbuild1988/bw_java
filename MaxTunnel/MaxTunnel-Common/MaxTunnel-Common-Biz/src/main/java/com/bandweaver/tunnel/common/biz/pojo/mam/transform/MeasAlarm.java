package com.bandweaver.tunnel.common.biz.pojo.mam.transform;

/**中间类
 * @author shaosen
 * @date 2018年10月19日
 */
public class MeasAlarm {
	public long time;
	public String alarmName;
	public int objectId;
	public int alarmSeverity;
	public String additionalText;
	public String alarmSource;
	public String longitude;
	public String latitude;
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	
	public String getAlarmName() {
		return alarmName;
	}
	public void setAlarmName(String alarmName) {
		this.alarmName = alarmName;
	}
	public int getObjectId() {
		return objectId;
	}
	public void setObjectId(int objectId) {
		this.objectId = objectId;
	}
	public int getAlarmSeverity() {
		return alarmSeverity;
	}
	public void setAlarmSeverity(int alarmSeverity) {
		this.alarmSeverity = alarmSeverity;
	}
	public String getAdditionalText() {
		return additionalText;
	}
	public void setAdditionalText(String additionalText) {
		this.additionalText = additionalText;
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
		return "MeasAlarm [time=" + time + ", alarmName=" + alarmName + ", objectId=" + objectId + ", alarmSeverity="
				+ alarmSeverity + ", additionalText=" + additionalText + ", alarmSource=" + alarmSource + ", longitude="
				+ longitude + ", latitude=" + latitude + "]";
	}
	
	
}
