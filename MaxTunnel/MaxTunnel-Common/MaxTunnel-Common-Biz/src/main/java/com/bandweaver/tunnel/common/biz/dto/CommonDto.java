package com.bandweaver.tunnel.common.biz.dto;

import java.io.Serializable;

public class CommonDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String name;
	private Integer count;
	
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
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	@Override
	public String toString() {
		return "CommonDto [id=" + id + ", name=" + name 
				+ ", count=" + count + "]";
	}
	

}
