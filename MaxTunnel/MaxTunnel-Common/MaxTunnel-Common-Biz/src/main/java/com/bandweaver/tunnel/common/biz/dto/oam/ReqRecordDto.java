package com.bandweaver.tunnel.common.biz.dto.oam;

import com.bandweaver.tunnel.common.biz.pojo.Staff;
import com.bandweaver.tunnel.common.biz.pojo.oam.ReqRecord;

public class ReqRecordDto extends ReqRecord {

	private static final long serialVersionUID = 1L;
	
	private Staff staff;
	private String equipmentName;
	
	public Staff getStaff() {
		return staff;
	}
	public void setStaff(Staff staff) {
		this.staff = staff;
	}
	public String getEquipmentName() {
		return equipmentName;
	}
	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}
	@Override
	public String toString() {
		return "ReqRecordDto [staff=" + staff + ", equipmentName="
				+ equipmentName + "]";
	}
	
}
