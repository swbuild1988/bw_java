package com.bandweaver.tunnel.common.biz.pojo.xml;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.bandweaver.tunnel.common.biz.pojo.em.RelatedUnit;

@XmlRootElement(name = "Config")
public class Config implements Serializable{

	private static final long serialVersionUID = 1L;
	// 批量导入监测对象所需管廊参数
	private TunnelParam tunnelParam;
	// 批量导入监测对象所需设备参数
	private List<EquipmentTypeFile> typeFiles;
	// 大屏地图各点位置
	private List<RelatedUnit> units;
	// 
	private List<Integer> objectTypeIds;
	
	public void init(){
		if(this.tunnelParam == null)
			this.tunnelParam = new TunnelParam(0, 0, 0, 0, 0, 0, 0, 0);
		if(this.units == null || this.units.size() < 1) {
			List<RelatedUnit> units = new ArrayList<>();
			units.add(new RelatedUnit(1, "112", "37"));
			this.units = units;
		}
		if(this.typeFiles == null || this.typeFiles.size() < 1) {
			List<EquipmentTypeFile> typeFiles = new ArrayList<>();
    		typeFiles.add(new EquipmentTypeFile("01", "设备", 0));
    		this.typeFiles = typeFiles;
		}
		if(this.objectTypeIds == null || this.objectTypeIds.size() < 1) {
			List<Integer> objectTypeIds = new ArrayList<>();
			objectTypeIds.add(0);
			this.objectTypeIds = objectTypeIds;
		}
	}
	
	public TunnelParam getTunnelParam() {
		return tunnelParam;
	}

	public void setTunnelParam(TunnelParam tunnelParam) {
		this.tunnelParam = tunnelParam;
	}

	public List<EquipmentTypeFile> getTypeFiles() {
		return typeFiles;
	}

	public void setTypeFiles(List<EquipmentTypeFile> typeFiles) {
		this.typeFiles = typeFiles;
	}

	public List<RelatedUnit> getUnits() {
		return units;
	}

	public void setUnits(List<RelatedUnit> units) {
		this.units = units;
	}

	public List<Integer> getObjectTypeIds() {
		return objectTypeIds;
	}

	public void setObjectTypeIds(List<Integer> objectTypeIds) {
		this.objectTypeIds = objectTypeIds;
	}
	
}
