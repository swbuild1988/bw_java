package com.bandweaver.tunnel.common.biz.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName: Area
 * @Description: 管廊区域
 * @author shaosen
 * @date 2018年6月11日
 */
public class Area implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 2494746325998543678L;

	private Integer id;

    private String name;

    private String sn;

    private String location;

    private Integer tunnelId;
    
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
        this.name = name;
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
        this.location = location;
    }

    public Integer getTunnelId() {
        return tunnelId;
    }

    public void setTunnelId(Integer tunnelId) {
        this.tunnelId = tunnelId;
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
        return "Area{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sn='" + sn + '\'' +
                ", location='" + location + '\'' +
                ", tunnelId=" + tunnelId +
                ", camera='" + camera + '\'' +
                ", crtTime=" + crtTime +
                '}';
    }
}