package com.bandweaver.tunnel.common.biz.dto;

import java.io.Serializable;
import java.util.Date;

import com.bandweaver.tunnel.common.biz.constant.TunnelStatus;
import com.bandweaver.tunnel.common.biz.pojo.Company;
import com.bandweaver.tunnel.common.biz.pojo.Staff;
import com.bandweaver.tunnel.common.biz.pojo.Tunnel;
import com.bandweaver.tunnel.common.biz.pojo.mam.MaxviewConfig;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class TunnelDto extends Tunnel implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    private Staff responsibility;

    private Company construct;

    private Company operation;

    private MaxviewConfig maxviewConfig;

    private String statusName;

    public Staff getResponsibility() {
        return responsibility;
    }

    public void setResponsibility(Staff responsibility) {
        this.responsibility = responsibility;
    }

    public Company getConstruct() {
        return construct;
    }

    public void setConstruct(Company construct) {
        this.construct = construct;
    }

    public Company getOperation() {
        return operation;
    }

    public void setOperation(Company operation) {
        this.operation = operation;
    }

    public MaxviewConfig getMaxviewConfig() {
        return maxviewConfig;
    }

    public void setMaxviewConfig(MaxviewConfig maxviewConfig) {
        this.maxviewConfig = maxviewConfig;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    @Override
    public String toString() {
        return "TunnelDto{" +
                "responsibility=" + responsibility +
                ", construct=" + construct +
                ", operation=" + operation +
                ", maxviewConfig=" + maxviewConfig +
                ", statusName='" + statusName + '\'' +
                "} " + super.toString();
    }
}