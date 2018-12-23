package com.bandweaver.tunnel.common.biz.dto;

import java.io.Serializable;
import java.util.Date;

import com.bandweaver.tunnel.common.biz.pojo.Tunnel;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * ClassName: Area
 * @Description: 管廊区域
 * @author shaosen
 * @date 2018年6月11日
 */
public class AreaDto implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 2107770878243609365L;

	private Integer id;

    private String name;

    private String sn;

    private String location;

    @JsonIgnore
    private Integer tunnelId;
    private TunnelSimpleDto tunnel;

    private String camera;
    
    private Date crtTime;

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
        this.name = name == null ? null : name.trim();
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public Integer getTunnelId() {
        return tunnelId;
    }

    public void setTunnelId(Integer tunnelId) {
        this.tunnelId = tunnelId;
    }
    

	public TunnelSimpleDto getTunnel() {
		return tunnel;
	}

	public void setTunnel(TunnelSimpleDto tunnel) {
		this.tunnel = tunnel;
	}

	public String getCamera() {
		return camera;
	}

	public void setCamera(String camera) {
		this.camera = camera;
	}

	public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

    @Override
    public String toString() {
        return "AreaDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sn='" + sn + '\'' +
                ", location='" + location + '\'' +
                ", tunnelId=" + tunnelId +
                ", tunnel=" + tunnel +
                ", camera='" + camera + '\'' +
                ", crtTime=" + crtTime +
                '}';
    }
}