package com.bandweaver.tunnel.common.biz.pojo.omm;

import java.io.Serializable;
import java.util.List;

public class InspectionGroup implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -3019798736904824136L;
	private Integer id;
    private String name;
    private Integer leaderId;
    private String leaderName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(Integer leaderId) {
        this.leaderId = leaderId;
    }

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    @Override
    public String toString() {
        return "InspectionGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", leaderId=" + leaderId +
                ", leaderName='" + leaderName + '\'' +
                '}';
    }
}
