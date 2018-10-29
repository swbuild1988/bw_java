package com.bandweaver.tunnel.common.biz.pojo.omm;

import com.bandweaver.tunnel.common.biz.pojo.ProcessBase;

import java.io.Serializable;
import java.util.Date;

public class MaintenanceOrder extends ProcessBase implements Serializable {
    private String orderId;
    private Integer tunnelId;
    private Integer defectId;
    private Integer worker;
    private Date maintenanceTime;
    private Date startTime;
    private Date endTime;
    private String maintenanceResult;
    private String describe;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getTunnelId() {
        return tunnelId;
    }

    public void setTunnelId(Integer tunnelId) {
        this.tunnelId = tunnelId;
    }

    public Integer getDefectId() {
        return defectId;
    }

    public void setDefectId(Integer defectId) {
        this.defectId = defectId;
    }

    public Integer getWorker() {
        return worker;
    }

    public void setWorker(Integer worker) {
        this.worker = worker;
    }

    public Date getMaintenanceTime() {
        return maintenanceTime;
    }

    public void setMaintenanceTime(Date maintenanceTime) {
        this.maintenanceTime = maintenanceTime;
    }

    public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getMaintenanceResult() {
		return maintenanceResult;
	}

	public void setMaintenanceResult(String maintenanceResult) {
		this.maintenanceResult = maintenanceResult;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	@Override
    public String toString() {
        return "MaintenanceOrder{" +
                "orderId='" + orderId + '\'' +
                ", tunnelId=" + tunnelId +
                ", defectId=" + defectId +
                ", worker=" + worker +
                ", maintenanceTime=" + maintenanceTime +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", maintenanceResult=" + maintenanceResult +
                ", describe=" + describe +
                "} " + super.toString();
    }
}
