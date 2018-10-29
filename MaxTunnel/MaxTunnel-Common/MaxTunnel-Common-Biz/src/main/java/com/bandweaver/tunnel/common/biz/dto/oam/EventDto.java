package com.bandweaver.tunnel.common.biz.dto.oam;

import com.bandweaver.tunnel.common.biz.constant.oam.EventLevelEnum;
import com.bandweaver.tunnel.common.biz.pojo.Staff;
import com.bandweaver.tunnel.common.biz.pojo.oam.Event;

public class EventDto extends Event {
	
	private Staff staff;
	private String staffName;
	
	private String levelName;

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public String getLevelName() {
		return EventLevelEnum.getEnum(this.getLevel()).getName();
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	
	
}
