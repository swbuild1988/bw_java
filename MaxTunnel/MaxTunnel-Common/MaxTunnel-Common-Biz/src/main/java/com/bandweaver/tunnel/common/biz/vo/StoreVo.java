package com.bandweaver.tunnel.common.biz.vo;

import java.util.Date;

import com.bandweaver.tunnel.common.biz.vo.BaseVo;

/**
 * ClassName: Store
 * @Description: 管廊仓
 * @author shaosen
 * @date 2018年6月14日
 */
public class StoreVo extends BaseVo{
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
}