package com.bandweaver.tunnel.common.biz.dto;

import java.io.Serializable;
import java.util.Date;

import com.bandweaver.tunnel.common.biz.pojo.Area;
import com.bandweaver.tunnel.common.biz.pojo.Tunnel;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * ClassName: Area
 * @Description: 管廊区域
 * @author shaosen
 * @date 2018年6月11日
 */
public class AreaDto extends Area implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 2107770878243609365L;

    private TunnelSimpleDto tunnel;

    public TunnelSimpleDto getTunnel() {
        return tunnel;
    }

    public void setTunnel(TunnelSimpleDto tunnel) {
        this.tunnel = tunnel;
    }

    @Override
    public String toString() {
        return "AreaDto{" +
                "tunnel=" + tunnel +
                "} " + super.toString();
    }
}