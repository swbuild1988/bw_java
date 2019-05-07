package com.bandweaver.tunnel.common.biz.dto.em;

import java.io.Serializable;

import com.bandweaver.tunnel.common.biz.constant.ProcessTypeEnum;
import com.bandweaver.tunnel.common.biz.pojo.Staff;
import com.bandweaver.tunnel.common.biz.pojo.em.EmPlanGroup;

public class EmPlanGroupDto extends EmPlanGroup implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private String planName;

	private Staff staff;

	public String getPlanName() {
		ProcessTypeEnum e = ProcessTypeEnum.getEnum(super.getPlanId());
		return e == null ? null : e.getName();
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}
	

}