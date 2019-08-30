package com.bandweaver.tunnel.common.biz.dto;

import java.io.Serializable;

public class TunnelSimpleDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String name;

	private Integer status;

	private Double length;
	
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Double getLength() {
		return length;
	}

	public void setLength(Double length) {
		this.length = length;
	}

	public Boolean getShow() {
		return show;
	}

	public void setShow(Boolean show) {
		this.show = show;
	}

	@Override
	public String toString() {
		return "TunnelSimpleDto [id=" + id + ", name=" + name + ", status=" + status + ", length=" + length + ", show=" + show + "]";
	}
}