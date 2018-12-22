package com.bandweaver.tunnel.common.biz.dto.oam;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.bandweaver.tunnel.common.biz.constant.oam.CableStatusEnum;
import com.bandweaver.tunnel.common.biz.constant.oam.ContractStatusEnum;
import com.bandweaver.tunnel.common.biz.pojo.oam.Cable;
import com.bandweaver.tunnel.common.biz.pojo.oam.CableContract;

public class ContractDto implements Serializable {

	private static final long serialVersionUID = 2875630261505081411L;

	private CableDto cableDto;
	
	private List<Integer> areaIds;
	private Integer storeId;
	private Integer tunnelId;

	public CableDto getCableDto() {
		return cableDto;
	}

	public void setCableDto(CableDto cableDto) {
		this.cableDto = cableDto;
	}

	public List<Integer> getAreaIds() {
		return areaIds;
	}

	public void setAreaIds(List<Integer> areaIds) {
		this.areaIds = areaIds;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	public Integer getTunnelId() {
		return tunnelId;
	}

	public void setTunnelId(Integer tunnelId) {
		this.tunnelId = tunnelId;
	}


}
