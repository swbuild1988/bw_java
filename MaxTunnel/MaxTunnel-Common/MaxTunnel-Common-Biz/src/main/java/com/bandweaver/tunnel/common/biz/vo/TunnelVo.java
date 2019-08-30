package com.bandweaver.tunnel.common.biz.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TunnelVo extends BaseVo{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

	private List<Integer> ids;

    private String name;

    private String sn;

    private Double length;

    private Integer responsibilityId;

    private Integer constructId;

    private Integer operationId;
    
    private Integer status;
    
    private String camera;
    
    private Integer maxviewConfigId;

    private Date crtTime;
    
    private Boolean show;

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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

	public Boolean getShow() {
		return show;
	}

	public void setShow(Boolean show) {
		this.show = show;
	}
}