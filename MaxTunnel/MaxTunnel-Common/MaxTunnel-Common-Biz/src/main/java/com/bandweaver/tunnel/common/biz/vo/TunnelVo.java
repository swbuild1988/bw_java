package com.bandweaver.tunnel.common.biz.vo;

import java.io.Serializable;
import java.util.Date;

public class TunnelVo extends BaseVo implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String name;

    private String sn;

    private Double length;

    private Integer responsibilityId;

    private Integer constructId;

    private Integer operationId;
    
    private String camera;
    
    private Integer maxviewConfigId;

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

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Integer getResponsibilityId() {
        return responsibilityId;
    }

    public void setResponsibilityId(Integer responsibilityId) {
        this.responsibilityId = responsibilityId;
    }

    public Integer getConstructId() {
        return constructId;
    }

    public void setConstructId(Integer constructId) {
        this.constructId = constructId;
    }

    public Integer getOperationId() {
        return operationId;
    }

    public void setOperationId(Integer operationId) {
        this.operationId = operationId;
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

	public Integer getMaxviewConfigId() {
		return maxviewConfigId;
	}

	public void setMaxviewConfigId(Integer maxviewConfigId) {
		this.maxviewConfigId = maxviewConfigId;
	}
    
}