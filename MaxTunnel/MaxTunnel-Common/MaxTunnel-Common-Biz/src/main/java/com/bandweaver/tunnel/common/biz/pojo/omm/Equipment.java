package com.bandweaver.tunnel.common.biz.pojo.omm;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName: Equipment
 * @Description: 设备
 * @author shaosen
 * @date 2018年6月4日
 */
public class Equipment implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -7268420534954351487L;
	private Integer id;
    private String assetNo;
    private String name;
    private Integer type;
    private Date crtTime;
    private Integer serviceLife;
    private Integer status;
    private Integer tunnelId;
    private Integer venderId;
    private Integer modelId;
    private Integer objId;
    private String imgUrl;
    
    public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Integer getVenderId() {
		return venderId;
	}

	public void setVenderId(Integer venderId) {
		this.venderId = venderId;
	}

	public Integer getModelId() {
		return modelId;
	}

	public void setModelId(Integer modelId) {
		this.modelId = modelId;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAssetNo() {
        return assetNo;
    }

    public void setAssetNo(String assetNo) {
        this.assetNo = assetNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


    public Date getCrtTime() {
		return crtTime;
	}

	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}

	public Integer getServiceLife() {
        return serviceLife;
    }

    public void setServiceLife(Integer serviceLife) {
        this.serviceLife = serviceLife;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTunnelId() {
        return tunnelId;
    }

    public void setTunnelId(Integer tunnelId) {
        this.tunnelId = tunnelId;
    }

    public Integer getObjId() {
		return objId;
	}

	public void setObjId(Integer objId) {
		this.objId = objId;
	}

	@Override
	public String toString() {
		return "Equipment [id=" + id + ", assetNo=" + assetNo + ", name=" + name + ", type=" + type + ", crtTime="
				+ crtTime + ", serviceLife=" + serviceLife + ", status=" + status + ", tunnelId=" + tunnelId
				+ ", venderId=" + venderId + ", modelId=" + modelId + ", imgUrl=" + imgUrl + "]";
	}
}
