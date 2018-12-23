package com.bandweaver.tunnel.common.biz.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.bandweaver.tunnel.common.biz.pojo.Area;
import com.bandweaver.tunnel.common.biz.pojo.Tunnel;

/**
 * ClassName: Store
 * @Description: 管廊仓
 * @author shaosen
 * @date 2018年6月14日
 */
public class Store implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -6470825875559682766L;

	private Integer id;

	private String name;

	private String sn;
	
    private Integer tunnelId;

    private Integer storeTypeId;
    
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

    public Integer getTunnelId() {
        return tunnelId;
    }

    public void setTunnelId(Integer tunnelId) {
        this.tunnelId = tunnelId;
    }

    public Integer getStoreTypeId() {
        return storeTypeId;
    }

    public void setStoreTypeId(Integer storeTypeId) {
        this.storeTypeId = storeTypeId;
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
        return "Store{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sn='" + sn + '\'' +
                ", tunnelId=" + tunnelId +
                ", storeTypeId=" + storeTypeId +
                ", camera='" + camera + '\'' +
                ", crtTime=" + crtTime +
                '}';
    }
}