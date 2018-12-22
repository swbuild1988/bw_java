package com.bandweaver.tunnel.common.biz.dto.omm;

import com.bandweaver.tunnel.common.biz.pojo.omm.MaintenanceOrder;

public class MaintenanceOrderDto extends MaintenanceOrder {
    private String tunnelName;
    private String workerName;
    private String defectName;

    public String getTunnelName() {
        return tunnelName;
    }

    public void setTunnelName(String tunnelName) {
        this.tunnelName = tunnelName;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public String getDefectName() {
		return defectName;
	}

	public void setDefectName(String defectName) {
		this.defectName = defectName;
	}

	@Override
    public String toString() {
        return "MaintenanceOrderDto{" +
                "tunnelName='" + tunnelName + '\'' +
                ", workerName='" + workerName + '\'' +
                ", defectName='" + defectName + '\'' +
                "} " + super.toString();
    }
}
