package com.bandweaver.tunnel.common.biz.dto.em;

import java.io.Serializable;

import com.bandweaver.tunnel.common.biz.constant.ProcessTypeEnum;
import com.bandweaver.tunnel.common.biz.pojo.Staff;
import com.bandweaver.tunnel.common.biz.pojo.em.EmPlanGroup;
import com.bandweaver.tunnel.common.biz.pojo.em.EmPlanMember;

public class EmPlanMemberDto extends EmPlanMember implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private EmPlanGroupDto group;

	private Staff staff;

	public EmPlanGroupDto getGroup() {
		return group;
	}

	public void setGroup(EmPlanGroupDto group) {
		this.group = group;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}
	

}