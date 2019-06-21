package com.bandweaver.tunnel.common.biz.vo.mam.substation;

import java.util.Date;

import com.bandweaver.tunnel.common.biz.vo.BaseVo;

/**
 * 变电站查询参数
 * @author ya.liu
 * @Date 2019年4月22日
 */
public class SubstationVo extends BaseVo {
	
	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer tunnelId;
	private String name;
	private Boolean type;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getTunnelId() {
		return tunnelId;
	}
	public void setTunnelId(Integer tunnelId) {
		this.tunnelId = tunnelId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getType() {
		return type;
	}
	public void setType(Boolean type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "SubstationVo [id=" + id + ", tunnelId=" + tunnelId + ", name="
				+ name + ", type=" + type + "]";
	}
	
}
