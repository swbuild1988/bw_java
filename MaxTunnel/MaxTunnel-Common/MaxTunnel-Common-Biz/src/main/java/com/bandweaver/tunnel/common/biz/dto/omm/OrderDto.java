package com.bandweaver.tunnel.common.biz.dto.omm;

import com.bandweaver.tunnel.common.biz.constant.omm.OrderStatus;
import com.bandweaver.tunnel.common.biz.constant.omm.OrderType;

import java.io.Serializable;
import java.util.Date;

public class OrderDto implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -5437045194591335696L;
	private String id;
    private Integer tunnelId;
    private String tunnelName;
    private Integer type;
    private Integer defectId;
    private Integer workerId;
    private String workerName;
    private Date workTime;
    private Integer status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getTunnelId() {
        return tunnelId;
    }

    public void setTunnelId(Integer tunnelId) {
        this.tunnelId = tunnelId;
    }

    public String getTunnelName() {
        return tunnelName;
    }

    public void setTunnelName(String tunnelName) {
        this.tunnelName = tunnelName;
    }

    public String getType() {
        return OrderType.getEnum(this.type).getName();
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getDefectId() {
        return defectId;
    }

    public void setDefectId(Integer defectId) {
        this.defectId = defectId;
    }

    public Integer getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Integer workerId) {
        this.workerId = workerId;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public Date getWorkTime() {
        return workTime;
    }

    public void setWorkTime(Date workTime) {
        this.workTime = workTime;
    }

    public String getStatus() {
        return OrderStatus.getEnum(this.status).getName();
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "id='" + id + '\'' +
                ", tunnelId=" + tunnelId +
                ", tunnelName='" + tunnelName + '\'' +
                ", type=" + type +
                ", defectId=" + defectId +
                ", workerId=" + workerId +
                ", workerName='" + workerName + '\'' +
                ", workTime=" + workTime +
                ", status=" + status +
                '}';
    }

}
