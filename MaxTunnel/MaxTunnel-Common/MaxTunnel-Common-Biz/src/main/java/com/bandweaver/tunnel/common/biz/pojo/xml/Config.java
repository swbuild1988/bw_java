package com.bandweaver.tunnel.common.biz.pojo.xml;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.bandweaver.tunnel.common.biz.pojo.em.RelatedUnit;

@XmlRootElement(name = "Config")
public class Config implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private TunnelParam tunnelParam;
	
	private List<EquipmentTypeFile> typeFiles;
	
	private List<RelatedUnit> units;
	
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
    		typeFiles.add(new EquipmentTypeFile("01", "…Ë±∏", 0));
    		this.typeFiles = typeFiles;
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
	
}
