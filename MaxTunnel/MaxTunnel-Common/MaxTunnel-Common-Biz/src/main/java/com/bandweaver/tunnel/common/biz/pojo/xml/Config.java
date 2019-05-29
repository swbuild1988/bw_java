package com.bandweaver.tunnel.common.biz.pojo.xml;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.bandweaver.tunnel.common.biz.pojo.em.RelatedUnit;

@XmlRootElement(name = "Config")
public class Config implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private TunnelParam tunnelParam;
	
	private List<EquipmentTypeFile> typeFiles;
	
	private List<RelatedUnit> units;
	
	public Config() {}
	
	public Config(TunnelParam tunnelParam, List<EquipmentTypeFile> typeFiles) {
		this.tunnelParam = tunnelParam;
		this.typeFiles = typeFiles;
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
