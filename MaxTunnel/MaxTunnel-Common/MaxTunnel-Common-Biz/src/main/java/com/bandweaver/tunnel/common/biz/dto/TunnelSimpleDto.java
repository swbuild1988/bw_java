package com.bandweaver.tunnel.common.biz.dto;

import java.io.Serializable;
import java.util.Date;

import com.bandweaver.tunnel.common.biz.pojo.Company;
import com.bandweaver.tunnel.common.biz.pojo.Staff;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class TunnelSimpleDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String name;

	private Integer status;

	private Double length;

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

	@Override
	public String toString() {
		return "TunnelSimpleDto [id=" + id + ", name=" + name + ", status=" + status + ", length=" + length + "]";
	}
}