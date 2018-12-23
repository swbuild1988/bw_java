package com.bandweaver.tunnel.common.biz.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.bandweaver.tunnel.common.biz.pojo.Area;
import com.bandweaver.tunnel.common.biz.pojo.Tunnel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.bandweaver.tunnel.common.biz.pojo.StoreType;

/**
 * ClassName: StoreDto
 * @Description: 管廊仓
 * @author shaosen
 * @date 2018年6月14日
 */
public class StoreDto implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 6899521081832228651L;

	private Integer id;
	
	private String name;

	private String sn;

	@JsonIgnore
    private Integer tunnelId;
    private TunnelSimpleDto tunnel;
    
    @JsonIgnore
    private Integer storeTypeId;
    private StoreType storeType;
    
    public StoreType getStoreType() {
		return storeType;
	}

	public void setStoreType(StoreType storeType) {
		this.storeType = storeType;
	}


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

	public TunnelSimpleDto getTunnel() {
		return tunnel;
	}

	public void setTunnel(TunnelSimpleDto tunnel) {
		this.tunnel = tunnel;
	}


	@Override
	public String toString() {
		return "StoreDto{" +
				"id=" + id +
				", name='" + name + '\'' +
				", sn='" + sn + '\'' +
				", tunnelId=" + tunnelId +
				", tunnel=" + tunnel +
				", storeTypeId=" + storeTypeId +
				", storeType=" + storeType +
				", camera='" + camera + '\'' +
				", crtTime=" + crtTime +
				'}';
	}
}