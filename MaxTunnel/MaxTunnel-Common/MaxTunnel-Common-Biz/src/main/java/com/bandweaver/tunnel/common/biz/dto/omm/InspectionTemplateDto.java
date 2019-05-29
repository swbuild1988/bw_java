package com.bandweaver.tunnel.common.biz.dto.omm;

import com.bandweaver.tunnel.common.biz.pojo.omm.InspectionTemplate;

public class InspectionTemplateDto extends InspectionTemplate {

	private static final long serialVersionUID = 1L;
	
	private String staffName;

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	
	
}
