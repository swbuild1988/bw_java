package com.bandweaver.tunnel.common.biz.dto.oam;

import com.bandweaver.tunnel.common.biz.constant.mam.ObjectType;
import com.bandweaver.tunnel.common.biz.constant.oam.EnergyType;
import com.bandweaver.tunnel.common.biz.dto.AreaDto;
import com.bandweaver.tunnel.common.biz.dto.StoreDto;
import com.bandweaver.tunnel.common.biz.dto.TunnelSimpleDto;
import com.bandweaver.tunnel.common.biz.pojo.oam.Consume;

public class ConsumeDto extends Consume{
	
	private TunnelSimpleDto tunnelSimpleDto;
	private StoreDto storeDto;
	private AreaDto areaDto;
	//对象类型名
	private String objectTypeName;
	//能耗类型名
	private String energyTypeName;
	
	public TunnelSimpleDto getTunnelSimpleDto() {
		return tunnelSimpleDto;
	}
	public void setTunnelSimpleDto(TunnelSimpleDto tunnelSimpleDto) {
		this.tunnelSimpleDto = tunnelSimpleDto;
	}
	public StoreDto getStoreDto() {
		return storeDto;
	}
	public void setStoreDto(StoreDto storeDto) {
		this.storeDto = storeDto;
	}
	public AreaDto getAreaDto() {
		return areaDto;
	}
	public void setAreaDto(AreaDto areaDto) {
		this.areaDto = areaDto;
	}
	public String getObjectTypeName() {
		return ObjectType.getEnum(super.getObjectType()).getName();
	}
	public void setObjectTypeName(String objectTypeName) {
		this.objectTypeName = objectTypeName;
	}
	public String getEnergyTypeName() {
		return EnergyType.getEnum(super.getEnergyType()).getName();
	}
	public void setEnergyTypeName(String energyTypeName) {
		this.energyTypeName = energyTypeName;
	}
	
}
