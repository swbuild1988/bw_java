package com.bandweaver.tunnel.common.biz.dto.omm;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.pojo.Staff;
import com.bandweaver.tunnel.common.biz.pojo.omm.InspectionPlan;

public class InspectionPlanSimpleDto extends InspectionPlan implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -1978181664789581304L;
	private String groupName;
    private String tunnelName;
    private String requestStatusName;
    private Staff staff;
    
    private List<JSONObject> others;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getTunnelName() {
        return tunnelName;
    }

    public void setTunnelName(String tunnelName) {
        this.tunnelName = tunnelName;
    }
    

    public String getRequestStatusName() {
		return this.getProcessStatus();
	}

	public void setRequestStatusName(String requestStatusName) {
		this.requestStatusName = requestStatusName;
	}
	
	

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public List<JSONObject> getOthers() {
		return others;
	}

	public void setOthers(List<JSONObject> others) {
		this.others = others;
	}

	@Override
	public String toString() {
		return "InspectionPlanSimpleDto [groupName=" + groupName + ", tunnelName="
				+ tunnelName + ", requestStatusName=" + requestStatusName
				+ ", staff=" + staff + ", others=" + others + "]";
	}
}
