package com.bandweaver.tunnel.common.biz.dto.omm;

import java.io.Serializable;
import java.util.List;

import com.bandweaver.tunnel.common.biz.pojo.Staff;
import com.bandweaver.tunnel.common.biz.pojo.omm.InspectionGroup;

public class InspectionGroupDto extends InspectionGroup implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 3310434999086755177L;
	
    private Staff leader;
    private List<Staff> members;

    public Staff getLeader() {
        return leader;
    }

    public void setLeader(Staff leader) {
        this.leader = leader;
    }

    public List<Staff> getMembers() {
        return members;
    }

    public void setMembers(List<Staff> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return "InspectionGroupDto{" +
                super.toString() +
                ", leader=" + leader +
                ", members=" + members +
                '}';
    }
}
