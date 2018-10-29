package com.bandweaver.tunnel.common.biz.dto;

import java.io.Serializable;
import java.util.Date;

import com.bandweaver.tunnel.common.biz.pojo.Company;
import com.bandweaver.tunnel.common.biz.pojo.Staff;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class TunnelDto implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String name;

    private Double length;
    @JsonIgnore
    private Integer responsibilityId;
    private Staff responsibility;
    
    @JsonIgnore
    private Integer constructId;
    private Company construct;
    
    @JsonIgnore
    private Integer operationId;
    private Company operation;

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

	public Staff getResponsibility() {
		return responsibility;
	}

	public void setResponsibility(Staff responsibility) {
		this.responsibility = responsibility;
	}

	public Company getConstruct() {
		return construct;
	}

	public void setConstruct(Company construct) {
		this.construct = construct;
	}

	public Company getOperation() {
		return operation;
	}

	public void setOperation(Company operation) {
		this.operation = operation;
	}
    
    
}