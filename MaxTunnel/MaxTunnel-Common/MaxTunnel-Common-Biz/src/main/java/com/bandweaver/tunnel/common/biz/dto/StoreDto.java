package com.bandweaver.tunnel.common.biz.dto;

import java.io.Serializable;
import java.util.Date;

import com.bandweaver.tunnel.common.biz.pojo.Store;
import com.bandweaver.tunnel.common.biz.pojo.StoreType;

/**
 * ClassName: StoreDto
 * @Description: 管廊仓
 * @author shaosen
 * @date 2018年6月14日
 */
public class StoreDto extends Store implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 6899521081832228651L;

    private TunnelSimpleDto tunnel;
    
    private StoreType storeType;
    
    public StoreType getStoreType() {
		return storeType;
	}

	public void setStoreType(StoreType storeType) {
		this.storeType = storeType;
	}


	private String camera;
    
    private Date crtTime;
    

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
				", tunnel=" + tunnel +
				", storeType=" + storeType +
				", camera='" + camera + '\'' +
				", crtTime=" + crtTime +
				'}';
	}
}