package com.bandweaver.tunnel.common.biz.vo.oam;

import com.bandweaver.tunnel.common.biz.vo.BaseVo;

public class EventVo extends BaseVo {
	
	private Integer id;
	
	private String name;
	
	private Integer level;
	
	private Integer staffId;

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

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getStaffId() {
		return staffId;
	}

	public void setStaffId(Integer staffId) {
		this.staffId = staffId;
	}
	
}
