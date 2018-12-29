package com.bandweaver.tunnel.common.biz.pojo.oam;

import java.io.Serializable;
import java.util.Date;

public class Consumption implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 918152811613565333L;
	private int id;
    private int tunnelId;
    private String tunnelName;
    private String time;
    private double value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTunnelId() {
        return tunnelId;
    }

    public void setTunnelId(int tunnelId) {
        this.tunnelId = tunnelId;
    }

    public String getTunnelName() {
        return tunnelName;
    }

    public void setTunnelName(String tunnelName) {
        this.tunnelName = tunnelName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Consumption{" +
                "id=" + id +
                ", tunnelId=" + tunnelId +
                ", tunnelName='" + tunnelName + '\'' +
                ", time=" + time +
                ", value=" + value +
                '}';
    }
}
