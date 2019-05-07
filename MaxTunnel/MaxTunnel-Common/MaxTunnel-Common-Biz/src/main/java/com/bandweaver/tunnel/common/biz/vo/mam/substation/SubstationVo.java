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
	private Integer invId; // 进线电压id
	private Integer inaId; // 进线电流id
	private Integer outvId; // 出线电压id
	private Integer outaId; // 出线电流id
	private Integer powerId; // 实时功率id
	private Date time; // 创建时间
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
	public Integer getInvId() {
		return invId;
	}
	public void setInvId(Integer invId) {
		this.invId = invId;
	}
	public Integer getInaId() {
		return inaId;
	}
	public void setInaId(Integer inaId) {
		this.inaId = inaId;
	}
	public Integer getOutvId() {
		return outvId;
	}
	public void setOutvId(Integer outvId) {
		this.outvId = outvId;
	}
	public Integer getOutaId() {
		return outaId;
	}
	public void setOutaId(Integer outaId) {
		this.outaId = outaId;
	}
	public Integer getPowerId() {
		return powerId;
	}
	public void setPowerId(Integer powerId) {
		this.powerId = powerId;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "SubstationVo [id=" + id + ", tunnelId=" + tunnelId + ", invId="
				+ invId + ", inaId=" + inaId + ", outvId=" + outvId + ", outaId="
				+ outaId + ", powerId=" + powerId + ", time=" + time + "]";
	}
	
}
